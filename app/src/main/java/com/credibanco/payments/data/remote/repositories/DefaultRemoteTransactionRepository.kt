package com.credibanco.payments.data.remote.repositories

import android.util.Base64
import com.credibanco.payments.data.PaymentsApi
import com.credibanco.payments.data.local.daos.TransactionDao
import com.credibanco.payments.data.local.entities.TransactionDetails
import com.credibanco.payments.data.remote.requests.AnnulmentRequest
import com.credibanco.payments.data.remote.requests.AuthorizationRequest
import javax.inject.Inject

class DefaultRemoteTransactionRepository @Inject constructor(
    private val api: PaymentsApi,
    private val dao: TransactionDao
) : RemoteTransactionRepository {

    override suspend fun authorizeTransaction(
        id: String,
        commerceCode: String,
        terminalCode: String,
        amount: String,
        card: String
    ): Boolean {
        try {
            val credentials = "$commerceCode$terminalCode"
            val base64Credentials = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
            val authorizationHeader = "Basic $base64Credentials"
            val response = api.authorizeTransaction(
                authorizationHeader,
                AuthorizationRequest(
                    id = id,
                    commerceCode = commerceCode,
                    terminalCode = terminalCode,
                    amount = amount,
                    card = card
                ),
            )
            if (response.statusCode == "00") {
                dao.insertTransaction(
                    TransactionDetails(
                        commerceCode = commerceCode,
                        terminalCode = terminalCode,
                        amount = amount,
                        card = card,
                        receiptId = response.receiptId,
                        rrn = response.rrn,
                        statusCode = response.statusCode,
                        statusDescription = response.statusDescription
                    )
                )
                return true
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return false
    }

    override suspend fun annulTransaction(
        commerceCode: String?,
        terminalCode: String?,
        receiptId: String?,
        rrn: String?
    ): Boolean {
        try {
            val credentials = "$commerceCode$terminalCode"
            val base64Credentials = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
            val annulmentHeader = "Basic $base64Credentials"
            val response = api.annulTransaction(
                annulmentHeader,
                AnnulmentRequest(
                    receiptId = receiptId,
                    rrn = rrn
                )
            )
            if (response.statusCode == "00") {
                dao.deleteTransaction(receiptId)
                return true
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return false
    }
}