package com.example.restaurantefastio2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class MenuItem(val imageId: Int, val text: String, val onClick: () -> Unit)

@Composable
fun TelaMenu(navController: NavHostController, mesaNumber: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        fontSize = 50.sp,
                        text = "Menu",
                        color = Color(0xFF333333)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Mesa $mesaNumber",
                        fontSize = 24.sp,
                        color = Color(0xFF333333),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }

            // Menu Items
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MenuRow(navController = navController, menuItems = listOf(
                    MenuItem(imageId = R.drawable.icon_e_removebg_preview, text = "Entradas", onClick = { navController.navigate("TelaEntradas/$mesaNumber") }),
                    MenuItem(imageId = R.drawable.icon_b_removebg_preview, text = "Bebidas", onClick = { navController.navigate("TelaBebidas/$mesaNumber") })
                ))
                MenuRow(navController = navController, menuItems = listOf(
                    MenuItem(imageId = R.drawable.icon_c_removebg_preview, text = "Prato Carne", onClick = { navController.navigate("TelaPratoCarne/$mesaNumber") }),
                    MenuItem(imageId = R.drawable.icon_p_removebg_preview, text = "Prato Peixe", onClick = { navController.navigate("TelaPratoPeixe/$mesaNumber") })
                ))
                MenuRow(navController = navController, menuItems = listOf(
                    MenuItem(imageId = R.drawable.icon_s_removebg_preview, text = "Sobremesas", onClick = { navController.navigate("TelaSobremesas/$mesaNumber") }),
                    MenuItem(imageId = R.drawable.icon_o_removebg_preview, text = "Outros", onClick = { navController.navigate("TelaOutros/$mesaNumber") })
                ))
            }

            // Action Buttons
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                ActionButton(text = "Conta", onClick = { navController.navigate("TelaConta/$mesaNumber") })
                ActionButton(text = "Voltar", onClick = { navController.navigateUp() })
            }
        }
    }
}

@Composable
fun MenuRow(navController: NavHostController, menuItems: List<MenuItem>) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        menuItems.forEach { menuItem ->
            MenuItem(
                menuItem = menuItem,
                navController = navController
            )
        }
    }
}

@Composable
fun MenuItem(menuItem: MenuItem, navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = menuItem.onClick)
    ) {
        Image(
            painter = painterResource(id = menuItem.imageId),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            fontSize = 18.sp,
            text = menuItem.text,
            color = Color(0xFF333333),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}



