package com.echo.features.main.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import com.echo.features.friends.di.DaggerFriendsComponent
import com.echo.features.friends.presentation.screens.FriendsScreen
import com.echo.features.profile.di.DaggerProfileComponent
import com.echo.features.profile.di.ProfileComponent
import com.echo.features.profile.presentation.screens.EditProfileScreen
import com.echo.features.profile.presentation.screens.ProfileScreen

object MainRoutes{
    const val FEED = "feed"
    const val CATEGORIES = "categories"
    const val PROFILE = "profile"
    const val PROFILE_EDIT = "profile/edit"

    const val FRIENDS = "friends"

}

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
    appComponent: AppComponent,
    onEventClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    profileComponent: ProfileComponent,
    onLogout: () -> Unit = {},
    authKey: Long
){
    val profileViewModel = daggerViewModel { profileComponent.getViewModel() }

    NavHost(
        navController = navController,
        startDestination = MainRoutes.FEED,
        modifier = modifier
    ){

        composable(MainRoutes.FEED) {
           val feedComponent = remember(authKey) {
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

        composable(MainRoutes.PROFILE) {
            val profileComponent = remember {
                DaggerProfileComponent.builder().appComponent(appComponent).build()
            }
            ProfileScreen(
                viewModel = profileViewModel,
                onNavigateToEdit = { navController.navigate(MainRoutes.PROFILE_EDIT) },
                onLogout = onLogout
            )
        }

        composable(MainRoutes.PROFILE_EDIT) {
            val profileComponent = remember {
                DaggerProfileComponent.builder().appComponent(appComponent).build()
            }
            EditProfileScreen(
                viewModel = profileViewModel,
                onBack = { navController.popBackStack() },
                onLogout = onLogout
            )
        }

        composable(MainRoutes.FRIENDS) {
            val friendsComponent = remember {
                DaggerFriendsComponent.builder()
                    .appComponent(appComponent)
                    .build()
            }
            FriendsScreen(viewModel = daggerViewModel { friendsComponent.getViewModel() })
        }
    }
}