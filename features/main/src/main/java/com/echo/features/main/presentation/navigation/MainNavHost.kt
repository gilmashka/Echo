package com.echo.features.main.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.echo.core.network.di.AppComponent
import com.echo.core.uikit.utils.daggerViewModel
import com.echo.features.feed.di.DaggerFeedComponent
import com.echo.features.feed.presentation.screens.FeedScreen
import com.echo.features.category.di.DaggerCategoryComponent
import com.echo.features.category.presentation.screens.CategoryPickerScreen
import com.echo.features.event.di.DaggerEventDetailsComponent
import com.echo.features.event.presentation.screens.EventDetailsScreen

object MainRoutes{
    const val FEED = "feed"
    const val CATEGORIES = "categories"

}

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
    appComponent: AppComponent,
    onEventClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = MainRoutes.FEED,
        modifier = modifier
    ){

        composable(MainRoutes.FEED) {
           val feedComponent = remember {
               DaggerFeedComponent.builder()
                   .appComponent(appComponent)
                   .build()
           }

            FeedScreen(
                viewModel = daggerViewModel { feedComponent.getViewModel() },
                onEventClick = onEventClick
            )
        }

        composable(MainRoutes.CATEGORIES) {
            val categoryComponent = remember {
                DaggerCategoryComponent.builder()
                    .appComponent(appComponent)
                    .build()
            }
            CategoryPickerScreen(
                    viewModel = daggerViewModel { categoryComponent.getViewModel() }
            )
        }
    }
}