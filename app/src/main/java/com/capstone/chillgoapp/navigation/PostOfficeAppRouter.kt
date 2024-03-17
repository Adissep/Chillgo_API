package com.capstone.chillgoapp.navigation

sealed class Screen(
    val route: String = ""
) {

    object SignUpScreen : Screen("signup")
    object TermsAndConditionsScreen : Screen("terms")
    object LoginScreen : Screen("login")
    object HomeScreen : Screen("home")

    object Splash : Screen("splash")
    object OnBoarding : Screen("onBoarding")
    object OnBoardingSecond : Screen("onBoardingSecond")
    object Dashboard : Screen("dashboard")
    object Favorite : Screen("cart")
    object Profile : Screen("profile")
    object More : Screen("more"){
        fun createRoute(city:String) = "more/$city"
    }
    object Reviews : Screen("reviews")
    object UmkmDetail : Screen("umkmDetail/{placeId}/{umkmId}/{city}"){
        fun createRoute(placeId: Long,umkmId: Long,city: String) = "umkmDetail/$placeId/$umkmId/$city"
    }
    object UmkmForm : Screen("umkmForm/{placeId}"){
        fun createRoute(placeId: String) = "umkmForm/$placeId"
    }
    object DetailTravel : Screen("home/{placeId}/{city}/{scroll}") {
        fun createRoute(placeId: String,city: String,scroll:Boolean) = "home/$placeId/$city/$scroll"
    }
}


//object PostOfficeAppRouter {
//
//    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.LoginScreen)
//
//    fun navigateTo(destination : Screen){
//        currentScreen.value = destination
//    }
//
//
//}