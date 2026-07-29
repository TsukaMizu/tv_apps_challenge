package com.example.tvapps.navigation

sealed class NavRoute(val route: String) {

    data object ShowList : NavRoute("show_list")

}