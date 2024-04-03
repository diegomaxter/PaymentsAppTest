package com.credibanco.payments.di

import android.content.Context
import com.credibanco.payments.data.local.database.AppDataBase
import com.credibanco.payments.data.local.daos.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase {
        return AppDataBase.buildDataBase(context)
    }

    @Provides
    @Singleton
    fun provideTransactionDao(dataBase: AppDataBase): TransactionDao {
        return dataBase.transactionDao()
    }
}