package com.credibanco.payments.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "TransactionDetailsEntity")
data class TransactionDetails(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val commerceCode: String? = null,
    val terminalCode: String? = null,
    val amount: String? = null,
    val card: String? = null,
    val receiptId: String? = null,
    val rrn: String? = null,
    val statusCode: String? = null,
    val statusDescription: String? = null,
)