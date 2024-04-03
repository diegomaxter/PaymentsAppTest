package com.credibanco.payments.views.search

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.credibanco.payments.R
import com.credibanco.payments.databinding.ActivitySearchTransactionBinding
import com.credibanco.payments.viewmodels.SearchTransactionState
import com.credibanco.payments.viewmodels.SearchTransactionViewModel
import com.credibanco.payments.views.annulment.TransactionDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchTransactionActivity : AppCompatActivity() {
    private val viewModel: SearchTransactionViewModel by viewModels()
    private lateinit var binding: ActivitySearchTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.transactionButton.isEnabled = true
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    invalidate(state)
                }
            }
        }
        binding.transactionButton.setOnClickListener {
            handleSearchTransaction()
        }
    }

    private fun handleSearchTransaction() {
        viewModel.getTransactionByReceiptId(receiptId = binding.receiptId.text.toString())
    }

    private fun invalidate(state: SearchTransactionState) {
        binding.progress.isVisible = state.isLoading
        if (state.isSuccess) {
            val intent = Intent(this, TransactionDetailActivity::class.java).apply {
                putExtra("transaction", state.transaction)
            }
            startActivity(intent)
            finish()
        }
        if (state.error != null && !state.isSuccess) {
            Toast.makeText(
                this,
                getString(R.string.feat_main_search_transaction_search_error),
                Toast.LENGTH_SHORT
            ).show()
        }
        viewModel.clearState()
    }
}
