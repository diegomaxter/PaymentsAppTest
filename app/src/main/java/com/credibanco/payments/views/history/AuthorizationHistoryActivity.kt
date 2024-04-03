package com.credibanco.payments.views.history

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.credibanco.payments.R
import com.credibanco.payments.databinding.ActivityAuthorizationHistoryBinding
import com.credibanco.payments.viewmodels.AuthorizationHistoryState
import com.credibanco.payments.viewmodels.AuthorizationHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthorizationHistoryActivity : AppCompatActivity() {
    private val viewModel: AuthorizationHistoryViewModel by viewModels()
    private val adapter = AuthorizationHistoryAdapter()
    private lateinit var binding: ActivityAuthorizationHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect { state ->
                    invalidate(state)
                }
            }
        }
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter
        viewModel.getAllTransactions()
    }

    private fun invalidate(state:AuthorizationHistoryState){
        binding.progress.isVisible = state.isLoading
        adapter.update(state.authorizations)
        if (state.error != null) {
            Toast.makeText(
                this, getString(R.string.feat_main_authorization_history_no_transactions), Toast.LENGTH_SHORT
            ).show()
        }
    }
}