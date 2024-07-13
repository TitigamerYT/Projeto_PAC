package com.example.restaurantefastio2.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidosmesasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pedido: pedidos_mesa)

    @Query("SELECT * FROM pedidos_mesa WHERE id_mesa = :idMesa")
    suspend fun getPedidosByMesa(idMesa: Int): List<pedidos_mesa>

    @Query("DELETE FROM pedidos_mesa WHERE id_mesa = :idMesa")
    suspend fun deletePedidosByMesa(idMesa: Int)

    @Query("SELECT * FROM pedidos_mesa")
    suspend fun getAllPedidos(): List<pedidos_mesa>
}

