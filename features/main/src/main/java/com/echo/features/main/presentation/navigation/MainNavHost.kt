package com.echo.features.main.presentation.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.echo.core.network.di.AppComponent
import com.echo.core.uikit.utils.daggerViewModel
import com.echo.features.feed.di.DaggerFeedComponent
import com.echo.features.feed.presentation.screens.FeedScreen

object MainRoutes{
    const val FEED = "feed"
    //todo: остальные экраны
}

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
    appComponent: AppComponent,
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
                viewModel = daggerViewModel { feedComponent.getViewModel() }
            )
        }
    }
}