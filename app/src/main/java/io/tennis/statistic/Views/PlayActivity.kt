package io.tennis.statistic.Views

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import com.orhanobut.logger.Logger
import io.tennis.statistic.R
import io.tennis.statistic.dataStore.gameData
import butterknife.ButterKnife
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.tennis.statistic.dataStore.AbstractMessage
import java.text.SimpleDateFormat
import java.util.*


private const val EXTRA_PARAM1 = "io.tennis.statistic.extra.PARAM1"
private const val EXTRA_PARAM2 = "io.tennis.statistic.extra.PARAM2"
class PlayActivity : AppCompatActivity() {


    private var dataOne= gameData()
    private var dataTwo= gameData()
    private var serveMap = mutableMapOf<String, gameData>()
    private lateinit var database: DatabaseReference


    private lateinit var textView_playerOne: Button
    private lateinit var textView_playerTwo: Button

    data class Serve (var serve: String , var respond: String) :  AbstractMessage()

    private var serveRole = Serve("", "")

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
//        mPlayerText1 = findViewById<TextView>(R.id.playerOne)
        database = FirebaseDatabase.getInstance().reference

        setContentView(R.layout.activity_play)
        val param1 = intent.getStringExtra(EXTRA_PARAM1)
        val param2 = intent.getStringExtra(EXTRA_PARAM2)

        dataOne.playerName = param1
        dataTwo.playerName = param2
        dataOne.against = param2
        dataTwo.against = param1

        dataOne.serve = true
        dataTwo.serve = false

        serveRole.serve = param1
        serveRole.respond = param2

        textView_playerOne = findViewById(R.id.Player_1)
        textView_playerTwo = findViewById(R.id.Player_2)


        serveMap["top"] = dataOne
        serveMap["down"] = dataTwo

        Logger.i("player one name in Player view" +  dataOne.stringify() + "serve term is " + serveRole.stringify())


        val btnOne = findViewById<Button>(R.id.btn_1)
        btnOne.setOnClickListener {
            Logger.i("clikced 1")
        }

        val btnServeOne = findViewById<Button>(R.id.serve_One)
        val btnServeTwo = findViewById<Button>(R.id.serve_Two)
        val btnSaveData = findViewById<Button>(R.id.save_Data)

        val btn_1 = findViewById<Button>(R.id.btn_1)
        val btn_2 = findViewById<Button>(R.id.btn_2)
        val btn_3 = findViewById<Button>(R.id.btn_3)
        val btn_4 = findViewById<Button>(R.id.btn_4)
        val btn_5 = findViewById<Button>(R.id.btn_5)
        val btn_6 = findViewById<Button>(R.id.btn_6)
        val btn_7 = findViewById<Button>(R.id.btn_7)
        val btn_8 = findViewById<Button>(R.id.btn_8)
        val btn_9 = findViewById<Button>(R.id.btn_9)
        val btn_10 = findViewById<Button>(R.id.btn_10)
        val btn_11 = findViewById<Button>(R.id.btn_11)
        val btn_12 = findViewById<Button>(R.id.btn_12)


        btnServeOne.setOnClickListener {
            Logger.i("serve data is " + btnServeOne.text)
            when (btnServeOne.text) {
                "Serve" ->
                    if(btnServeOne.text != btnServeTwo.text){
                        btnServeOne.setText("Catch")
                        btnServeTwo.setText("Serve")
                    }

                "Catch" ->
                    if(btnServeOne.text != btnServeTwo.text){
                        btnServeOne.setText("Serve")
                        btnServeTwo.setText("Catch")
                    }
            }
            dataOne.serve = !dataOne.serve
            dataTwo.serve = !dataTwo.serve

        }

        btnServeTwo.setOnClickListener {
            Logger.i("serve data is " + btnServeTwo.text)
            when (btnServeOne.text) {
                "Serve" ->
                    if(btnServeOne.text != btnServeTwo.text){
                        btnServeOne.setText("Catch")
                        btnServeTwo.setText("Serve")
                    }

                "Catch" ->
                    if(btnServeOne.text != btnServeTwo.text){
                        btnServeOne.setText("Serve")
                        btnServeTwo.setText("Catch")
                    }
            }

            dataOne.serve = !dataOne.serve
            dataTwo.serve = !dataTwo.serve

        }

        btnSaveData.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            // TODO add reason in Beta version
            builder.setTitle("Save Data")
            builder.setMessage("Select the winner of the round")
            builder.setPositiveButton( dataOne.playerName,
                DialogInterface.OnClickListener { dialog, id ->
                    dataOne.result = "win"
                    dataTwo.result = "loose"
                    Logger.i("upload data " + dataOne.stringify())
                    Logger.i("upload data " + dataTwo.stringify())
                    database.child(dataOne.playerName).child((System.currentTimeMillis()).toString()).push().setValue(dataOne)
                    database.child(dataTwo.playerName).child((System.currentTimeMillis()).toString()).push().setValue(dataTwo)

                    dataOne.spots.clear()
                    dataTwo.spots.clear()
                    dialog.dismiss()
                })
            builder.setNegativeButton( dataTwo.playerName,
                DialogInterface.OnClickListener { dialog, id ->
                    dataOne.result = "loose"
                    dataTwo.result = "win"
                    Logger.i("upload data " + dataOne.stringify())
                    Logger.i("Selected " + dataTwo.stringify())
                    database.child(dataOne.playerName).child((System.currentTimeMillis()).toString()).push().setValue(dataOne)
                    database.child(dataTwo.playerName).child((System.currentTimeMillis()).toString()).push().setValue(dataTwo)
                    dataOne.spots.clear()
                    dataTwo.spots.clear()
                    dialog.dismiss()
                })
            val alert = builder.create()
            alert.show()
        }

        textView_playerOne.setOnClickListener {

            // change player2 name to player1
            var buffer = textView_playerTwo.text
            textView_playerTwo.setText(textView_playerOne.text)
            //change player1 name to player2
            textView_playerOne.setText(buffer)
            if (dataOne.playerName == buffer) {
                serveMap["top"] = dataOne
                serveMap["down"] = dataTwo
                Logger.i("changed status")
            }

            else {
                serveMap["down"] = dataOne
                serveMap["top"] = dataTwo
                Logger.i("changed status")

            }

        }


        textView_playerTwo.setOnClickListener {

            // change player2 name to player1
            var buffer = textView_playerTwo.text
            textView_playerTwo.setText(textView_playerOne.text)
            //change player1 name to player2
            textView_playerOne.setText(buffer)

            if (dataOne.playerName == buffer) {
                serveMap["top"] = dataOne
                serveMap["down"] = dataTwo
                Logger.i("changed status")

            }

            else {
                serveMap["down"] = dataOne
                serveMap["top"] = dataTwo
                Logger.i("changed status")

            }

        }

        btn_1.setOnClickListener{
            serveMap["down"]?.spots!!.add(1)
            Logger.i("added " + 1)
        }

        btn_2.setOnClickListener{
            serveMap["down"]?.spots!!.add(2)
            Logger.i("added " + 2)
        }

        btn_3.setOnClickListener{
            serveMap["down"]?.spots!!.add(3)
            Logger.i("added " + 3)
        }

        btn_4.setOnClickListener{
            serveMap["down"]?.spots!!.add(4)
            Logger.i("added " + 4)
        }

        btn_5.setOnClickListener{
            serveMap["down"]?.spots!!.add(5)
            Logger.i("added " + 5)
        }

        btn_6.setOnClickListener{
            serveMap["down"]?.spots!!.add(6)
            Logger.i("added " + 6)
        }

        btn_7.setOnClickListener{
            serveMap["top"]?.spots!!.add(7)
            Logger.i("added " + 7)
        }

        btn_8.setOnClickListener{
            serveMap["top"]?.spots!!.add(8)
            Logger.i("added " + 8)
        }

        btn_9.setOnClickListener{
            serveMap["top"]?.spots!!.add(9)
            Logger.i("added " + 9)
        }

        btn_10.setOnClickListener{
            serveMap["top"]?.spots!!.add(10)
            Logger.i("added " + 10)
        }

        btn_11.setOnClickListener{
            serveMap["top"]?.spots!!.add(11)
            Logger.i("added " + 11)
        }

        btn_12.setOnClickListener{
            serveMap["top"]?.spots!!.add(12)
            Logger.i("added " + 12)
        }
    }

    override fun onStart() {
        super.onStart()
        textView_playerOne.setText(dataOne.playerName)
        textView_playerTwo.setText(dataTwo.playerName)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN) {
            if (event.keyCode == KeyEvent.KEYCODE_BACK) {
                startActivity(
                    Intent(
                        this.applicationContext,
                        MainActivity::class.java
                    )
                )
                this.finish()
            }
        }
        return super.dispatchKeyEvent(event)
    }

}
