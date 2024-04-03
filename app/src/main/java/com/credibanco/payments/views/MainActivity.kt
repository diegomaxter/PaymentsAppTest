package com.credibanco.payments.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.credibanco.payments.R
import com.credibanco.payments.views.authorization.AuthorizationActivity
import com.credibanco.payments.views.history.AuthorizationHistoryActivity
import com.credibanco.payments.views.search.SearchTransactionActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.authorization).setOnClickListener {
            startActivity(Intent(this, AuthorizationActivity::class.java))
        }
        findViewById<Button>(R.id.authorization_list).setOnClickListener {
            startActivity(Intent(this, AuthorizationHistoryActivity::class.java))
        }
        findViewById<Button>(R.id.search_transaction).setOnClickListener {
            startActivity(Intent(this, SearchTransactionActivity::class.java))
        }
    }
}