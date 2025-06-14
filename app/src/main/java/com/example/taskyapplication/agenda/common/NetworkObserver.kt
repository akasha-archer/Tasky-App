package com.example.taskyapplication.agenda.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.InetSocketAddress
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext
import java.net.Socket

sealed class NetworkStatus {
    data object Available : NetworkStatus()
    data object Unavailable : NetworkStatus()
}

interface INetworkObserver {
    val networkStatus: StateFlow<NetworkStatus>
    fun isOnline(): Boolean // For a quick synchronous check if needed, based on last known status
}

@Singleton
class NetworkObserver @Inject constructor(
    @ApplicationContext private val context: Context,
    // Consider injecting a CoroutineScope if you want to control its lifecycle more explicitly
    // For a Singleton, ApplicationScope is often appropriate.
    // private val externalScope: CoroutineScope
) : INetworkObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _networkStatus = MutableStateFlow(getCurrentNetworkStatus())
    override val networkStatus: StateFlow<NetworkStatus> = _networkStatus.asStateFlow()

    // Optional: A CoroutineScope for managing observation and active probes
    // If not injected, create one. Ensure it's cancelled appropriately if NetworkObserver
    // is not a true singleton tied to Application lifecycle.
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
                // A network is available, but we should verify internet access
                // You could trigger an active probe here if desired
                _networkStatus.value = NetworkStatus.Available // Optimistic update
                // performActiveProbe() // Optional: verify actual internet
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                // Check if any other network is still available
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
                    // This specific network lost internet, check overall status
                    _networkStatus.value = getCurrentNetworkStatus()
                }
            }
        }

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

        // For older APIs (before N), registerDefaultNetworkCallback is not available.
        // The above registerNetworkCallback with NET_CAPABILITY_INTERNET is generally preferred.
        // If you need to support very old devices, you might need BroadcastReceiver for CONNECTIVITY_ACTION
        // but that's deprecated for API 28+.

        // Optional: Unregister callback if this NetworkObserver's scope ends.
        // If it's a @Singleton tied to Application, it might not be strictly necessary
        // to manually unregister unless you have specific cleanup needs.
        // externalScope.coroutineContext[Job]?.invokeOnCompletion {
        //     connectivityManager.unregisterNetworkCallback(networkCallback)
        // }
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
                false
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

    // If you create your own CoroutineScope, ensure it's cancelled when appropriate
    // e.g., if NetworkObserver is not an @Singleton tied to the Application lifecycle.
    // fun cleanup() {
    //     coroutineScope.cancel()
    // }
}

/**
 * Alternative using callbackFlow (more idiomatic for wrapping callbacks)
 * This is a more advanced way to structure the callback listening.
 */
fun Context.observeConnectivityAsFlow(): Flow<NetworkStatus> = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            trySend(NetworkStatus.Available)
        }

        override fun onLost(network: Network) {
            // Check if another network is available before declaring unavailable
            val activeNetwork = connectivityManager.activeNetwork
            if (activeNetwork == null) {
                trySend(NetworkStatus.Unavailable)
            } else {
                val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
                if (capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    trySend(NetworkStatus.Available)
                } else {
                    trySend(NetworkStatus.Unavailable)
                }
            }
        }

        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                trySend(NetworkStatus.Available)
            } else {
                // It's possible this specific network lost internet, but another is active.
                // Re-evaluate overall status.
                val activeNetwork = connectivityManager.activeNetwork
                val activeCaps = connectivityManager.getNetworkCapabilities(activeNetwork)
                if (activeCaps != null && activeCaps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    activeCaps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    trySend(NetworkStatus.Available)
                } else {
                    trySend(NetworkStatus.Unavailable)
                }
            }
        }
    }

    // Get initial status
    val activeNetwork = connectivityManager.activeNetwork
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
    if (capabilities != null &&
        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    ) {
        trySend(NetworkStatus.Available)
    } else {
        trySend(NetworkStatus.Unavailable)
    }

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callback)

    awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}.distinctUntilChanged() // Only emit when the status actually changes