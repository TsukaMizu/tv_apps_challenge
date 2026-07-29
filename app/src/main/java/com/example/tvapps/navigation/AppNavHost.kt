package com.example.tvapps.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tvapps.data.repository.TvRepository
import com.example.tvapps.ui.list.ShowListScreen

@Composable
fun AppNavHost(
    repository: TvRepository,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.ShowList.route,
        modifier = modifier
    ) {
        composable(NavRoute.ShowList.route) {
            ShowListScreen(
                repository = repository,
                onShowClick = { }
            )
        }
    }
}