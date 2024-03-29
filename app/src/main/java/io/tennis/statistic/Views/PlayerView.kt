package io.tennis.statistic.Views

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import io.tennis.statistic.R
import io.tennis.statistic.adapter.CustomAdapter
import io.tennis.statistic.adapter.PlayersAdapter
import io.tennis.statistic.dataStore.StatsDataDisplay
import io.tennis.statistic.dataStore.gameData

import kotlinx.android.synthetic.main.activity_data_view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val EXTRA_PARAM_TOKEN = "io.tennis.statistic.extra.TOKEN"
private const val EXTRA_PLAYER = "io.tennis.statistic.extra.PLAYER"

class PlayerView : AppCompatActivity() {
    private var Userdata = gameData()
    private lateinit var database: DatabaseReference
    private var userToken: String = ""
    private var playerName: String = ""
    private var displayData = ArrayList<gameData>()
    private var playerNames = ArrayList<String>()


    val postListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            val post = dataSnapshot.child("Users").child(userToken).child("Games")
            val gson = Gson()
//            Logger.i("data is " + gson.toJson(post))

            for (i in post.children){
                playerNames.add(i.key.toString())
            }
//

            onEventBoardUpdated(playerNames)
        }


        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Logger.i("fail get data" + databaseError.toException())
            // ...
        }

    }


    fun onEventBoardUpdated(board: ArrayList<String>) {
        var listView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.playerListRecyclerView)
        listView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        var adapter = PlayersAdapter(board, userToken)
        listView.adapter = adapter
//        for (n in board){
////            listView.addItemDecoration()
//            printList.add(n.stringify()+"\n")
//        }
//        Logger.i("the board is " + printList.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_view)
        userToken = intent.getStringExtra(EXTRA_PARAM_TOKEN)
        database = FirebaseDatabase.getInstance().reference
        database.addListenerForSingleValueEvent(postListener)
        EventBus.getDefault().register(this)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDisplayData(displayData: StatsDataDisplay){
        Logger.i("got event" +displayData.totalWinPercent + "%")
        val builder = AlertDialog.Builder(this)
        // TODO add reason in Beta version
        builder.setTitle("Stats Data")
        builder.setMessage(
                            "total rounds is " + displayData.total_round + "\n" +
                            "total win is " + displayData.total_win  + "\n" +
                            "total win when serve is " + displayData.serve_win  + "\n" +
                            "total win when return is " + displayData.return_win  + "\n" +

                            "total win percentage is " + displayData.totalWinPercent + " %" + "\n" +
                            "serve win percentage is " + displayData.serveWinPercent + " %" + "\n" +
                            "return win percentage is " + displayData.returnWinPercent + " %" + "\n" +
                            "1-4 Shot win percentage is  " + displayData.shortWin + " %" + "\n" +
                            "5 + Shot win percentage is " + displayData.longwin + " %" + "\n"
        )

        builder.setPositiveButton( "OK",
            DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
            })

        val alert = builder.create()
        alert.show()
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN) {
            if (event.keyCode == KeyEvent.KEYCODE_BACK) {
                this.finish()
            }
        }
        return super.dispatchKeyEvent(event)
    }

}
