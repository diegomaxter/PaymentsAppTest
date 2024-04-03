package com.credibanco.payments.views.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.credibanco.payments.R
import com.credibanco.payments.databinding.FragmentAuthorizationHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthorizationHistoryFragment : Fragment() {
    private val viewModel: AuthorizationHistoryViewModel by viewModels()
    private val adapter = AuthorizationHistoryAdapter()
    private var _binding: FragmentAuthorizationHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    invalidate(state)
                }
            }
        }
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter
        viewModel.getAllTransactions()
    }

    private fun invalidate(state: AuthorizationHistoryState) {
        binding.progress.isVisible = state.isLoading
        adapter.update(state.authorizations)
        if (state.error != null) {
            Toast.makeText(
                requireContext(),
                getString(R.string.feat_main_authorization_history_no_transactions),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}