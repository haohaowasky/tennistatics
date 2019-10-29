package io.tennis.statistic.Views
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.orhanobut.logger.Logger
import java.util.ArrayList




private const val EXTRA_PARAM1 = "io.tennis.statistic.extra.PARAM1"
private const val EXTRA_PARAM2 = "io.tennis.statistic.extra.PARAM2"
private const val EXTRA_PARAM3 = "io.tennis.statistic.extra.PARAM3"
private const val EXTRA_PARAM4 = "io.tennis.statistic.extra.PARAM4"

class MainActivity : AppCompatActivity() {

    val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build())


    object gameData {
        var spots: MutableList<MutableList<Int>> = ArrayList()
        var serve: Boolean = false
        var playerName: String = " "
    }

    private var playerOneName: String = ""
    private var playerTwoName: String = ""
    private var dataOne: gameData = gameData
    private var dataTwo: gameData= gameData
    private var userName: String = ""
    private var userID: String= ""


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                Logger.i("user ID is " + user?.displayName + " and the email is " + user?.uid)
                userName = user?.displayName.toString()
                userID = user?.uid.toString()

            } else {
                Logger.i("Sign in failed")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(io.tennis.statistic.R.layout.activity_main)

        var playerOne = findViewById(io.tennis.statistic.R.id.playerOne) as EditText
        var playerTwo= findViewById(io.tennis.statistic.R.id.playerTwo) as EditText

        val btnStart = findViewById<Button>(io.tennis.statistic.R.id.button_back)
        val btnSignIn = findViewById<Button>(io.tennis.statistic.R.id.btn_signIn)



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
                putExtra(EXTRA_PARAM3, userName)
                putExtra(EXTRA_PARAM4, userID)
            }
            startActivity(intent)
            this.finish()
        }


        btnSignIn.setOnClickListener{
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                1)
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
