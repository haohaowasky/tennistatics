package io.tennis.statistic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.tennis.statistic.R
import io.tennis.statistic.dataStore.gameData

class CustomAdapter(val userList: ArrayList<gameData>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.txtagainst?.text = "Against: " + userList[position].against
        if (userList[position].serve == false) {
            holder?.txtServe?.text = "Catch"
        }else {
            holder?.txtServe?.text = "Serve"
        }
        holder?.txtResult?.text = "result: " + userList[position].result
        holder?.txtSpotsTotal?.text = userList[position].spotsTotal.toString()
        holder?.txtTime?.text = userList[position].timeStamp

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtagainst = itemView.findViewById<TextView>(R.id.against)
        val txtServe = itemView.findViewById<TextView>(R.id.serve)
        val txtResult = itemView.findViewById<TextView>(R.id.result)
        val txtSpotsTotal = itemView.findViewById<TextView>(R.id.spotsTotal)
        val txtTime = itemView.findViewById<TextView>(R.id.time)

    }
}