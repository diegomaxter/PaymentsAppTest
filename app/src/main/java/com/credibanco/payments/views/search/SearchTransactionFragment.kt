package com.credibanco.payments.views.search

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
import com.credibanco.payments.R
import com.credibanco.payments.databinding.FragmentSearchTransactionBinding
import com.credibanco.payments.views.annulment.TransactionDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchTransactionFragment : Fragment() {
    private val viewModel: SearchTransactionViewModel by viewModels()
    private var _binding: FragmentSearchTransactionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.transactionButton.isEnabled = true
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
            val transactionDetailFragment = TransactionDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("transaction", state.transaction)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, transactionDetailFragment)
                .addToBackStack(null)
                .commit()
        }
        if (state.error != null && !state.isSuccess) {
            Toast.makeText(
                requireContext(),
                getString(R.string.feat_main_search_transaction_search_error),
                Toast.LENGTH_SHORT
            ).show()
        }
        viewModel.clearState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}