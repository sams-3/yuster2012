package com.kidshealth.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kidshealth.app.screens.appointment.AppointmentScreen
import com.kidshealth.app.screens.auth.CreateAccountScreen
import com.kidshealth.app.screens.auth.LoginScreen
import com.kidshealth.app.screens.dashboard.DashboardScreen
import com.kidshealth.app.screens.growth.GrowthTrackingScreen
import com.kidshealth.app.screens.welcome.WelcomeScreen

@Composable
fun KidsHealthNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route,
        modifier = modifier
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                },
                onCreateAccountClick = {
                    navController.navigate(Screen.CreateAccount.route)
                }
            )
        }
        
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                },
                onCreateAccountClick = {
                    navController.navigate(Screen.CreateAccount.route)
                }
            )
        }
        
        composable(Screen.CreateAccount.route) {
            CreateAccountScreen(
                onAccountCreated = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
        
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onScheduleAppointmentClick = {
                    navController.navigate(Screen.Appointment.route)
                },
                onTrackGrowthClick = {
                    navController.navigate(Screen.GrowthTracking.route)
                }
            )
        }
        
        composable(Screen.Appointment.route) {
            AppointmentScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.GrowthTracking.route) {
            GrowthTrackingScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}