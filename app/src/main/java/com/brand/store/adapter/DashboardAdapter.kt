package com.brand.store.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.brand.store.R
import com.brand.store.data.DataClass
import com.squareup.picasso.Picasso
import com.brand.store.data.Wallet

class DashboardAdapter(private val context: Context, private val dataList: List<DataClass>,wallet: Wallet) :
    RecyclerView.Adapter<DashboardAdapter.MyViewHolder>() {
    val walletBalance = Wallet(5000.00) // Initialize the wallet


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataItem = dataList[position]

        // Bind data to views
        holder.recTitle.text = dataItem.dataTitle
        holder.recPrice.text = dataItem.dataPrice

        // Load image using Picasso
        Picasso.get()
            .load(dataItem.dataImage)
            .into(holder.recImage)
        holder.Buybutton.setOnClickListener {
            val price = dataItem.dataPrice?.toDoubleOrNull() ?: 1999.0
            buyClickListener(price)
        }
        holder.Add_To_Cart.setOnClickListener {
            Toast.makeText(context, "Product is Added to Cart ", Toast.LENGTH_SHORT).show()
        }
        holder.More_Info.setOnClickListener {
            Toast.makeText(context, "Not Available More Information about this product", Toast.LENGTH_SHORT).show()
        }

        holder.Add_TO_favorite.setOnClickListener {
            Toast.makeText(context,"Product is add to ${holder.Add_TO_favorite.text}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buyClickListener(price: Double) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Confirm Purchase")
        alertDialogBuilder.setMessage("Do you want to buy this product for $price?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            if (walletBalance.balance >= price) {
                walletBalance.deductBalance(price)
                notifyDataSetChanged() // Update the adapter to reflect the new data
                Toast.makeText(context, "Purchase successful!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Insufficient balance!", Toast.LENGTH_SHORT).show()
            }
        }
        alertDialogBuilder.setNegativeButton("No") { _, _ ->
            // Do nothing if the user cancels the purchase
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recImage: ImageView
        var recTitle: TextView
        var recPrice: TextView
        var Buybutton: Button
        var Add_To_Cart : Button
        var More_Info : Button
        var Add_TO_favorite : Button

        init {
            recImage = itemView.findViewById(R.id.image)
            recTitle = itemView.findViewById(R.id.Style)
            recPrice = itemView.findViewById(R.id.price)
            Buybutton = itemView.findViewById(R.id.Buybutton)
            Add_To_Cart = itemView.findViewById(R.id.Add_To_Cart)
            More_Info = itemView.findViewById(R.id.More_Info)
            Add_TO_favorite = itemView.findViewById(R.id.Add_TO_favorite)
        }
    }
}
