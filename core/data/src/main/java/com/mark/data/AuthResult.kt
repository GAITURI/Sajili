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

            }
        }
    }





}