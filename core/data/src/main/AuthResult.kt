import androidx.lifecycle.ViewModel

sealed class AuthResult {
object Loading:AuthResult()
    data class Success(val message:String? = null, val authResponse:AuthResponse?= null): AuthResult()
    data class Error(val message: String?):AuthResult()
    object Idle:AuthResult()

}
class AuthViewModel: ViewModel(){
    //mutable stateflow to hold the current state of authentication operations
    private val _registrationState= MutableStateFlow<AuthResult>(AuthResult.Idle)
    val registrationState:StateFlow<AuthResult> = _registrationState

    private val _loginState = MutableStateFlow<AuthResult>(AuthResult.Idle)










}