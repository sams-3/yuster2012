package com.kidshealth.app.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object CreateAccount : Screen("create_account")
    object Dashboard : Screen("dashboard")
    object Appointment : Screen("appointment")
    object GrowthTracking : Screen("growth_tracking")
    object DoctorReportForm : Screen("doctor_report_form")
    object HealthReports : Screen("health_reports")
    object ReportDetail : Screen("report_detail/{reportId}") {
        fun createRoute(reportId: String) = "report_detail/$reportId"
    }
    object Notifications : Screen("notifications")
    object DoctorDashboard : Screen("doctor_dashboard")
}