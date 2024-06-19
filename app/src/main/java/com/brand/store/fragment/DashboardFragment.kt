package com.brand.store.fragment

import com.brand.store.R

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.brand.store.adapter.DashboardAdapter
import com.brand.store.data.DataClass
import com.brand.store.data.Wallet
import com.brand.store.databinding.FragmentDashboardBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DashboardFragment : Fragment() {
    public var walletBalance: Double = 50000.0 // Initial wallet balance
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var binding: FragmentDashboardBinding? = null
    private lateinit var dataList: ArrayList<DataClass>
    private lateinit var adapter: DashboardAdapter
    private var databaseReference: DatabaseReference? = null
    private var eventListener: ValueEventListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding?.root
        sharedPreferences = requireContext().getSharedPreferences("WalletPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        walletBalance = sharedPreferences.getFloat("walletBalance", 50000.0F).toDouble()
        fun handleBuyAction() {
            // You can display a toast message or perform other actions here
            Toast.makeText(requireContext(), "Payment successful", Toast.LENGTH_SHORT).show()
        }
        val buyClickListener: (Double) -> Unit = { productPrice ->
            if (walletBalance >= productPrice) {
                // Deduct the product price from the wallet balance
                walletBalance -= productPrice

                // Update the wallet balance in SharedPreferences
                editor.putFloat("walletBalance", walletBalance.toFloat()).apply()

                // Call a function to handle the buy action (e.g., show a toast)
                handleBuyAction()

                // Refresh the UI or perform any other necessary actions
                adapter.notifyDataSetChanged()
            } else {
                // Display a message to inform the user that they have insufficient funds
                Toast.makeText(requireContext(), "Insufficient funds", Toast.LENGTH_SHORT).show()
            }

        }

        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        binding?.recyclerDashboard?.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        dataList = ArrayList()
        val initialBalance = 50000.00
        val wallet = Wallet(initialBalance) // Initialize the wallet
        val adapter = DashboardAdapter(requireContext(), dataList, wallet)
        binding?.recyclerDashboard?.adapter = adapter

        databaseReference =
            FirebaseDatabase.getInstance().getReference("Sunglass Hosting Application")

        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(DataClass::class.java)
                    if (dataClass != null) {
                        dataList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }

        })

        return view




    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        // Remove the ValueEventListener when the Fragment is destroyed
        eventListener?.let {
            databaseReference?.removeEventListener(it)
        }
    }
}