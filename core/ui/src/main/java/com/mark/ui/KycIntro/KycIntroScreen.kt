package com.mark.ui.KycIntro

import android.os.Bundle
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mark.ui.KycIntro.ui.theme.SajiliTheme
import com.mark.ui.R

@Composable
fun KycIntroScreen(
    onBackClick:() ->Unit,
    onBeginClick:()-> Unit
){
//    applying app's theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color= MaterialTheme.colorScheme.background
    ){
        Column(
            modifier= Modifier
                .fillMaxSize()
                .padding(24.dp)

        ) {
            //header with back button
            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onBackClick),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow),
                    contentDescription = stringResource(R.string.back_to_dashboard),
                    modifier =Modifier.size(24.dp)
                )
                Spacer(modifier= Modifier.width(8.dp))
                Text(
                    text="Register User",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text="Verify identity and Comply with Regulations",
                    style= MaterialTheme.typography.bodyLarge,
                    color= MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top= 8.dp)

                )
                Spacer(modifier= Modifier.height(48.dp))
            }
        }
    }
}