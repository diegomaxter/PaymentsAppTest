package com.credibanco.payments.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.credibanco.payments.data.local.daos.TransactionDao
import com.credibanco.payments.data.local.entities.TransactionDetails


@Database(entities = [TransactionDetails::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    companion object {
        private lateinit var dataBase: AppDataBase

        fun buildDataBase(context: Context): AppDataBase {
            if (!this::dataBase.isInitialized) {
                synchronized(this) {
                    dataBase = Room.databaseBuilder(
                        context,
                        AppDataBase::class.java, context.packageName.toString()
                    ).build()
                }
            }
            return dataBase
        }
    }
}