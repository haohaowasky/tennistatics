package io.tennis.statistic.Views

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import com.orhanobut.logger.Logger
import io.tennis.statistic.R
import io.tennis.statistic.dataStore.gameData
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTouch
import io.tennis.statistic.dataStore.AbstractMessage
import java.security.AccessController.getContext

private const val EXTRA_PARAM1 = "io.tennis.statistic.extra.PARAM1"
private const val EXTRA_PARAM2 = "io.tennis.statistic.extra.PARAM2"
class PlayActivity : AppCompatActivity() {


    private var dataOne= gameData()
    private var dataTwo= gameData()

    private lateinit var textView_playerOne: Button
    private lateinit var textView_playerTwo: Button

    data class Serve (var serve: String , var respond: String) :  AbstractMessage()

    private var serveRole = Serve("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
//        mPlayerText1 = findViewById<TextView>(R.id.playerOne)

        setContentView(R.layout.activity_play)
        val param1 = intent.getStringExtra(EXTRA_PARAM1)
        val param2 = intent.getStringExtra(EXTRA_PARAM2)

        dataOne.playerName = param1
        dataTwo.playerName = param2

        serveRole.serve = param1
        serveRole.respond = param2

        textView_playerOne = findViewById(R.id.Player_1)
        textView_playerTwo = findViewById(R.id.Player_2)






        Logger.i("player one name in Player view" +  dataOne.stringify() + "serve term is " + serveRole.stringify())


        val btnOne = findViewById<Button>(R.id.btn_1)
        btnOne.setOnClickListener {
            Logger.i("clikced 1")
        }

        val btnServeOne = findViewById<Button>(R.id.serve_One)
        val btnServeTwo = findViewById<Button>(R.id.serve_Two)
        val btnSaveData = findViewById<Button>(R.id.save_Data)

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
        }

        btnSaveData.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            // TODO add reason in Beta version
            builder.setTitle("Save Data")
            builder.setMessage("Select the winner of the round")
            builder.setPositiveButton( serveRole.serve,
                DialogInterface.OnClickListener { dialog, id ->
                    Logger.i("Selected " + serveRole.serve)
                    dialog.dismiss()
                })
            builder.setNegativeButton(serveRole.respond,
                DialogInterface.OnClickListener { dialog, id ->
                    Logger.i("Selected " + serveRole.respond)
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
        }


        textView_playerTwo.setOnClickListener {

            // change player2 name to player1
            var buffer = textView_playerTwo.text
            textView_playerTwo.setText(textView_playerOne.text)

            //change player1 name to player2
            textView_playerOne.setText(buffer)
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
