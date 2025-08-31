package com.mark.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mark.ui.ui.theme.SajiliTheme

class NewUserReg : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SajiliTheme {

            }
        }
    }
}

@Composable
fun NewUserReg(onConfirmPersonalClick:()-> Unit){
//    list of countries and counties
    val countries= listOf("Kenya","Uganda", "Rwanda")
    val kenyanCounties= listOf("Nairobi","Kisumu", "Kiambu","Mombasa", "Nakuru")
    val keyLocations= listOf("Kampala","Bujumbura","Dar Es Salaam")
//    state variables for form inputs
    var firstName by remember { mutableStateOf("") }
    var lastName  by remember { mutableStateOf("") }
    var emailReg by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var selectedCountry by remember { mutableStateOf("Kenya") }
    var selectedRegion by remember { mutableStateOf("") }


    var countryExpanded by remember { mutableStateOf(false) }
    var regionExpanded by remember { mutableStateOf(false) }

   Surface(
       modifier= Modifier.fillMaxSize(),
       color= MaterialTheme.colorScheme.background
   ) {
       Column(
           modifier= Modifier
               .fillMaxSize()
               .padding(16.dp),
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           LazyColumn(
               modifier= Modifier
                   .weight(1f)
                   .fillMaxWidth()
                   .padding(horizontal = 8.dp)
           ){
//               header with back button
               item{
                   Row(
                       modifier= Modifier
                           .fillMaxWidth()
                           .padding(bottom=24.dp),
                       verticalAlignment= Alignment.CenterVertically
                   ){
                       IconButton(onClick = {   TODO() }) {


                           Icon(
                               imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                               contentDescription = "Back",
                               tint= Color.Gray
                           )
                       }
                       Text(
                           text = "Identity Verification",
                           fontSize = 20.sp,
                           fontWeight = FontWeight.SemiBold,
                           color= Color.Black,
                           modifier= Modifier.padding(start = 8.dp,)
                       )
                   }
               }
//               Personal Information Section
               item {
                   FormSection(title = "Personal Information") {
                       OutlinedTextField(
                           value = firstName,
                           onValueChange = { firstName = it },
                           label = { Text("First Name") },
                           modifier = Modifier.fillMaxWidth(),
                           singleLine = true

                       )
                       Spacer(modifier= Modifier.height(16.dp))
                       OutlinedTextField(
                           value= lastName,
                           onValueChange = {lastName=it},
                           label = { Text("Last Name") },
                           modifier= Modifier.fillMaxWidth(),
                           singleLine = true
                       )
                       Spacer(modifier= Modifier.height(16.dp))
                        OutlinedTextField(
                            value= emailReg,
                            onValueChange = {emailReg= it},
                            label={ Text("Email Address") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                       Spacer(modifier= Modifier.height(16.dp))
                        OutlinedTextField(
                            value= phoneNumber,
                            onValueChange = {phoneNumber=it},
                            label= { Text("Phone Number") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                   }
               }
               item{
                    Spacer(modifier= Modifier.height(24.dp))
               }

//               Address Information Section
               item {
                   FormSection(title = "Address Information") {
                       OutlinedTextField(
                           value = address,
                           onValueChange = { address = it },
                           label = { Text("Address") },
                           modifier = Modifier.fillMaxWidth(),
                           singleLine = true
                       )
                       Spacer(modifier = Modifier.height(16.dp))

//                   Country Dropdown
                   Box(
                       modifier = Modifier
                           .fillMaxWidth()
                           .clickable{countryExpanded= true}
                           .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                           .padding(16.dp)
                   ){
                  Row(verticalAlignment = Alignment.CenterVertically) {
                       Text(
                           text= selectedCountry,
                           modifier = Modifier.weight(1f)
                       )
                       Icon(
                           imageVector = Icons.Default.ArrowDropDown,
                           contentDescription = "Country dropdown"
                       )
                   }
                   DropdownMenu(
                       expanded = countryExpanded,
                       onDismissRequest = {countryExpanded= false},
                       modifier = Modifier.fillMaxWidth(0.9f)
                   ) {
                       countries.forEach { country ->
                           DropdownMenuItem(
                               text = { Text(country) },
                               onClick = {
                                   selectedCountry = country
                                   countryExpanded = false
                                   selectedRegion = ""
                               }
                           )
                       }
                   }
                }

                   Spacer(modifier= Modifier.height(16.dp))

//                   Region Dropdown(conditionally shown based on country
                   val regions= when(selectedCountry){
                       "Kenya" ->kenyanCounties
                       "Uganda" -> keyLocations
                       "Rwanda"->keyLocations
                       else-> emptyList()

                   }
                   if (regions.isNotEmpty()){
                       Box(
                           modifier= Modifier
                               .fillMaxWidth()
                                .clickable{regionExpanded= true}
                               .border(1.dp,Color.LightGray, RoundedCornerShape(8.dp))
                               .padding(8.dp)
                       ) {
                           Row(verticalAlignment = Alignment.CenterVertically) {
                               Text(
                                   text = selectedRegion.ifEmpty { "Select Region" },
                                   modifier = Modifier.weight(1f)
                               )
                               Icon(
                                   imageVector = Icons.Default.ArrowDropDown,
                                   contentDescription = "Region dropdown"
                               )
                           }


                           DropdownMenu(
                               expanded = regionExpanded,
                               onDismissRequest = { regionExpanded = false },
                               modifier = Modifier.fillMaxWidth(0.9f)
                           ) {
                               regions.forEach { region ->
                                   DropdownMenuItem(
                                       text = { Text(region) },
                                       onClick = {
                                           selectedRegion = region
                                           regionExpanded = false
                                       }
                                   )

                               }
                           }
                       }
                       }
                   }
               }

           }
//           confirm verification button at the  bottom
           Button(
               onClick = onConfirmPersonalClick,
               modifier =Modifier
                   .fillMaxWidth()
                   .height(56.dp)
                   .clip(RoundedCornerShape(12.dp)),
               colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8))
           ) {
               Text(
                   text = "Confirm Verification",
                   fontSize = 16.sp,
                   fontWeight = FontWeight.Bold,
                   color= Color.White
               )
           }
       }


   }

}

@Composable
fun FormSection(title: String, content: @Composable () ->Unit) {
    Column(
        modifier= Modifier.fillMaxWidth()
    ) {
        Text(
            text= title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        content()
    }

}
@Preview(showBackground = true)
@Composable
fun PreviewKycFormScreen(){
    NewUserReg(onConfirmPersonalClick = {})
}
