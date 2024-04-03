package com.credibanco.payments.views.annulment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.credibanco.payments.R
import com.credibanco.payments.databinding.ActivityTransactionDetailBinding
import com.credibanco.payments.domain.Transaction
import com.credibanco.payments.viewmodels.TransactionDetailState
import com.credibanco.payments.viewmodels.TransactionDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@AndroidEntryPoint
class TransactionDetailActivity : AppCompatActivity() {
    private val viewModel: TransactionDetailViewModel by viewModels()
    private lateinit var binding: ActivityTransactionDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.annulmentButton.isEnabled = true

        val transaction = intent.getParcelableExtra<Transaction>("transaction")

        with(binding.transaction) {
            transactionCard.text = transaction?.card
            transactionId.text = transaction?.id
            transactionAmount.text = transaction?.amount
            transactionTerminalCode.text = transaction?.terminalCode
            transactionCommerceCode.text = transaction?.commerceCode
            transactionReceiptId.text = transaction?.receiptId
            transactionRrn.text = transaction?.rrn
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    invalidate(state)
                }
            }
        }
        binding.annulmentButton.setOnClickListener {
            viewModel.annulment(
                receiptId = transaction?.receiptId,
                rrn = transaction?.rrn,
                commerceCode = transaction?.commerceCode,
                terminalCode = transaction?.terminalCode
            )
        }
    }

    private fun invalidate(state: TransactionDetailState) {
        binding.progress.isVisible = state.isLoading
        if (state.isSuccess) {
            Toast.makeText(
                this,
                getString(R.string.feat_main_transaction_detail_success),
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
        if (state.error != null && !state.isSuccess) {
            Toast.makeText(
                this,
                getString(R.string.feat_main_transaction_detail_error),
                Toast.LENGTH_SHORT
            ).show()
        }
        viewModel.clearState()
    }
}