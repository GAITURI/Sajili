package com.mark.ui.KycIntro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.mark.ui.KycIntro.ui.theme.SajiliTheme
import kotlin.math.exp

class ServiceSelectionKycFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container:ViewGroup?,savedInstanceState:Bundle?):View {
        return ComposeView(requireContext()).apply {
            setContent {
                ServiceSelectionKycScreen(
                    onProceedClick={
//                        handle form submission and navigation
                    },
                    onBackClick={
//                        hanlde back navigation
                    }
                )
            }

            }
        }

        }
// main composable for service selected

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceSelectionKycScreen(
    onProceedClick:() ->Unit,
    onBackClick:()->Unit
) {
//    a list of services for demo
    val mainServices = listOf("Main Service A", "Main Service B")
    val subServicesA = listOf("Sub-Service A1", "Sub-Service A2")
    val subServicesB = listOf("Sub-Service B1", "Sub-Service B2", "Sub-Service B3")

//    stat variables for form inputs
    var selectedMainService by remember { mutableStateOf(mainServices.first()) }
    var selectedSubServiceOne by remember { mutableStateOf("") }
    var selectedSubServiceTwo by remember { mutableStateOf("") }
    Surface(

        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            The header with the back button
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Gray
                    )
                }
                Text(
                    text = "Select Services",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

//        Main Service dropdown feature-list
            Text(
                text = "Select Main Service",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                style = MaterialTheme.typography.titleMedium
            )
            CustomDropdownMenu(
                options = mainServices,
                selectedOption = selectedMainService,
                onOptionSelected = {
                    selectedMainService = it
                    //reset sub-services when main service changes
                    selectedSubServiceOne = ""
                    selectedSubServiceTwo = ""
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
//        sub-service 1dropdown
            val currentSubServices = if (selectedMainService == "Main Service A") {
                subServicesA
            } else {
                subServicesB
            }
            Text(
                text = "Select Sub-Service One",
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                style = MaterialTheme.typography.titleMedium

            )
            CustomDropdownMenu(
                options = currentSubServices,
                selectedOption = selectedSubServiceOne,
                onOptionSelected = { selectedSubServiceOne = it }

            )
            Spacer(modifier = Modifier.height(24.dp))
//            Sub-service 2 Dropdown
            Text(
                text = "Select Sub-Service Two",
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                style = MaterialTheme.typography.titleMedium
            )
            CustomDropdownMenu(
                options = currentSubServices,
                selectedOption = selectedSubServiceTwo,
                onOptionSelected = { selectedSubServiceTwo = it }
            )
            Spacer(modifier = Modifier.weight(1f))
//        Proceed Button
            Button(
                onClick = onProceedClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(MaterialTheme.shapes.extraLarge),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8))

            ) {
                Text(
                    text = "Proceed",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
//reusable composable for a styled dropdown menu
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownMenu(
    options:List<String>,
    selectedOption:String,
    onOptionSelected:(String)-> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded= expanded,
        onExpandedChange = { expanded= it }
    ) {

        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth().menuAnchor(),
            label = { Text("Select Option") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)

            },
            singleLine = true
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )

            }
        }

    }
}
@Preview(showBackground = true)
@Composable
fun PreviewServiceSelectionScreen(){
    ServiceSelectionKycScreen(onProceedClick = {}, onBackClick = {})
}