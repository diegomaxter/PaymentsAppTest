package com.credibanco.payments.views.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.credibanco.payments.data.local.entities.TransactionDetails
import com.credibanco.payments.databinding.ItemAuthorizationBinding

class AuthorizationHistoryAdapter(
    private var transactionsAuthorized : List<TransactionDetails> = emptyList()
) :
    RecyclerView.Adapter<AuthorizationHistoryAdapter.AuthorizationHistoryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AuthorizationHistoryViewHolder {
        val binding =
            ItemAuthorizationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AuthorizationHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AuthorizationHistoryViewHolder,
        position: Int
    ) {
        val transaction = transactionsAuthorized[position]
        holder.bind(transaction)
    }

    override fun getItemCount(): Int = transactionsAuthorized.size

    @SuppressLint("NotifyDataSetChanged")
    fun update(transactions: List<TransactionDetails>) {
        this.transactionsAuthorized = transactions
        notifyDataSetChanged()
    }

    class AuthorizationHistoryViewHolder(
        private val binding: ItemAuthorizationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: TransactionDetails) {
            binding.transactionId.text = transaction.id
            binding.transactionAmount.text = transaction.amount
            binding.transactionCard.text = transaction.card
            binding.transactionTerminalCode.text = transaction.terminalCode
            binding.transactionCommerceCode.text = transaction.commerceCode
            binding.transactionReceiptId.text = transaction.receiptId
            binding.transactionRrn.text = transaction.rrn
        }
    }

}