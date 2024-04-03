package com.credibanco.payments.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.credibanco.payments.R
import com.credibanco.payments.data.local.entities.TransactionDetails
import com.credibanco.payments.data.local.repositories.LocalTransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class AuthorizationHistoryState(
    val isLoading: Boolean = false,
    val authorizations: List<TransactionDetails> = emptyList(),
    @StringRes val error: Int? = null
)

@HiltViewModel
class AuthorizationHistoryViewModel @Inject constructor(
    private val repository: LocalTransactionRepository
) : ViewModel() {
    private val internalState = MutableStateFlow(AuthorizationHistoryState())
    val state: StateFlow<AuthorizationHistoryState> = internalState

    fun getAllTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            internalState.value = internalState.value.copy(
                isLoading = true
            )
            delay(1000)
            val authorizations = repository.getAllTransactions()

            if (authorizations.isNotEmpty()) {
                internalState.value = internalState.value.copy(
                    authorizations = authorizations,
                    isLoading = false
                )
            }else{
                internalState.value = internalState.value.copy(
                    isLoading = false,
                    error = R.string.feat_main_authorization_history_error
                )
            }
        }
    }
}