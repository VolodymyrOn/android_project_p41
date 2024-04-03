package com.example.seedsmanager

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ManagerAdapter(private val dataSet: ArrayList<ManagerModel>, val callback: (Int)->Unit): RecyclerView.Adapter<ManagerAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.seedNameTextView)
        val whenTextView: TextView = view.findViewById(R.id.plantingTimeTextView)
        val sizeTextView: TextView = view.findViewById(R.id.plotSizeTextView)
        val deleteImageView: ImageView = view.findViewById(R.id.deleteImageView)
        val updateImageView: ImageView = view.findViewById(R.id.updateImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.manager_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.whenTextView.text = item.whenMonth
        holder.nameTextView.text = item.name
        holder.sizeTextView.text = item.howMany.toString()+" m²"


        holder.deleteImageView.setOnClickListener{
            dataSet.remove(item)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, dataSet.size)
        }

        holder.updateImageView.setOnClickListener{
            callback(position)
        }



    }

    override fun getItemCount() = dataSet.size
}
