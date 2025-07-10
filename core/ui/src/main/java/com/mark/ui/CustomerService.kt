package com.mark.ui

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//data class to represent a customer service item
data class CustomerServiceItem(
    val icon: ImageVector,
    val text:String,
    val onClick: ()-> Unit={}
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDashboardScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {}) {
                            androidx.compose.material3.Icon(
                                Icons.Default.List,
                                contentDescription = "Menu"
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text("User", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Text("XXXXXXX", fontSize = 14.sp)
                        }
                    }
                },
                actions = {
                    //a bell icon for notification
                    Box(modifier = Modifier.padding(end = 8.dp)) {
                        IconButton(onClick = {}) {
                            androidx.compose.material3.Icon(
                                Icons.Default.Notifications,
                                contentDescription = "Notifications"
                            )
                        }
                        //notification badge
                        Text(
                            text = "2",
                            color = Color.White,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = (-4).dp, y = 4.dp)
                                .background(Color.Red, RoundedCornerShape(50))
                        )

                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE50000),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                //Home
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        androidx.compose.material3.Icon(
                            Icons.Default.Home,
                            contentDescription = "HomeIcon"
                        )
                    },
                    label = { Text("Home") }
                )
                //MyAccount
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = {
                        androidx.compose.material3.Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = "My Account"
                        )
                    },
                    label = { Text("MyAccount") }
                )
                //transactions
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = {
                        androidx.compose.material3.Icon(
                            Icons.Default.List,
                            contentDescription = "Transaction"
                        )
                    },
                    label = { Text("Transaction") }
                )
                //activities
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = {
                        androidx.compose.material3.Icon(
                            Icons.Default.Info,
                            contentDescription = "Activities"
                        )
                    },
                    label = { Text("Activities") }
                )
                //reports
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = {
                        androidx.compose.material3.Icon(
                            Icons.Default.Settings,
                            contentDescription = "Reports"
                        )
                    },
                    label = { Text("Reports") }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF0F0F0))
        ) {
            //promote and earn section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(100.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            )
            {
                Row(
                    modifier= Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Column {
                        Text("Promote & Earn", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Explore >",
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.clickable {}
                        )

                    }
                    androidx.compose.material3.Icon(
                        Icons.Default.Search,
                        contentDescription = "Promote PlaceHolder",
                        tint = Color.Gray,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            //customer service label
            Text(
                text = "Customer Service",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            //customer service grid
            val customerServiceItems = listOf(
                CustomerServiceItem(Icons.Default.AccountCircle, "Existing Client"),
                CustomerServiceItem(Icons.Default.Add, "New KYC Client"),
                CustomerServiceItem(Icons.Outlined.Notifications,"SIM SWAP"),
                CustomerServiceItem(Icons.Outlined.MailOutline, "Viral Voucher Cash Out"),
                CustomerServiceItem(Icons.Outlined.ShoppingCart, "Cash In"),
                CustomerServiceItem(Icons.Outlined.Phone, "Airtime/Bundle"),
                CustomerServiceItem(Icons.Outlined.PlayArrow, "Agent Float Transfer"),
                CustomerServiceItem(Icons.Outlined.ShoppingCart, "Bill Payments"),
                CustomerServiceItem(Icons.Outlined.CheckCircle, "Retailer EVD Sales"),
                CustomerServiceItem(Icons.Outlined.DateRange, "MNP"),
                CustomerServiceItem(Icons.Outlined.Delete, "De-Registration")

            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(customerServiceItems.size) { index ->
                    val item = customerServiceItems[index]
                    CustomerServiceGridItem(item)
                }
            }
                }
    }

}
@Composable
fun CustomerServiceGridItem(item:CustomerServiceItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable(onClick = item.onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            androidx.compose.material3.Icon(
                imageVector = item.icon,
                contentDescription = item.text,
                modifier = Modifier.size(48.dp),
                tint = Color.Red
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = item.text,
                fontSize = 12.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )

        }

    }
}
@Preview(showBackground = true)
@Composable
fun CustomerScreenPreview(){
    CustomerDashboardScreen()
}




