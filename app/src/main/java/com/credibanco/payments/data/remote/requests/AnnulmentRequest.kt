package com.credibanco.payments.data.remote.requests

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnnulmentRequest(
    val receiptId: String? = null,
    val rrn: String? = null
): Parcelable