package com.example.restaurantefastio2.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
@Entity(tableName = "mesas")
data class mesas(
    @PrimaryKey val id_mesa: Int = 0,
    val nome: String
)
@Entity(tableName = "produto")
data class produto(
    @PrimaryKey(autoGenerate = true) val id_produto: Int = 0,
    val produto: String,
    val preco: Double
)
@Entity(tableName = "pedidos_mesa")
data class pedidos_mesa(
    @PrimaryKey(autoGenerate = true) val id_pedido: Int = 0,
    val id_mesa: Int,
    val produto: String,
    val categoria: String,
    val quant: Int = 0,
    val notas: String,
    val soma_pedido: Double
)
@Entity(tableName = "conta")
data class conta(
    @PrimaryKey(autoGenerate = true) val id_conta: Int = 0,
    val id_mesa: Int,
    val conta: Double,
    val data_pagamento: String
)
