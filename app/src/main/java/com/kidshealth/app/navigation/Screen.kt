package com.kidshealth.app.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object CreateAccount : Screen("create_account")
    object Dashboard : Screen("dashboard")
    object Appointment : Screen("appointment")
    object GrowthTracking : Screen("growth_tracking")
}