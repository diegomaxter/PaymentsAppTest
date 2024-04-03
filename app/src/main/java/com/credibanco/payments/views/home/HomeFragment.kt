package com.credibanco.payments.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.credibanco.payments.R
import com.credibanco.payments.databinding.FragmentHomeBinding
import com.credibanco.payments.views.authorization.AuthorizationFragment
import com.credibanco.payments.views.history.AuthorizationHistoryFragment
import com.credibanco.payments.views.search.SearchTransactionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.authorization.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            val fragment = AuthorizationFragment()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.authorizationList.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            val fragment = AuthorizationHistoryFragment()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.searchTransaction.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            val fragment = SearchTransactionFragment()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}