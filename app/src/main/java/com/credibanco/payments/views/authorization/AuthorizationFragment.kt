package com.credibanco.payments.views.authorization

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
import com.credibanco.payments.databinding.FragmentAuthorizationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthorizationFragment : Fragment() {

    private val viewModel: AuthorizationViewModel by viewModels()
    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
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
                requireContext(),
                getString(R.string.feat_main_authorization_success),
                Toast.LENGTH_SHORT
            ).show()
            activity?.finish()
        }
        if (state.error != null) {
            Toast.makeText(
                requireContext(),
                getString(R.string.feat_main_authorization_denied),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.clearState()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}