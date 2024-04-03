package com.credibanco.payments.data.remote.repositories


interface RemoteTransactionRepository {

    suspend fun authorizeTransaction(
        id: String,
        commerceCode: String,
        terminalCode: String,
        amount: String,
        card: String
    ): Boolean

    suspend fun annulTransaction(
        commerceCode: String?,
        terminalCode: String?,
        receiptId: String?,
        rrn: String?
    ): Boolean
}