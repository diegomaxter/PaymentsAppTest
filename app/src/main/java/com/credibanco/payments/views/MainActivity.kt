package com.credibanco.payments.views

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.credibanco.payments.R
import com.credibanco.payments.databinding.ActivityMainBinding
import com.credibanco.payments.views.authorization.AuthorizationFragment
import com.credibanco.payments.views.history.AuthorizationHistoryFragment
import com.credibanco.payments.views.search.SearchTransactionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.authorization.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = AuthorizationFragment()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            binding.fragmentContainer.visibility = View.VISIBLE
            backPress()
        }
        binding.authorizationList.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = AuthorizationHistoryFragment()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            binding.fragmentContainer.visibility = View.VISIBLE
            backPress()
        }
        binding.searchTransaction.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = SearchTransactionFragment()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            binding.fragmentContainer.visibility = View.VISIBLE
            backPress()
        }
    }

    private fun backPress() {
        onBackPressedDispatcher.addCallback {
            if (binding.fragmentContainer.visibility == View.GONE) {
                finish()
            }
            binding.fragmentContainer.visibility = View.GONE
            supportFragmentManager.popBackStack()
        }
    }
}