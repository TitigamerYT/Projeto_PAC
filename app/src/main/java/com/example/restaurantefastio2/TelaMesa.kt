package com.example.restaurantefastio2

import ViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect

@Composable
fun TelaMesa(navController: NavController, viewModel: ViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.loadPedidosMesas()
    }

    val pedidosMesas by viewModel.pedidos_mesas.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp)
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                modifier = Modifier.padding(16.dp),
                fontSize = 30.sp,
                text = "Bem-vindos ao Restaurante Fastio",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF3E2723)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(20.dp),
                fontSize = 24.sp,
                text = "Escolha a sua mesa",
                textAlign = TextAlign.Center,
                color = Color(0xFF6D4C41)
            )
            Spacer(modifier = Modifier.height(16.dp))

            val mesasComPedidos = pedidosMesas.map { it.id_mesa }.toSet()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MesaButton(navController, 1, mesasComPedidos.contains(1))
                MesaButton(navController, 2, mesasComPedidos.contains(2))
                MesaButton(navController, 3, mesasComPedidos.contains(3))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MesaButton(navController, 4, mesasComPedidos.contains(4))
                MesaButton(navController, 5, mesasComPedidos.contains(5))
                MesaButton(navController, 6, mesasComPedidos.contains(6))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MesaButton(navController, 7, mesasComPedidos.contains(7))
                MesaButton(navController, 8, mesasComPedidos.contains(8))
                MesaButton(navController, 9, mesasComPedidos.contains(9))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("TelaTodasAsContas") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8D6E63)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(12.dp))
                    .height(50.dp)
            ) {
                Text(
                    text = "Todas as contas",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MesaButton(navController: NavController, mesaNumber: Int, hasPedidos: Boolean) {
    Button(
        onClick = { navController.navigate("TelaMenu/$mesaNumber") },
        colors = ButtonDefaults.buttonColors(containerColor = if (hasPedidos) Color.Red else Color(0xFF8D6E63)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .shadow(8.dp, RoundedCornerShape(12.dp))
            .height(50.dp)
    ) {
        Text(
            text = "Mesa $mesaNumber",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


