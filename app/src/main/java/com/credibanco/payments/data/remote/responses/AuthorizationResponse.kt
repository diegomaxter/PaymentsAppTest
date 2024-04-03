package com.credibanco.payments.data.remote.responses

data class AuthorizationResponse(
    val receiptId: String,
    val rrn: String,
    val statusCode: String,
    val statusDescription: String
)