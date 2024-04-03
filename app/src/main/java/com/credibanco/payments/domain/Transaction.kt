package com.credibanco.payments.domain

import android.os.Parcelable
import com.credibanco.payments.data.local.entities.TransactionDetails
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val id: String? = null,
    val amount: String? = null,
    val commerceCode: String? = null,
    val terminalCode: String? = null,
    val card: String? = null,
    val rrn: String? = null,
    val receiptId: String? = null
) : Parcelable

fun TransactionDetails.toTransaction() = Transaction(
    id = this.id,
    amount = this.amount,
    commerceCode = this.commerceCode,
    terminalCode = this.terminalCode,
    card = this.card,
    rrn = this.rrn,
    receiptId = this.receiptId
)