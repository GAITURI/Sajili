package com.mark.ui.KycIntro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mark.ui.KycIntro.ui.theme.SajiliTheme
import com.mark.ui.R

val ICONS= mapOf(
    "back" to R.drawable.ic_back_arrow,
    "personal" to R.drawable.ic_stat_name,
    "identity" to R.drawable.ic_stat_account,
    "check" to R.drawable.ic_stat_check,
    "bolt" to R.drawable.ic_stat_bolt
)



@Composable
fun KycIntroScreen(
    onBackClick:() ->Unit,
    onConfirmVerificationClick:()-> Unit={}
) {
//    applying app's theme
    MaterialTheme(colorScheme = lightColorScheme()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)

            ){
                //header with back button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onBackClick),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = ICONS["back"]!!),
                        contentDescription = stringResource(R.string.back_to_dashboard),
                        modifier = Modifier.size(24.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Identity Verification",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black

                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
//            The two step items for personal info and identity verification
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    KycStepItem(
                        title = "Personal Information",
                        description = "Enter details and provide the documents",
                        isComplete = false,
                        iconPainter = painterResource(id = ICONS["personal"]!!)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    KycStepItem(
                        title = "Identity Verification",
                        description = "Scan Id to verify Identity",
                        isComplete = false,
                        iconPainter = painterResource(id = ICONS["identity"]!!)
                    )
                }
                    Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier= Modifier
                        .fillMaxWidth()
                        .padding(bottom=16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter= painterResource(id= ICONS["bolt"]!!),
                        contentDescription = "Bolt Icon",
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFFCC201)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                Text(
                        text = "It will take just a few minutes",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                //Main CTA Button
                Button(
                    onClick = onConfirmVerificationClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(MaterialTheme.shapes.extraLarge),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8))
                ) {
                    Text(
                        text = "Confirm Verification",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

            }
        }
    }
}
//reusable composable for a single KYC step item
@Composable
fun KycStepItem(
    title:String,
    description:String,
    isComplete:Boolean,
    iconPainter:Painter
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                shape = MaterialTheme.shapes.large
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
//    left side icon
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(if (isComplete) Color.Green else Color(0xFF1A73E8)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = iconPainter,
                contentDescription = title,
                modifier = Modifier.size(24.dp),
                tint = if (isComplete) Color.White else Color(0xFFF0F4F7)

            )
        }
//Title and Description
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
//status icon on the right
        Icon(
            painter = painterResource(id = ICONS["check"]!!),
            contentDescription = "Status",
            modifier= Modifier.size(24.dp),
            tint = if (isComplete) Color.Green else Color(0xFFF0F4F7)
            )
    }
}

@Preview(showBackground = true)
@Composable
fun KycIntroScreenPreview(){
    KycIntroScreen(
        onBackClick = {},
        onConfirmVerificationClick = {}
    )

}