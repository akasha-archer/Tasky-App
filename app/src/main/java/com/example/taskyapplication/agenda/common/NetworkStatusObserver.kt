package com.example.taskyapplication.agenda.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.InetSocketAddress
import javax.inject.Inject
import javax.inject.Singleton
import java.net.Socket

sealed class NetworkStatus {
    data object Available : NetworkStatus()
    data object Unavailable : NetworkStatus()
}

interface INetworkObserver {
    val networkStatus: StateFlow<NetworkStatus>
    fun isOnline(): Boolean
}

@Singleton
class NetworkStatusObserver @Inject constructor(
    @ApplicationContext private val context: Context,
) : INetworkObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _networkStatus = MutableStateFlow(getCurrentNetworkStatus())
    override val networkStatus: StateFlow<NetworkStatus> = _networkStatus.asStateFlow()
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO + Job())


    init {
        observeNetworkChanges()
    }

    private fun observeNetworkChanges() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                _networkStatus.value = NetworkStatus.Available
                 performActiveProbe()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                _networkStatus.value = getCurrentNetworkStatus()
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                val isInternet =
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                if (isInternet) {
                    _networkStatus.value = NetworkStatus.Available
                } else {
                    _networkStatus.value = getCurrentNetworkStatus()
                }
            }
        }

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun getCurrentNetworkStatus(): NetworkStatus {
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return if (capabilities != null &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) // VALIDATED is a good sign
        ) {
            NetworkStatus.Available
        } else {
            NetworkStatus.Unavailable
        }
    }

    /**
     * Provides a synchronous way to check the last known network status.
     * Useful for immediate checks, but observing the StateFlow is preferred for UI updates.
     */
    override fun isOnline(): Boolean {
        return _networkStatus.value == NetworkStatus.Available
    }


    /**
     * Optional: Actively probes internet connectivity.
     * This can be called periodically or when a network becomes available.
     */
    private fun performActiveProbe() {
        coroutineScope.launch {
            val hasInternet = try {
                // Simple socket connection test (adjust timeout as needed)
                Socket().use { socket ->
                    socket.connect(InetSocketAddress("8.8.8.8", 53), 1500) // Google DNS
                    true
                }
            } catch (e: IOException) {
                Log.e("NetworkStatusObserver", "No internet connection: ${e.message}")
                false
            } finally {
                cleanup()
            }

            if (hasInternet) {
                if (_networkStatus.value != NetworkStatus.Available) {
                    _networkStatus.value = NetworkStatus.Available
                }
            } else {
                // If active probe fails, and system still thinks network is up,
                // we might consider it offline for practical purposes.
                if (_networkStatus.value == NetworkStatus.Available) {
                    // Be cautious with this. If the system says available but probe fails,
                    // it could be a temporary blip or firewall.
                    // For now, let's trust the probe if it says unavailable.
                    _networkStatus.value = NetworkStatus.Unavailable
                }
            }
        }
    }

     fun cleanup() {
         coroutineScope.cancel()
     }
}
