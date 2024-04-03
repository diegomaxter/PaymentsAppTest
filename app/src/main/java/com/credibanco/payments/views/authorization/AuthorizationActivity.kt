package com.credibanco.payments.views.authorization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.credibanco.payments.R
import com.credibanco.payments.databinding.ActivityAuthorizationBinding
import com.credibanco.payments.viewmodels.AuthorizationState
import com.credibanco.payments.viewmodels.AuthorizationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthorizationActivity : AppCompatActivity() {

    private val viewModel: AuthorizationViewModel by viewModels()
    private lateinit var binding: ActivityAuthorizationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
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
            handleCreateTransaction()
        }
    }

    private fun handleCreateTransaction() {
        viewModel.authorization(
            amount = binding.amount.text.toString(),
            card = binding.card.text.toString(),
            commerceCode = binding.commerceCode.text.toString(),
            id = binding.id.text.toString(),
            terminalCode = binding.terminalCode.text.toString()
        )
    }

    private fun invalidate(state: AuthorizationState) {
        binding.progress.isVisible = state.isLoading
        if (state.isSuccess) {
            Toast.makeText(
                this, getString(R.string.feat_main_authorization_success), Toast.LENGTH_SHORT
            ).show()
            finish()
        }
        if (state.error != null) {
            Toast.makeText(
                this, getString(R.string.feat_main_authorization_denied), Toast.LENGTH_SHORT
            ).show()
            viewModel.clearState()
        }
    }
}