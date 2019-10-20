package io.tennis.statistic.Views
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import io.tennis.statistic.R
import com.orhanobut.logger.Logger
import java.util.ArrayList

private const val EXTRA_PARAM1 = "io.tennis.statistic.extra.PARAM1"
private const val EXTRA_PARAM2 = "io.tennis.statistic.extra.PARAM2"
class MainActivity : AppCompatActivity() {

    object gameData {
        var spots: MutableList<MutableList<Int>> = ArrayList()
        var serve: Boolean = false
        var playerName: String = " "
    }

    private var playerOneName: String = ""
    private var playerTwoName: String = ""
    private var dataOne: gameData= gameData
    private var dataTwo: gameData= gameData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var playerOne = findViewById(R.id.playerOne) as EditText
        var playerTwo= findViewById(R.id.playerTwo) as EditText

        val btnStart = findViewById<Button>(R.id.button_back)


        btnStart.setOnClickListener {
            playerOneName = playerOne.getText().toString()
            playerTwoName = playerTwo.getText().toString()
//            Logger.i("player one name" +  playerOneName + " " + "Player2 name " + " " + playerTwoName)

            dataOne.playerName = playerOneName
            dataTwo.playerName = playerTwoName

            Logger.i("player one name" +  dataOne.toString() + " " + "Player2 name " + " " + dataTwo.toString())

            val intent = Intent(this, PlayActivity::class.java).apply {
                putExtra(EXTRA_PARAM1, playerOneName)
                putExtra(EXTRA_PARAM2, playerTwoName)
            }
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
