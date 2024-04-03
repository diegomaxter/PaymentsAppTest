package com.credibanco.payments.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.credibanco.payments.data.local.entities.TransactionDetails

@Dao
interface TransactionDao {

    @Upsert
    suspend fun insertTransaction(transactionDetails: TransactionDetails)

    @Query("SELECT * FROM TransactionDetailsEntity")
    suspend fun getAllTransactions(): List<TransactionDetails>

    @Query("SELECT * FROM TransactionDetailsEntity WHERE receiptId = :receiptId")
    suspend fun getTransactionsByReceiptId(receiptId: String): TransactionDetails?

    @Query("DELETE FROM TransactionDetailsEntity WHERE receiptId = :receiptId")
    suspend fun deleteTransaction(receiptId: String?)
}
