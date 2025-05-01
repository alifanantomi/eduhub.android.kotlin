package com.example.eduhub.navigation

object Destinations {
    const val SPLASH_ROUTE = "splash"

    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTER = "register"
    const val FORGOT_PASSWORD_ROUTE = "forgot_password"

    const val HOME_ROUTE = "home"
    const val MODULE_LIST_ROUTE = "modules"
    const val MODULE_DETAIL_ROUTE = "module/{moduleId}"
    const val BOOKMARKS_ROUTE = "bookmarks"
    const val PROFILE_ROUTE = "profile"

    fun moduleDetailRoute(moduleId: String): String {
        return "module/${moduleId}"
    }
}