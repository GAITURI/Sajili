package com.mark.sajili

import android.app.Application
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mark.data.Profile
import com.mark.sajili.ui.theme.SajiliTheme
import com.mark.ui.AuthAgentDestination
import com.mark.ui.CustomerDashboard.CustomerDashboardScreen
import com.mark.ui.LoginScreen
import com.mark.ui.RegistrationScreen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp(){
//    the nav controller is created once and used for all navigation
    val navController= rememberNavController()
    NavHost(navController= navController, startDestination= AuthAgentDestination.LOGIN_ROUTE){
//        COMPOSABLE FOR THE Login Screen, which is in the "core:ui" module
    composable(AuthAgentDestination.LOGIN_ROUTE){
        LoginScreen(
            onLoginSuccess ={_, _ ->
//                on successful login navigate to the customer dashboard
                navController.navigate(AuthAgentDestination.CUSTOMER_DASHBOARD_ROUTE)
            },
            onForgotPasswordClick={},
            onSignUpClick={
//                navigate to the registration screen
                navController.navigate(AuthAgentDestination.REGISTRATION_ROUTE)
            }

        )
    }
    composable(AuthAgentDestination.REGISTRATION_ROUTE){
        RegistrationScreen(
            onConfirmRegistrationClick={_, _, _ ->
//                once user is successfully registered navigate back to login
                        navController.navigate(AuthAgentDestination.LOGIN_ROUTE)
                                       },
            onLoginClick={
                navController.navigate(AuthAgentDestination.LOGIN_ROUTE)

            }
        )

    }
//        Composable for the Registration Screen,
        composable(AuthAgentDestination.CUSTOMER_DASHBOARD_ROUTE){
            CustomerDashboardScreen(
                profileService = TODO()
            )
        }

    }
}

