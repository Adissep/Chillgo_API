package com.capstone.chillgoapp.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.navigation.NavigationItem
import com.capstone.chillgoapp.navigation.Screen
import com.capstone.chillgoapp.screens.DetailScreen
import com.capstone.chillgoapp.screens.FavoriteScreen
import com.capstone.chillgoapp.screens.HomeScreen
import com.capstone.chillgoapp.screens.LoginScreen
import com.capstone.chillgoapp.screens.MoreScreen
import com.capstone.chillgoapp.screens.OnBoardingScreen
import com.capstone.chillgoapp.screens.OnBoardingSecondScreen
import com.capstone.chillgoapp.screens.ProfileScreen
import com.capstone.chillgoapp.screens.ReviewsScreen
import com.capstone.chillgoapp.screens.SignUpScreen
import com.capstone.chillgoapp.screens.SplashScreen
import com.capstone.chillgoapp.screens.TermAndConditionsScreen
import com.capstone.chillgoapp.screens.UmkmFormScreen
import com.capstone.chillgoapp.screens.UmkmMapScreen
import com.capstone.chillgoapp.ui.theme.ChillGoAppTheme
import com.capstone.chillgoapp.ui.theme.PrimaryBorder
import com.capstone.chillgoapp.ui.theme.PrimaryMain

@Composable
fun DashboardApp() {

    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Splash.route &&
                currentRoute != Screen.OnBoarding.route &&
                currentRoute != Screen.OnBoardingSecond.route &&
                currentRoute != Screen.LoginScreen.route &&
                currentRoute != Screen.SignUpScreen.route &&
                currentRoute != Screen.UmkmDetail.route &&
                currentRoute != Screen.TermsAndConditionsScreen.route &&
                currentRoute != Screen.DetailTravel.route &&
                currentRoute != Screen.Reviews.route &&
                currentRoute != Screen.UmkmForm.route
            ) {
                BottomBar(navController)
            }
        },
    ) { innerPadding ->
        val paddingScaffold = Modifier.padding(innerPadding)
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(
                    gotoLogin = {
                        navController.navigate(Screen.OnBoarding.route) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    },
                    gotoHome = {
                        navController.navigate(Screen.HomeScreen.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Screen.OnBoarding.route) {
                OnBoardingScreen(
                    onNavigateToLogin = {
                        navController.navigate(Screen.LoginScreen.route)
                    },
                    onNavigateToSecondScreen = {
                        navController.navigate(Screen.OnBoardingSecond.route)
                    }
                )
            }
            composable(Screen.OnBoardingSecond.route) {
                OnBoardingSecondScreen(
                    onNavigateToLogin = {
                        navController.navigate(Screen.LoginScreen.route)
                    },
                    onNavigateToSignup = {
                        navController.navigate(Screen.SignUpScreen.route)
                    }
                )
            }
            composable(Screen.LoginScreen.route) {
                LoginScreen(
                    onNavigateToSignUp = {
                        navController.navigate(Screen.SignUpScreen.route)
                    },
                    onNavigateToHome = {
                        navController.navigate(Screen.HomeScreen.route) {
                            popUpTo(Screen.OnBoarding.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Screen.SignUpScreen.route) {
                SignUpScreen(
                    onNavigateToLogin = {
                        navController.popBackStack()
                    },
                    onNavigateToHome = {
                        navController.navigate(Screen.HomeScreen.route) {
                            popUpTo(Screen.OnBoarding.route) {
                                inclusive = true
                            }
                        }
                    },
                    onNavigateToTerm = {
                        navController.navigate(Screen.TermsAndConditionsScreen.route)
                    }
                )
            }
            composable(Screen.TermsAndConditionsScreen.route) {
                TermAndConditionsScreen()
            }
            composable(Screen.HomeScreen.route) {
                HomeScreen(
                    modifier = paddingScaffold,
                    navigateToDetail = { ticketId, city ->
                        navController.navigate(
                            Screen.DetailTravel.createRoute(
                                ticketId,
                                city,
                                false
                            )
                        )
                    },
                    navigateToMore = {
                        navController.navigate(Screen.More.createRoute(it))
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    modifier = paddingScaffold,
                    onNavigateToDetail = { placeId ->
                        navController.navigate(Screen.DetailTravel.createRoute(placeId, "", false))
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    modifier = paddingScaffold,
                    navigateToDetail = { ticketId, city ->
                        navController.navigate(
                            Screen.DetailTravel.createRoute(
                                ticketId,
                                city,
                                false
                            )
                        )
                    },
                    onNavigateToLogin = {
                        navController.navigate(Screen.LoginScreen.route, navOptions {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        })
                    },
                )
            }
            composable(
                route = Screen.DetailTravel.route,
                arguments = listOf(
                    navArgument("placeId") { type = NavType.StringType },
                    navArgument("city") { type = NavType.StringType },
                    navArgument("scroll") { type = NavType.BoolType }
                ),
            ) {
                val isScroll = it.arguments?.getBoolean("scroll", false) ?: false

                DetailScreen(
                    modifier = paddingScaffold,
                    isScroll = isScroll,
                    navigateToReviews = {
                        navController.navigate(Screen.Reviews.route)
                    },
                    navigateToUmkmDetail = { placeId, city, umkmId ->
                        navController.navigate(Screen.UmkmDetail.createRoute(placeId, umkmId, city))
                    },
                    navigateToUmkmForm = { placeId ->
                        navController.navigate(Screen.UmkmForm.createRoute(placeId))
                    }
                )
            }
            composable(
                Screen.UmkmDetail.route,
                arguments = listOf(
                    navArgument("umkmId") { type = NavType.LongType },
                    navArgument("city") { type = NavType.StringType },
                    navArgument("placeId") { type = NavType.LongType }),
            ) {
                UmkmMapScreen()
            }
            composable(
                "${Screen.More.route}/{city}",
                arguments = listOf(navArgument("city") { type = NavType.StringType })
            ) {
                MoreScreen(
                    modifier = paddingScaffold,
                    navigateToDetail = { placeId, city ->
                        navController.navigate(
                            Screen.DetailTravel.createRoute(
                                placeId,
                                city,
                                false
                            )
                        )
                    },
                    navigateToDetailReview = { placeId, city ->
                        navController.navigate(Screen.DetailTravel.createRoute(placeId, city, true))
                    },
                )
            }
            composable(Screen.Reviews.route) {
                ReviewsScreen(
                    onBackPressed = { navController.popBackStack() },
                )
            }
            composable(Screen.UmkmForm.route) {
                UmkmFormScreen(
                    onBackPressed = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = PrimaryMain
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.HomeScreen
            ),
            NavigationItem(
                title = stringResource(R.string.favorite),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    selectedIconColor = PrimaryBorder,
                    selectedTextColor = PrimaryBorder
                )
            )
        }
    }
}

/*private fun shareOrder(context: Context, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.subject_ticket))
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.subject_ticket)
        )
    )
}*/

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreview() {
    ChillGoAppTheme {
        DashboardApp()
    }
}
