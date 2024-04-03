package com.credibanco.payments.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.credibanco.payments.R
import com.credibanco.payments.data.remote.repositories.RemoteTransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthorizationState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    @StringRes val error: Int? = null
)

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val repository: RemoteTransactionRepository
) : ViewModel() {

    private val internalState = MutableStateFlow(AuthorizationState())
    val state: StateFlow<AuthorizationState> = internalState

    fun authorization(
        id: String,
        amount: String,
        card: String,
        commerceCode: String,
        terminalCode: String
    ) {
        internalState.value = internalState.value.copy(
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val authorization = repository.authorizeTransaction(
                id = id,
                amount = amount,
                card = card,
                commerceCode = commerceCode,
                terminalCode = terminalCode
            )
            if (authorization) {
                internalState.value = internalState.value.copy(
                    isSuccess = true,
                    isLoading = false
                )

            } else {
                internalState.value = internalState.value.copy(
                    error = R.string.feat_main_authorization_error,
                    isLoading = false
                )
            }
        }
    }

    fun clearState() {
        internalState.value = AuthorizationState()
    }

}