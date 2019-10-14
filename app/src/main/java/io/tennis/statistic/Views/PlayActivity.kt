package io.tennis.statistic.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import io.tennis.statistic.R
import io.tennis.statistic.dataStore.AbstractMessage
import java.util.ArrayList

private const val EXTRA_PARAM1 = "io.tennis.statistic.extra.PARAM1"
private const val EXTRA_PARAM2 = "io.tennis.statistic.extra.PARAM2"
class PlayActivity : AppCompatActivity() {

    object gameData: AbstractMessage(){
        var spots: MutableList<MutableList<Int>> = ArrayList()
        var serve: Boolean = false
        var playerName: String = " "
        val gson = Gson()

    }
    private var dataOne: gameData= gameData
    private var dataTwo: gameData= gameData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        val param1 = intent.getStringExtra(EXTRA_PARAM1)
        val param2 = intent.getStringExtra(EXTRA_PARAM2)
        dataOne.playerName = param1
        dataTwo.playerName = param2

        Logger.i("player one name in Player view" +  dataOne.stringify() + " " + "Player2 name " + " " + dataTwo.stringify())


        val btnBack = findViewById<Button>(R.id.button_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_BACK -> return true
            }
        }
        return super.dispatchKeyEvent(event)
    }
}
