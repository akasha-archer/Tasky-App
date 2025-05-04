package com.example.taskyapplication

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskyapplication.agenda.presentation.AgendaScreen
import com.example.taskyapplication.auth.presentation.AuthViewModel
import com.example.taskyapplication.ui.theme.TaskyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel: AuthViewModel by viewModels()
        authViewModel.isTokenExpired()
        val isTokenValid = authViewModel.isTokenValid.value
        val isUserRegistered =
            authViewModel.lceAuthUserData.value.data?.userId?.isNotEmpty() ?: false

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                authViewModel.isTokenValid.value
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
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = UserStateScreen
                ) {
                    composable<UserStateScreen> {
                        LaunchedEffect(isTokenValid, isUserRegistered) {
                            val route = when {
                                isTokenValid -> AgendaScreen
                                isUserRegistered -> LoginScreen
                                else -> RegisterScreen
                            }
                            navController.navigate(route) {
                                popUpTo(UserStateScreen) { inclusive = true }
                            }
                        }
                    }
                    composable<AgendaScreen> {
                        AgendaScreen(navController = navController)
                    }

                    composable<LoginScreen> {
                        LoginScreen(navController = navController)
                    }

                    composable<RegisterScreen> {
                        AccountCreationScreen(navController = navController)
                    }
                }
            }
        }
    }
}
