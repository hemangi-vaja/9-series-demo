package com.example.practicaldemo

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaldemo.databinding.ItemBtBinding
import com.example.practicaldemo.helper.Const
import com.example.practicaldemo.helper.PreferenceHelper


class BTAdapter(
    private val context: Context,
    private var btList: List<BTItems>, private val listener: (BTItems) -> Unit
) :
    RecyclerView.Adapter<BTAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemBtBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = ItemBtBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = btList[position]
        holder.binding.tvName.text = item.name
        Log.d("<>>>>>", "onBindViewHolder: " + item.name)
        holder.binding.tvMac.text = item.mac
        holder.itemView.setOnClickListener {
            listener.invoke(item)
        }
        val bluetooth = item.mac
        val mac: String =
            context?.let { PreferenceHelper.defaultPrefs(it).getString(Const.printer, "") }
                .toString()
        if (mac == bluetooth) {
            holder.binding.tvConnected.visibility = View.VISIBLE
        } else {
            holder.binding.tvConnected.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        Log.d("<>", "getItemCount: " + btList.size)
        return btList.size
    }

}