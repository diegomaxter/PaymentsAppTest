package com.credibanco.payments.data

import com.credibanco.payments.data.remote.requests.AnnulmentRequest
import com.credibanco.payments.data.remote.responses.AuthorizationResponse
import com.credibanco.payments.data.remote.requests.AuthorizationRequest
import com.credibanco.payments.data.remote.responses.AnnulmentResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface PaymentsApi {
    @Headers("Content-Type: application/json")
    @POST("/api/payments/authorization")
    suspend fun authorizeTransaction(
        @Header("Authorization") authorizationHeader: String,
        @Body authorizationRequest: AuthorizationRequest
    ): AuthorizationResponse

    @Headers("Content-Type: application/json")
    @POST("/api/payments/annulment")
    suspend fun annulTransaction(
        @Header("Authorization") annulmentHeader: String,
        @Body annulmentRequest: AnnulmentRequest
    ): AnnulmentResponse
}