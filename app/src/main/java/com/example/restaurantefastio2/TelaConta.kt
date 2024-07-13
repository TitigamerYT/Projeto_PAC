package com.example.restaurantefastio2

import ViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import com.example.restaurantefastio2.ui.theme.RestauranteFastio2Theme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TelaConta(navController: NavController, mesaNumber: Int, viewModel: ViewModel = viewModel()) {
    val pedidosMesa by viewModel.pedidos_mesas.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val showConfirmation = remember { mutableStateOf(false) }

    // Carregar pedidos da mesa específica
    LaunchedEffect(mesaNumber) {
        viewModel.carregarPedidosMesa(mesaNumber)
    }

    // Filtrar os pedidos para a mesa específica
    val pedidosMesaFiltrados = pedidosMesa.filter { it.id_mesa == mesaNumber }

    // Calcular o total da conta
    val totalConta = pedidosMesaFiltrados.sumOf { it.soma_pedido }
    val idMesa = pedidosMesaFiltrados.firstOrNull()?.id_mesa ?: mesaNumber

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Conta da Mesa $mesaNumber",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 30.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(pedidosMesaFiltrados) { pedido ->
                Text(
                    text = "Produto: ${pedido.produto}, Quantidade: ${pedido.quant}, Notas: ${pedido.notas}, Valor Total: ${pedido.soma_pedido}€",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total da Conta: $totalConta€",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                coroutineScope.launch {
                    val dataPagamento = LocalDateTime.now()
                    viewModel.addConta(id_mesa = idMesa, conta = totalConta, data_pagamento = dataPagamento)
                    viewModel.deletePedidosMesaByIdMesa(idMesa)
                    showConfirmation.value = true
                }
            }) {
                Text(text = "Finalizar Conta")
            }

            Button(onClick = { navController.navigateUp() }) {
                Text(text = "Voltar")
            }
        }

        if (showConfirmation.value) {
            Text(
                text = "Conta finalizada com sucesso!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun TelaContaPreview() {
    val navController = rememberNavController()
    RestauranteFastio2Theme {
        TelaConta(navController, mesaNumber = 1)
    }
}
