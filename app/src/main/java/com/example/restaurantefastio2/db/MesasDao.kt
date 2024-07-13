package com.example.restaurantefastio2.db

import androidx.room.*
@Dao
interface MesasDao {
    @Query("SELECT * FROM mesas")
    suspend fun getAllMesas(): List<mesas>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mesas: List<mesas>)

    @Query("DELETE FROM mesas")
    suspend fun deleteAll()
}
