package com.credibanco.payments.data.local.repositories

import com.credibanco.payments.data.local.entities.TransactionDetails
import com.credibanco.payments.domain.Transaction

interface LocalTransactionRepository {

    suspend fun getAllTransactions(): List<TransactionDetails>

    suspend fun getTransactionByReceiptId(receiptId: String) : Transaction?

}