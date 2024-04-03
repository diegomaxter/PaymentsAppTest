package com.credibanco.payments.di

import com.credibanco.payments.data.PaymentsApi
import com.credibanco.payments.data.local.daos.TransactionDao
import com.credibanco.payments.data.local.repositories.DefaultLocalTransactionRepository
import com.credibanco.payments.data.local.repositories.LocalTransactionRepository
import com.credibanco.payments.data.remote.repositories.DefaultRemoteTransactionRepository
import com.credibanco.payments.data.remote.repositories.RemoteTransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PaymentsModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.7:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): PaymentsApi {
        return retrofit.create(PaymentsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteTransactionRepository(
        api: PaymentsApi,
        dao: TransactionDao
    ): RemoteTransactionRepository {
        return DefaultRemoteTransactionRepository(api, dao)
    }

    @Provides
    @Singleton
    fun provideLocalTransactionRepository(
        dao: TransactionDao
    ): LocalTransactionRepository {
        return DefaultLocalTransactionRepository(dao)
    }
}