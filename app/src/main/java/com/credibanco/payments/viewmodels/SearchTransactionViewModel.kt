package com.credibanco.payments.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.credibanco.payments.R
import com.credibanco.payments.data.local.repositories.LocalTransactionRepository
import com.credibanco.payments.domain.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchTransactionState(
    val isSuccess: Boolean = false,
    val transaction: Transaction? = null,
    val isLoading: Boolean = false,
    @StringRes val error: Int? = null
)

@HiltViewModel
class SearchTransactionViewModel @Inject constructor(
    private val repository: LocalTransactionRepository
) : ViewModel() {
    private val internalState = MutableStateFlow(SearchTransactionState())
    val state: StateFlow<SearchTransactionState> = internalState

    fun getTransactionByReceiptId(receiptId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            internalState.value = internalState.value.copy(
                isLoading = true
            )
            delay(1000)
            val transaction = repository.getTransactionByReceiptId(receiptId)
            internalState.value = internalState.value.copy(
                transaction = transaction,
                isSuccess = transaction != null,
                error = R.string.feat_main_search_transaction_search_error,
                isLoading = false
            )
        }
    }

    fun clearState() {
        internalState.value = SearchTransactionState()
    }
}
