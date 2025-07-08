package com.kidshealth.app.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kidshealth.app.di.DatabaseModule
import com.kidshealth.app.di.SupabaseModule
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
import com.kidshealth.app.viewmodel.HealthReportViewModel
import com.kidshealth.app.viewmodel.HealthReportViewModelFactory
import com.kidshealth.app.viewmodel.SupabaseHealthReportViewModel
import com.kidshealth.app.viewmodel.SupabaseHealthReportViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun KidsHealthNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    // Initialize Supabase repositories
    val healthReportRepository = remember { SupabaseModule.provideHealthReportRepository() }
    val appointmentRepository = remember { SupabaseModule.provideAppointmentRepository() }
    
    // Initialize Supabase ViewModels
    val healthReportViewModel: SupabaseHealthReportViewModel = viewModel(
        factory = SupabaseHealthReportViewModelFactory(healthReportRepository)
    )
    
    // Collect health reports
    val healthReports by healthReportViewModel.healthReports.collectAsState()

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
                        healthReportViewModel.saveHealthReport(report)
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
            var report by remember { mutableStateOf(null as com.kidshealth.app.data.model.HealthReport?) }
            
            LaunchedEffect(reportId) {
                reportId?.let {
                    report = healthReportViewModel.getHealthReportById(it)
                }
            }
            
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