package com.example.restaurantefastio2.db

import androidx.room.*
@Dao
interface ProdutosDao {
    @Insert
    suspend fun insert(produto: produto)
    @Query("SELECT * FROM produto")
    suspend fun getAllProdutos(): List<produto>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(produtos: List<produto>)
    @Query("DELETE FROM produto")
    suspend fun deleteAll()
}
