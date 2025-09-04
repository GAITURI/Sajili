package com.mark.ui.kyc

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mark.ui.R
import kotlinx.coroutines.delay


val RESOURCE_IDS= mapOf(
    "id_card" to R.drawable.menu_edit,
    "selfie" to R.drawable.menu_camera,
    "info"  to R.drawable.info,
    "illustration_1" to R.drawable.kycproto,
    "illustration_2" to R.drawable.kycproto2



)
//@OptIn(ExperimentalPagerApi::class)
@Composable
fun IdentityVerificationScreen(
    onIdCardClick: ()->Unit,
    onSelfieClick: ()->Unit,
    onWhyNeededClick:()->Unit
){

Surface(
    modifier= Modifier
        .fillMaxSize(),
    color = Color(0xFFF0F4F7)
){
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
//        headerText
        Text(
            text = "VERIFY YOUR IDENTITY",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF1D5A5A),
            modifier = Modifier.padding(top=24.dp, bottom = 16.dp)
        )
//        Illustration Section-Pager for animated scrolling images
        val images= listOf(
            painterResource(id= RESOURCE_IDS["illustration_1"]!!),
            painterResource(id= RESOURCE_IDS["illustration_2"]!!)
        )
        val pagerState= rememberPagerState(pageCount = {images.size})

//        Auto-scroll effect(optional)
        LaunchedEffect(Unit) {
            while(true) {
                delay(3000)
                val nextPage=(pagerState.currentPage+1)% pagerState.pageCount
                pagerState.animateScrollToPage(nextPage)
            }

        }

    Box(
        modifier= Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ){
        HorizontalPager(
            state= pagerState,
            modifier= Modifier.fillMaxSize()
        )  { page->
            Image(
                painter = images[page],
                contentDescription = "KYC Illustration ${page+1}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop

            )

        }
//        pager indicator
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier= Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom=8.dp),
            activeColor= Color(0xFF1D5A5A),
            inactiveColor= Color.LightGray
        )
    }
    Spacer(modifier= Modifier.height(32.dp))
        // Body Text
        Text(
            text = "Please submit the following documents to complete the verification process",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier= Modifier.height(24.dp))
        // Option 1: Take a picture of a valid ID
        VerificationStepItem(
            title = "Take a picture of a valid ID",
            description = "To check your personal informations are correct",
            iconPainter = painterResource(id = RESOURCE_IDS["id_card"]!!),
            onClick = onIdCardClick
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Option 2: Take a selfie of yourself
        VerificationStepItem(
            title = "Take a selfie of yourself",
            description = "To match your face to your ID photo",
            iconPainter = painterResource(id = RESOURCE_IDS["selfie"]!!),
            onClick = onSelfieClick
        )
        Spacer(modifier = Modifier.weight(1f)) // Pushes content up
// "Why is this needed?" link
        TextButton(
            onClick = onWhyNeededClick,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        {
            Text(
                text = "Why is this needed?",
                color = Color(0xFF1A73E8), // Blue link color
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }


    }



}



}

//reusable composable for each verification step item
@Composable
fun VerificationStepItem(
    title: String,
    description: String,
    iconPainter: Painter,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Step Icon
        Icon(
            painter = iconPainter,
            contentDescription = null, // Content description handled by parent
            modifier = Modifier.size(24.dp),
            tint = Color(0xFF1D5A5A)
        )
        Spacer(modifier = Modifier.width(16.dp))
        // Title and Description
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
        // Arrow Icon
        Icon(
            painter = painterResource(id = android.R.drawable.ic_media_play), // Placeholder for forward arrow
            contentDescription = "Navigate forward",
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )
    }
}
@Composable
fun HorizontalPagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFF1D5A5A), // Your active color
    inactiveColor: Color = Color.LightGray,  // Your inactive color
    indicatorSize: Dp = 8.dp,
    spacing: Dp = 8.dp
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ensure pageCount is not zero to avoid issues with repeat
        val pageCount = pagerState.pageCount
        if (pageCount > 0) {
            repeat(pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) activeColor else inactiveColor
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color)
                        .size(indicatorSize)
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewIdentityVerificationScreen() {
    IdentityVerificationScreen(
        onSelfieClick = {},
        onIdCardClick = {},
        onWhyNeededClick = {}

    )
}