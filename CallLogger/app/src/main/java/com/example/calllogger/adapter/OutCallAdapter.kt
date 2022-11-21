package com.example.calllogger.adapter

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calllogger.MainActivity
import com.example.calllogger.data.AppDatabase
import com.example.calllogger.data.OutCallEntity
import com.example.calllogger.databinding.RowCallBinding

class OutCallAdapter(var context: Context) : ListAdapter<OutCallEntity, OutCallAdapter.ViewHolder>(CallDiffCallback()) {

    // Ebbe a listába gyűjtjük ki az elemeket
    private val items = mutableListOf<OutCallEntity>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowCallBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val call = getItem(position)

        // Feltöltöm a listát.
        items.add(call)

        holder.tvDate.text = call.callDate
        holder.tvPhone.text = call.phonenumber

        holder.btnCall.setOnClickListener {
            // tel:3033131
            // Implicit intent..
            val number = items[position].phonenumber
            val intentDial = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                context.startActivity(intentDial)
            }
        }

        holder.btnDial.setOnClickListener {
            // tel:3033131
            // Implicit intent..
            val number = items[position].phonenumber
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
            context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener {
            deleteItem(holder.adapterPosition)
        }
    }

    inner class ViewHolder(val binding: RowCallBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvDate = binding.tvDate
        val tvPhone = binding.tvPhone
        val btnCall = binding.btnCall
        val btnDial = binding.btnDial
        val btnDelete = binding.btnDelete
    }

    fun deleteItem(position: Int) {
        try {
            var itemToDelete = items.get(position)
            Thread {
                AppDatabase.getInstance(context).outCallDAO().
                deleteCall(itemToDelete)

                (context as MainActivity).runOnUiThread {
                    items.removeAt(position)
                    notifyItemRemoved(position)
                }
            }.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}


class CallDiffCallback : DiffUtil.ItemCallback<OutCallEntity>() {
    override fun areItemsTheSame(oldItem: OutCallEntity, newItem: OutCallEntity): Boolean {
        return oldItem.callId == newItem.callId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: OutCallEntity, newItem: OutCallEntity): Boolean {
        return oldItem == newItem
    }
}