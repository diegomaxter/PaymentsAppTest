package com.credibanco.payments.views

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.credibanco.payments.R
import com.credibanco.payments.databinding.ActivityMainBinding
import com.credibanco.payments.views.authorization.AuthorizationFragment
import com.credibanco.payments.views.history.AuthorizationHistoryFragment
import com.credibanco.payments.views.home.HomeFragment
import com.credibanco.payments.views.search.SearchTransactionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.fragmentContainer.apply {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = HomeFragment()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack("home")
            transaction.commit()
        }
    }
}