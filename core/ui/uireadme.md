# The UI Readme.md explains how the UI structure of the project connects
## 📂 Project Structure

### 1.UI Layer
This is the visible part of the app where the user interacts with the application

**Files:**
-`LoginScreen.kt` 
- `RegistrationScreen.kt`


    - These are composable functions that define the layout and appearance of the login and registration screens
    -They are responsible for collecting user input and displaying the UI State
    - They don't do any networking or data storage themselves,they simply call functions and react to changes in the login state
    -Using the hiltViewModel() returns an existing HiltViewModel  -annotated ViewModel or creates a new one scoped to the current navigation graph present on the {@link NavController} back stack.

- `AuthViewModel.kt`
- This class holds the state of the UI and the logic for authentication.
- Its a Hilt injected class(@HiltViewModel) that receives its dependencies(authService) to make a network call.
-When the UI calls function, the ViewModel uses the injected authService to make network calls.
- If the call succeeds, it uses the injected tokenRepository to save the JWT token
- It then updates its stateFlow(_loginState), which the LoginScreen is observing causing the UI to change.
