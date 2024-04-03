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


data class TransactionDetailState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    @StringRes val error: Int? = null
)

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    private val repository: RemoteTransactionRepository
) : ViewModel() {

    private val internalState = MutableStateFlow(TransactionDetailState())
    val state: StateFlow<TransactionDetailState> = internalState

    fun annulment(
        receiptId: String?,
        rrn: String?,
        commerceCode: String?,
        terminalCode: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            internalState.value = internalState.value.copy(
                isLoading = true
            )
            delay(1000)
            val annulment = repository.annulTransaction(
                receiptId = receiptId,
                rrn = rrn,
                commerceCode = commerceCode,
                terminalCode = terminalCode
            )
            if (!annulment) {
                internalState.value = internalState.value.copy(
                    isLoading = false,
                    error = R.string.feat_main_annulment_error
                )
            } else {
                internalState.value = internalState.value.copy(
                    isLoading = false,
                    isSuccess = true
                )
            }
        }
    }

    fun clearState() {
        internalState.value = TransactionDetailState()
    }
}
