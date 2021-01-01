package com.controle.venda.database

import android.content.Context
import androidx.room.*
import com.controle.venda.model.Produto
import com.controle.venda.model.Venda
import java.util.*

@Database(entities = [Venda::class, Produto::class], version = 1, exportSchema = false)
@TypeConverters(AppDatabase.DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun vendaDao(): VendaDao

    abstract fun produtoDao(): ProdutoDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {

            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "controle_venda.db").build()
            }

            return INSTANCE as AppDatabase
        }

    }

    class DateTypeConverter {

        @TypeConverter
        fun fromTimestamp(value: Long?): Date? {
            if (value == null) {
                return null
            }

            return Date(value)
        }

        @TypeConverter
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }

    }

}