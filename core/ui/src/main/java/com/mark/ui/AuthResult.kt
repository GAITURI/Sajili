package com.mark.ui

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.mark.data.AuthResponse
import com.mark.data.AuthService
import com.mark.data.FirebaseTokenRequest
import com.mark.data.NetworkModule.safeApiCall
import com.mark.data.RegisterRequest
import com.mark.data.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject


//managing authentication state in an Android application
//it models every possible authentication state the UI can be in
sealed class AuthResult {
    data object Loading: AuthResult() //on-going authentication work
    data class Success(val message:String? = null, val authResponse: AuthResponse?= null): AuthResult()
    data class Error(val message: String?,val statusCode:Int?): AuthResult()
    data object Idle: AuthResult() //this is the initial state
    data object  CodeSent:AuthResult()
}
//profile responseUI State Sealed class

@HiltViewModel
class AuthViewModel @Inject constructor(
private val authService:AuthService, //inject AuthService (retrofit backend service)
private val tokenRepository: TokenRepository //inject token repository
): ViewModel(){
private val auth= FirebaseAuth.getInstance() //handles sms verification and issues firebase ID tokens
private val _authState= MutableStateFlow<AuthResult>(AuthResult.Idle)
val authState=_authState.asStateFlow()
//UI input states (mutable, used by COMPOSE UI) the UI observes authState and redraws automatically

    var phoneNumberInput by mutableStateOf("")
    var smsCodeInput by mutableStateOf("")
    var pinInput by mutableStateOf("") //temporary storage for r
    private var verificationId:String?=null //stores Firebase verification session ID R

//user authentication status and JWT token Token persistence &auth states
    var isAuthenticated by mutableStateOf(false)
    var jwtToken by mutableStateOf<String?>(null)
//it enables auto-login and session restoration after app restarts
    init {
        jwtToken= tokenRepository.getToken()
        isAuthenticated= jwtToken != null
    }
    private suspend fun registerWithBackend(idToken: String){
//        creates a backend registration payload
        val request= RegisterRequest(
            idToken = idToken, //firebase ID token proves phone ownership
            phoneNumber = phoneNumberInput, //associates user with phone number
            pin = pinInput,
            confirmPin = pinInput
        )
        //executes a backend call safely
        val result = safeApiCall { authService.register(request) }
        result.onSuccess{
//        this success state is what triggers the Pop-up in the UI
                authResponse-> _authState.value= AuthResult.Success("Registration Successful!", authResponse)
            pinInput=""
        }.onFailure {throwable->
            _authState.value= AuthResult.Error(throwable.message?: "Registration Failed", null)
        }
    }
//func to handle the first step: send the SMS code
//    the UI Enters Loading
    fun sendVerificationCode(activityContext:Context){
        _authState.value= AuthResult.Loading //initial function state
        val activity= activityContext as? ComponentActivity
       if(activity == null){
           _authState.value= AuthResult.Error("Activity context is required for Firebase phone auth ", null)
        return
       }
    //begin Firebase phone auth configuration
      val options= PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumberInput)
            .setTimeout(100L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override  fun onVerificationCompleted(credential: PhoneAuthCredential){
                       viewModelScope.launch {
                           signInWithFirebase(credential)
                       }
                    }
                override fun onVerificationFailed(e: FirebaseException){
                    _authState.value= AuthResult.Error(e.message, null)
                }
                override fun onCodeSent(id:String, token:PhoneAuthProvider.ForceResendingToken){
                    _authState.value= AuthResult.CodeSent
                    verificationId= id
                }

            })
            .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
    }
    fun verifySmsCode(){
        _authState.value= AuthResult.Loading
        if (verificationId== null  || smsCodeInput.isBlank()){
            _authState.value= AuthResult.Error("Sms Code orVerification Id is missing",null)
       return
        }
        val credential= PhoneAuthProvider.getCredential(verificationId!!, smsCodeInput)
        viewModelScope.launch{
            signInWithFirebase(credential)
        }
    }


//helper function to sign in with firebase and then verify the token with my backend
    private suspend fun signInWithFirebase(credential: PhoneAuthCredential) {
        try{
            val authResult= auth.signInWithCredential(credential).await()
            val idToken= authResult.user?.getIdToken(true)?.await()?.token
            if(idToken != null){
                if (pinInput.isNotEmpty()){
                    registerWithBackend(idToken)
                }else{
                verifyTokenWithBackend(idToken)
                }
            }else{
                _authState.value= AuthResult.Error("Failed to get Firebase ID token", null)
            }
        }catch (e:Exception){
            _authState.value= AuthResult.Error(e.message, null)
        }
    }

//    Final step:send the Firebase token to your backend
    private suspend fun verifyTokenWithBackend(idToken: String) {
        val request= FirebaseTokenRequest(idToken)
        val result = safeApiCall { authService.verifyToken(request) }
        result.onSuccess { authResponse->
            val newJwtToken= authResponse.jwt
            if(newJwtToken != null){
                tokenRepository.saveToken(newJwtToken)
                jwtToken= newJwtToken
                isAuthenticated=true
                _authState.value= AuthResult.Success("Login Successful!", authResponse)
        }else{
            _authState.value= AuthResult.Error("Backend did not provide a jwt Token.", null)
        }

        }.onFailure { throwable->
            val errorMessage= throwable.message?:"Failed to verify token with backend."
            _authState.value=AuthResult.Error(errorMessage, null)

        }


    }



    fun clearAuthStates() {
        _authState.value = AuthResult.Idle
        phoneNumberInput = ""
        smsCodeInput = ""
        pinInput=""
        jwtToken = null // Clear token
        isAuthenticated = false // Set to false
    }


}




