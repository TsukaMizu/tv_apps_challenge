package com.example.tvapps.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tvapps.data.repository.TvRepository
import com.example.tvapps.ui.detail.ShowDetailScreen
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
                onShowClick = { showId ->
                    navController.navigate(NavRoute.ShowDetail.createRoute(showId))
                }
            )
        }

        composable(
            route = NavRoute.ShowDetail.route,
            arguments = listOf(
                navArgument(NavRoute.ShowDetail.ARG_SHOW_ID) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val showId = backStackEntry.arguments?.getInt(NavRoute.ShowDetail.ARG_SHOW_ID) ?: return@composable
            ShowDetailScreen(
                showId = showId,
                repository = repository,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}