package io.tennis.statistic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import io.tennis.statistic.R
import io.tennis.statistic.Views.MainActivity.gameData.playerName
import io.tennis.statistic.dataStore.gameData

class PlayersAdapter(val userList: ArrayList<String>, userID:String): RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {
    private lateinit var database: DatabaseReference
    private var playersName = ""

    val postListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            val post = dataSnapshot.child("Users").child(userID).child("Games").child(playersName)
//            val gson = Gson()
            Logger.i("data is " + userID + " " + playersName )

            for (i in post.children){
                Logger.i("the data is " + i.toString())
            }


        }


        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Logger.i("fail get data" + databaseError.toException())
            // ...
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.txtPlayers?.text = userList[position]
        holder?.txtPlayers.setOnClickListener{
            database = FirebaseDatabase.getInstance().reference
            database.addListenerForSingleValueEvent(postListener)
            playersName = holder?.txtPlayers?.text.toString()
            Logger.i("button clicked  " + holder?.txtPlayers?.text.toString() )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.players, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtPlayers = itemView.findViewById<Button>(R.id.playerName)
    }
}