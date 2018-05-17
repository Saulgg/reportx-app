package com.dev.sandia.reportx

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class RecyclerAdapter (val ticketsList : MutableList<Tickets>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.txtName?.text = ticketsList[position].name
        holder?.txtMessage?.text = ticketsList[position].message
        holder?.txtBuilding?.text = ticketsList[position].building
        holder?.txtRoom?.text = ticketsList[position].room
        holder?.txtPriority?.text = ticketsList[position].priority
        holder?.txtWorking?.text = ticketsList[position].working
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return ticketsList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtName = itemView.findViewById<TextView>(R.id.textView3)
        val txtMessage = itemView.findViewById<TextView>(R.id.textView2)
        val txtBuilding = itemView.findViewById<TextView>(R.id.textView17)
        val txtRoom = itemView.findViewById<TextView>(R.id.textView9)
        val txtPriority = itemView.findViewById<TextView>(R.id.textView15)
        val txtWorking = itemView.findViewById<TextView>(R.id.textView13)
    }
}