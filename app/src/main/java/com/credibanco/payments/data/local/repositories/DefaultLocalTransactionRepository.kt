package com.credibanco.payments.data.local.repositories

import com.credibanco.payments.data.local.daos.TransactionDao
import com.credibanco.payments.data.local.entities.TransactionDetails
import com.credibanco.payments.domain.Transaction
import com.credibanco.payments.domain.toTransaction

class DefaultLocalTransactionRepository(
    private val dao: TransactionDao
) : LocalTransactionRepository {
    override suspend fun getAllTransactions(): List<TransactionDetails> {
        return dao.getAllTransactions()
    }

    override suspend fun getTransactionByReceiptId(receiptId: String): Transaction? {
        return dao.getTransactionsByReceiptId(receiptId)?.toTransaction()
    }
}