package com.mark.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthResult {
object Loading: AuthResult()
    data class Success(val message:String? = null, val authResponse: AuthResponse?= null): AuthResult()
    data class Error(val message: String?): AuthResult()
    object Idle: AuthResult()

}
class AuthViewModel: ViewModel(){
    //mutable stateflow to hold the current state of authentication operations
    private val _registrationState= MutableStateFlow<AuthResult>(AuthResult.Idle)
    val registrationState: StateFlow<AuthResult> = _registrationState.asStateFlow()

    private val _loginState = MutableStateFlow<AuthResult>(AuthResult.Idle)
    val loginState:StateFlow<AuthResult> = _loginState.asStateFlow()

//UI input states (mutable, used by COMPOSE UI)
    var phoneNumberInput by mutableStateOf("")
    var passwordInput by mutableStateOf("")



//user authentication status and JWT token
    var isAuthenticated by mutableStateOf(false)
    var jwtToken by mutableStateOf<String?>(null)


    //function to get the authenticated API service(provides current JWT token)
    private fun getAuthenticatedApiService()= RetrofitClient.getApiService{jwtToken}



    fun register(){
        _registrationState.value= AuthResult.Loading
        viewModelScope.launch{
            try {
                val request = RegisterRequest(phoneNumber,pin, confirmPin)
                val result = RetrofitClient.safeApiCall { RetrofitClient.authService.register(request) }

            result.onSuccess { response ->
                _registrationState.value = AuthResult.Success(response.message)
                //clear fields after successful registration
                phoneNumberInput =""
                passwordInput =""


            }.onFailure { throwable ->
                val errorMessage = when(throwable){
                    is SajiliApiException ->throwable.message ?:"Registration failed"
                    else -> "An unexpected error occurred: ${throwable.message}"
                }
                val statusCode = (throwable as? SajiliApiException)?.statusCode
                _registrationState.value = AuthResult.Error(errorMessage, statusCode)
                println("Registration Error: ${throwable.stackTraceToString()}")

            }
            }
            catch (e:Exception){
                _registrationState.value = AuthResult.Error("Network error: ${e.message}")
                println("Network Error during registration: ${e.stackTraceToString()}")
            }
        }
    }

fun login(){
    _loginState.value = AuthResult.Loading
    viewModelScope.launch {
        try{
            val request = LoginRequest(phoneNumber,pin)
            val result = RetrofitClient.safeApiCall { RetrofitClient.authService.login(request) }
        result.onSuccess { authResponse->
            jwtToken= authResponse.jwt // store token
            isAuthenticated = jwtToken !=null //update auth status
            _loginState.value = AuthResult.Success("Login Successful!", authResponse)

            passwordInput=""

        }.onFailure { throwable ->
            val errorMessage = when (throwable){
                is SajiliApiException ->throwable.message?: "Login failed."
                else -> "An unexpected error occurred: ${throwable.message}"
            }
            val statusCode = (throwable as? SajiliApiException)?.statusCode
                _loginState.value = AuthResult.Error(errorMessage, statusCode)
                println("Login error:{throwable.stackTraceToString()}")


        }

        } catch (e:Exception){
            _loginState.value= AuthResult.Error("Network error: ${e.message}")
            println("Network error during login: ${e.stackTraceToString()}")
        }
    }
}
    // Example of using a protected endpoint
//    fun getProtectedData() {
//        if (jwtToken.isNullOrBlank()) {
//            _loginState.value = AuthResult.Error("Not authenticated. Please log in.")
//            return
//        }
//        _loginState.value = AuthResult.Loading // Can use a separate state for data loading if preferred
//        viewModelScope.launch {
//            try {
//                // Use the authenticated API service
//                val result = RetrofitClient.safeApiCall {
//                    getAuthenticatedApiService().getProtectedData("Bearer $jwtToken")
//                }
//
//                result.onSuccess { messageResponse ->
//                    _loginState.value = AuthResult.Success(messageResponse.message)
//                }.onFailure { throwable ->
//                    val errorMessage = when (throwable) {
//                        is SajiliApiException -> throwable.message ?: "Failed to fetch data."
//                        else -> "An unexpected error occurred: ${throwable.message}"
//                    }
//                    val statusCode = (throwable as? SajiliApiException)?.statusCode
//                    _loginState.value = AuthResult.Error(errorMessage, statusCode)
//                    println("Protected Data Error: ${throwable.stackTraceToString()}")
//                    if (throwable is SajiliApiException && throwable.statusCode == 401) {
//                        logout() // Log out if token is invalid/expired
//                    }
//                }
//            } catch (e: Exception) {
//                _loginState.value = AuthResult.Error("Network error: ${e.message}")
//                println("Network Error fetching protected data: ${e.stackTraceToString()}")
//            }
//        }
//    }

fun logout(){
    jwtToken = null
    isAuthenticated = false
    phoneNumberInput=""
    passwordInput=""
    _loginState.value= AuthResult.Idle
    _registrationState.value= AuthResult.Idle
}
    fun clearAuthStates(){
        _registrationState.value= AuthResult.Idle
        _loginState.value = AuthResult.Idle
    }
}