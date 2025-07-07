package com.kidshealth.app.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kidshealth.app.data.model.HealthReport
import com.kidshealth.app.data.repository.HealthReportRepository
import com.kidshealth.app.screens.appointment.AppointmentScreen
import com.kidshealth.app.screens.auth.CreateAccountScreen
import com.kidshealth.app.screens.auth.LoginScreen
import com.kidshealth.app.screens.dashboard.DashboardScreen
import com.kidshealth.app.screens.doctor.DoctorReportFormScreen
import com.kidshealth.app.screens.growth.GrowthTrackingScreen
import com.kidshealth.app.screens.notifications.NotificationsScreen
import com.kidshealth.app.screens.reports.HealthReportsScreen
import com.kidshealth.app.screens.reports.ReportDetailScreen
import com.kidshealth.app.screens.welcome.WelcomeScreen
import kotlinx.coroutines.launch

@Composable
fun KidsHealthNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    // Initialize repositories
    val healthReportRepository = remember { HealthReportRepository() }
    val scope = rememberCoroutineScope()
    
    // Collect health reports
    val healthReports by healthReportRepository.healthReports.collectAsState(initial = emptyList())

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
                healthReports = healthReports,
                onScheduleAppointmentClick = {
                    navController.navigate(Screen.Appointment.route)
                },
                onTrackGrowthClick = {
                    navController.navigate(Screen.GrowthTracking.route)
                },
                onViewReportsClick = {
                    navController.navigate(Screen.HealthReports.route)
                },
                onNotificationsClick = {
                    navController.navigate(Screen.Notifications.route)
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
        
        composable(Screen.DoctorReportForm.route) {
            DoctorReportFormScreen(
                patientId = "patient_1", // This should come from navigation arguments
                patientName = "John Doe", // This should come from navigation arguments
                onBackClick = {
                    navController.popBackStack()
                },
                onReportSaved = { report ->
                    scope.launch {
                        healthReportRepository.saveHealthReport(report)
                        navController.popBackStack()
                    }
                }
            )
        }
        
        composable(Screen.HealthReports.route) {
            HealthReportsScreen(
                healthReports = healthReports,
                onBackClick = {
                    navController.popBackStack()
                },
                onReportClick = { reportId ->
                    navController.navigate(Screen.ReportDetail.createRoute(reportId))
                }
            )
        }
        
        composable(Screen.ReportDetail.route) { backStackEntry ->
            val reportId = backStackEntry.arguments?.getString("reportId")
            val report = healthReports.find { it.id == reportId }
            
            ReportDetailScreen(
                report = report,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Notifications.route) {
            NotificationsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}