package com.mark.sajili

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mark.ui.AuthAgentDestination
import com.mark.ui.AuthViewModel
import com.mark.ui.ConfirmPopUp
import com.mark.ui.CustomerDashboard.CustomerDashboardScreen
import com.mark.ui.LoginScreen
import com.mark.ui.RegistrationScreen
import dagger.hilt.android.AndroidEntryPoint


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
    val authViewModel: AuthViewModel= hiltViewModel()
    NavHost(navController= navController, startDestination= AuthAgentDestination.LOGIN_ROUTE){
        composable(AuthAgentDestination.CONFIRM_OTP_ROUTE){
            ConfirmPopUp(
                onVerificationSuccess = { jwtToken ->
                    navController.navigate(AuthAgentDestination.CUSTOMER_DASHBOARD_ROUTE) {
                        popUpTo(AuthAgentDestination.LOGIN_ROUTE) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(AuthAgentDestination.LOGIN_ROUTE)
                },
                viewModel = TODO()
            )
        }

        //        COMPOSABLE FOR THE Login Screen, which is in the "core:ui" module

    composable(AuthAgentDestination.LOGIN_ROUTE){
        LoginScreen(
            onLoginSuccess ={_, _ ->
//                on successful login navigate to the customer dashboard
                navController.navigate(AuthAgentDestination.CUSTOMER_DASHBOARD_ROUTE){
                    popUpTo(AuthAgentDestination.LOGIN_ROUTE){inclusive= true}
                }
            },
            onForgotPasswordClick={
                navController.navigate(AuthAgentDestination.CUSTOMER_DASHBOARD_ROUTE)
            },
            onSmsCodeSent = {
                navController.navigate(AuthAgentDestination.CONFIRM_OTP_ROUTE)
            },
            onSignUpClick={
//                navigate to the registration screen
                navController.navigate(AuthAgentDestination.REGISTRATION_ROUTE)
            }

        )
    }

    composable(AuthAgentDestination.REGISTRATION_ROUTE){

        RegistrationScreen(
            viewModel = authViewModel,
            onSmsCodeSent ={
//                when sms is sent, navigate to the verification popup/screen
                navController.navigate(AuthAgentDestination.CONFIRM_OTP_ROUTE)
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

