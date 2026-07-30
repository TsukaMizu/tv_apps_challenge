package com.example.tvapps.navigation

sealed class NavRoute(val route: String) {

    data object ShowList : NavRoute("show_list")

    data object ShowDetail : NavRoute("show_detail/{showId}") {
        const val ARG_SHOW_ID = "showId"
        fun createRoute(showId: Int) = "show_detail/$showId"
    }
}