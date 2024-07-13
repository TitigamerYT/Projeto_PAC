package com.example.restaurantefastio2

import ViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaEntradas(navController: NavController, mesaNumber: Int) {
    val viewModel: ViewModel = viewModel()
    val produtos by viewModel.produto.collectAsState()

    // Estado para o AlertDialog
    var showDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf(TextFieldValue("")) }
    var note by remember { mutableStateOf(TextFieldValue("")) }

    val entradas = produtos.filter {
        it.produto in listOf(
            "Pão", "Azeitonas", "Manteigas e Patês", "Tábua de Enchidos", "Tábua de Queijos",
            "Saladas Frias", "Camarão"
        )
    }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Adicionar item") },
            text = {
                Column {
                    Text(text = selectedItem)
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { quantity = it },
                        label = { Text("Quantidade") }
                    )
                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it },
                        label = { Text("Nota") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val quantidade = quantity.text.toIntOrNull() ?: 0
                    val nota = note.text
                    val preco = entradas.find { it.produto == selectedItem }?.preco ?: 0.0
                    val soma_pedido = quantidade * preco
                    viewModel.addPedidoMesa(
                        id_mesa = mesaNumber,
                        produto = selectedItem,
                        categoria = "Entrada",
                        quant = quantidade,
                        notas = nota,
                        soma_pedido = soma_pedido
                    )
                    showDialog = false
                }) {
                    Text("Adicionar")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = { Text("Entradas") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.padding(16.dp)) {
                entradas.forEach { produto ->
                    val itemText = "${produto.produto} - ${produto.preco}€"
                    Text(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .clickable {
                                selectedItem = produto.produto
                                showDialog = true
                            },
                        fontSize = 20.sp,
                        text = itemText,
                        textAlign = TextAlign.Left,
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TelaEntradasPreview() {
    val navController = rememberNavController()
    TelaEntradas(navController, mesaNumber = 1)
}

