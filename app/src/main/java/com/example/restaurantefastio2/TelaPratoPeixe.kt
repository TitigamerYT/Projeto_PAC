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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPratoPeixe(navController: NavHostController, mesaNumber: Int, viewModel: ViewModel = viewModel()) {
    val produtos by viewModel.produto.collectAsState()

    // Filtrar apenas os pratos de peixe
    val pratosDePeixe = produtos.filter {
        it.produto in listOf(
            "Bacalhau à brás", "Bacalhau com natas", "Polvo à lagareiro",
            "Lulas grelhadas", "Robalo grelhado", "Dourada grelhada",
            "Bacalhau assado", "Arroz de tamboril", "Espeto de gambas",
            "Chocos fritos", "Medalhões de pescada", "Açorda de camarão",
            "Feijoada de camarão"
        )
    }

    // Estados para o AlertDialog de adicionar item
    val showDialog = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf("") }
    val quantity = remember { mutableStateOf(TextFieldValue("")) }
    val note = remember { mutableStateOf(TextFieldValue("")) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Adicionar item") },
            text = {
                Column {
                    Text(text = selectedItem.value)
                    OutlinedTextField(
                        value = quantity.value,
                        onValueChange = { quantity.value = it },
                        label = { Text("Quantidade") }
                    )
                    OutlinedTextField(
                        value = note.value,
                        onValueChange = { note.value = it },
                        label = { Text("Nota") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val quantidade = quantity.value.text.toIntOrNull() ?: 0
                    val nota = note.value.text
                    val preco = pratosDePeixe.find { it.produto == selectedItem.value.split(" - ")[0] }?.preco ?: 0.0
                    val soma_pedido = quantidade * preco
                    viewModel.addPedidoMesa(
                        id_mesa = mesaNumber,
                        produto = selectedItem.value.split(" - ")[0],
                        categoria = "Prato de Peixe",
                        quant = quantidade,
                        notas = nota,
                        soma_pedido = soma_pedido
                    )
                    showDialog.value = false
                }) {
                    Text("Adicionar")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) {
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
                title = { Text("Prato de Peixe") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.padding(16.dp)) {
                pratosDePeixe.forEach { produto ->
                    val itemText = "${produto.produto} - ${produto.preco}€"
                    Text(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .clickable {
                                selectedItem.value = itemText
                                showDialog.value = true
                            },
                        fontSize = 20.sp,
                        text = itemText,
                        textAlign = TextAlign.Left
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TelaPratoPeixePreview() {
    val navController = rememberNavController()
    TelaPratoPeixe(navController, mesaNumber = 1)
}

