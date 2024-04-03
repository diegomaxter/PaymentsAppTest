package com.credibanco.payments.views.annulment

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
import com.credibanco.payments.databinding.FragmentTransactionDetailBinding
import com.credibanco.payments.domain.Transaction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@AndroidEntryPoint
class TransactionDetailFragment : Fragment() {
    private val viewModel: TransactionDetailViewModel by viewModels()
    private var _binding: FragmentTransactionDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.annulmentButton.isEnabled = true

        val transaction = arguments?.getParcelable<Transaction>("transaction")

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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
                requireContext(),
                getString(R.string.feat_main_transaction_detail_success),
                Toast.LENGTH_SHORT
            ).show()
            activity?.finish()
        }
        if (state.error != null && !state.isSuccess) {
            Toast.makeText(
                requireContext(),
                getString(R.string.feat_main_transaction_detail_error),
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