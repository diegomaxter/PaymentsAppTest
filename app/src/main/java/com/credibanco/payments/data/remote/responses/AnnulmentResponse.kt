package com.credibanco.payments.data.remote.responses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnnulmentResponse(
    val statusCode: String,
    val statusDescription: String
) : Parcelable