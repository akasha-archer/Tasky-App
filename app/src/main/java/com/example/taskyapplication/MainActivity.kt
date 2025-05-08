package com.example.taskyapplication

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.taskyapplication.auth.register.RegisterViewModel
import com.example.taskyapplication.ui.theme.TaskyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val registerViewModel: RegisterViewModel by viewModels()
        registerViewModel.isTokenExpired()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                registerViewModel.isTokenValid.value
            }

            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500L
                zoomX.doOnEnd { screen.remove() }

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }
        enableEdgeToEdge()
        setContent {
            TaskyApplicationTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val isTokenValid by registerViewModel.isTokenValid.collectAsStateWithLifecycle()
                    val isUserRegistered = false
                    val navController = rememberNavController()
                    // Composable that has NavHost as the root composable to handle navigation logic
                    NavigationRoot(
                        navController = navController,
                        isLoggedIn = isTokenValid,
                        isUserRegistered = isUserRegistered
                    )
                }
            }
        }
    }
}
