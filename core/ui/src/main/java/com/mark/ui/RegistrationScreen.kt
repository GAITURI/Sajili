package com.mark.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    onConfirmRegistrationClick: (String, String, String)-> Unit,
    onLoginClick: ()->Unit
){
    var phoneNumber by remember{mutableStateOf("")}
    var pin by remember { mutableStateOf("") }
    var confirmPin by remember {mutableStateOf("")}
    var pinVisibility by remember { mutableStateOf(false) }
    var confirmPinVisibility by remember{ mutableStateOf(false) }

    Scaffold(
        topBar = { AppToolbar(title = stringResource(id= R.string.register)) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "BackgroundImage",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            //content box with card
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {
                Spacer(modifier = Modifier.height(80.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.85F)
                        .align(Alignment.Center)
                        .offset(y = (-20).dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp)
                            .background(Color.White, RoundedCornerShape(30.dp)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.register),
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(60.dp))

                        //phone number input textfield
                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            label = { Text(stringResource(id = R.string.phoneNumber)) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Call,
                                    contentDescription = "Phone Icon"
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp)
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        //PIN Input Field
                        OutlinedTextField(
                            value = pin,
                            onValueChange = { pin = it },
                            label = { Text(stringResource(id = R.string.enter_pin)) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = "Lock Icon"
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            visualTransformation = if (pinVisibility) VisualTransformation.None else PasswordVisualTransformation(),
//                            trailingIcon = {
//                                val image =
//                                    if (pinVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
//                                IconButton(onClick = { pinVisibility = !pinVisibility }) {
//                                    Icon(
//                                        imageVector = image,
//                                        contentDescription = "TogglePin Visibility"
//                                    )
//                                }
//                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))


                        //confirm PIN Input Field
                        OutlinedTextField(
                            value = confirmPin,
                            onValueChange = { confirmPin = it },
                            label = { Text(stringResource(id = R.string.confirm_pin)) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = "Lock Icon"
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            visualTransformation = if (confirmPinVisibility) VisualTransformation.None else PasswordVisualTransformation(),
//                            trailingIcon = {
//                                val image =
//                                    if (confirmPinVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
//                                IconButton(onClick = {
//                                    confirmPinVisibility = !confirmPinVisibility
//                                }) {
//                                    Icon(
//                                        imageVector = image,
//                                        contentDescription = "Toggle Confirm Pin Visibility"
//                                    )
//                                }
//                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp)
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Button(
                            onClick = { onConfirmRegistrationClick(phoneNumber, pin, confirmPin) },
                            modifier = Modifier
                                .width(300.dp)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(20.dp),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.confirm_registration),
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        //already have an account? Login text
                        Text(
                            text = stringResource(id = R.string.already_have_account_login),
                            color = Color.Black,
                            modifier = Modifier
                                .padding(bottom = 40.dp)
                                .clickable { onLoginClick() }
                                .align(Alignment.CenterHorizontally)
                        )

                    }
                }
            }

        }

    }
}
@Preview(showBackground= true, name= "Registration Screen Preview")
@Composable
fun RegistrationScreenPreview(){
    RegistrationScreen(
        onConfirmRegistrationClick = {phoneNumber, pin, confirmPin->

        },
        onLoginClick = {

        }
    )
}