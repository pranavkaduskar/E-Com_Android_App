package com.brand.store.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brand.store.R
import com.brand.store.data.Wallet
import com.brand.store.databinding.FragmentProfileBinding
import com.brand.store.fragment.DashboardFragment

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var wallet: Wallet

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("WalletPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        // Load wallet balance from SharedPreferences (default to Rs 50,000)
        val initialBalance = sharedPreferences.getFloat("wallet_balance", 5000.0f)
        wallet = Wallet(initialBalance.toDouble())

        // Bind wallet balance to UI
        binding.walletBalanceTextView.text = wallet.balance.toString()

        // Set up "Add Money" button click listener
        binding.addMoneyButton.setOnClickListener { addMoneyToWallet() }
    }

    private fun addMoneyToWallet() {
        val amountToAdd = 1000.0 // Amount to add in Rs (you can change this)
        wallet.balance += amountToAdd

        // Update the balance in SharedPreferences
        editor.putFloat("wallet_balance", wallet.balance.toFloat()).apply()

        // Update the wallet balance in the UI
        binding.walletBalanceTextView.text = wallet.balance.toString()
    }
}