package io.tennis.statistic.adapter

import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import io.tennis.statistic.R
import io.tennis.statistic.Views.MainActivity
import io.tennis.statistic.Views.PlayerView
import io.tennis.statistic.dataStore.StatsDataDisplay
import io.tennis.statistic.dataStore.gameData
import org.greenrobot.eventbus.EventBus

class PlayersAdapter(val userList: ArrayList<String>, userID:String): RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {
    private lateinit var database: DatabaseReference
    private var playersName = ""
    private var total_round = 0
    private var total_win = 0
    private var serve_win = 0
    private var return_win = 0
    private var short_win = 0
    private var long_win = 0
    private var displayData = StatsDataDisplay()


    val postListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            val post = dataSnapshot.child("Users").child(userID).child("Games").child(playersName)
            val gson = Gson()
            Logger.i("data is " + userID + " " + playersName)

            for (i in post.children) {
                for (j in i.children) {
                    Logger.i("the data is " + j.getValue(gameData::class.java)!!.result)
                    total_round += 1
                    if (j.getValue(gameData::class.java)!!.result == "win") {
                        total_win += 1
                        when (j.getValue(gameData::class.java)!!.serve) {
                            true -> serve_win += 1
                            false -> return_win += 1

                        }

                        if (j.getValue(gameData::class.java)!!.spots.size < 3) {
                            short_win += 1
                        } else {
                            long_win += 1
                        }
                    }
//                Logger.i("the data is " + i.getValue(gameData::class.java)!!.result)
//                Logger.i("the data is " + i.toString())

                }
            }
            Logger.i("total round: " + total_round + " total win + " +  total_win + " total serve win  " + serve_win + " return win  " + return_win + " short win : " + short_win + " long win "+ long_win )
            displayData.totalWinPercent = (total_win.toDouble()/total_round.toDouble()).times(100).toInt().toString()
            displayData.serveWinPercent = (serve_win.toDouble()/total_round.toDouble()).times(100).toInt().toString()
            displayData.returnWinPercent = (return_win.toDouble()/total_round.toDouble()).times(100).toInt().toString()
            displayData.shortWin = (short_win.toDouble()/total_win.toDouble()).times(100).toInt().toString()
            displayData.longwin = (long_win.toDouble()/total_win.toDouble()).times(100).toInt().toString()
            EventBus.getDefault().post(displayData)
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
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtPlayers = itemView.findViewById<Button>(R.id.playerName)
    }
}