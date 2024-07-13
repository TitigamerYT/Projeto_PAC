package com.example.restaurantefastio2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [mesas::class, produto::class, pedidos_mesa::class, conta::class],
    version = 5
)
abstract class RestauranteFastioBase : RoomDatabase() {

    abstract fun mesasDao(): MesasDao
    abstract fun produtosDao(): ProdutosDao
    abstract fun pedidosmesasDao(): PedidosmesasDao
    abstract fun contaDao(): ContaDao

    companion object {
        private const val DATABASE_NAME = "restaurant_database"

        // Migration from version 2 to 3, 3 to 4, and 4 to 5
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Migration logic from version 2 to 3
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Migration logic from version 3 to 4
            }
        }

        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Check if the column "notas" already exists before trying to add it
                val cursor = database.query("PRAGMA table_info(pedidos_mesa)")
                var columnNotasExists = false
                while (cursor.moveToNext()) {
                    val columnName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                    if (columnName == "notas") {
                        columnNotasExists = true
                        break
                    }
                }
                cursor.close()
                if (!columnNotasExists) {
                    database.execSQL("ALTER TABLE pedidos_mesa ADD COLUMN notas TEXT DEFAULT '' NOT NULL")
                }

                // Check if the column "nome" already exists before trying to add it
                val cursorMesas = database.query("PRAGMA table_info(mesas)")
                var columnNomeExists = false
                while (cursorMesas.moveToNext()) {
                    val columnName = cursorMesas.getString(cursorMesas.getColumnIndexOrThrow("name"))
                    if (columnName == "nome") {
                        columnNomeExists = true
                        break
                    }
                }
                cursorMesas.close()
                if (!columnNomeExists) {
                    database.execSQL("ALTER TABLE mesas ADD COLUMN nome TEXT NOT NULL DEFAULT ''")
                }
            }
        }

        @Volatile
        private var INSTANCE: RestauranteFastioBase? = null

        fun getDatabase(context: Context): RestauranteFastioBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestauranteFastioBase::class.java,
                    DATABASE_NAME
                )
                    .addMigrations(MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
