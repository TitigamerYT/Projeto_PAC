package com.example.restaurantefastio2.db

import androidx.room.*
@Dao
interface ContaDao {
    @Insert
    suspend fun insert(conta: conta)

    @Query("SELECT * FROM conta")
    suspend fun getAllConta(): List<conta>

    @Query("SELECT * FROM conta WHERE DATE(data_pagamento) = DATE(:dataAtual)")
    suspend fun getContasByData(dataAtual: String): List<conta>
}

