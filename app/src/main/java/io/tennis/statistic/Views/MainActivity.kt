package io.tennis.statistic.Views
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
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
private const val EXTRA_PARAM_TOKEN = "io.tennis.statistic.extra.TOKEN"


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
    private var ifSignIn: Boolean = false


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var playerOne = findViewById(io.tennis.statistic.R.id.playerOne) as EditText
        var playerTwo= findViewById(io.tennis.statistic.R.id.playerTwo) as EditText
        var btnStart = findViewById<Button>(io.tennis.statistic.R.id.button_back)
        var btnSignIn = findViewById<Button>(io.tennis.statistic.R.id.btn_signIn)
        var btn_getPlayer = findViewById<Button>(io.tennis.statistic.R.id.btn_getData)

        btnSignIn.setText("Sign Out")

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

        btn_getPlayer.setOnClickListener{
            val intent = Intent(this, PlayerView::class.java).apply {
                putExtra(EXTRA_PARAM_TOKEN, userID)
            }
            startActivity(intent)
        }


        if (requestCode == 1) {
            val response = IdpResponse.fromResultIntent(data)
            ifSignIn = true
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                Logger.i("user ID is " + user?.displayName + " and the email is " + user?.uid)
                userName = user?.displayName.toString()
                userID = user?.uid.toString()

            } else {
                Logger.i("Sign in failed")
                btnSignIn.setText("Sign In")
                ifSignIn = false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(io.tennis.statistic.R.layout.activity_main)
//        var btnStart = findViewById<Button>(io.tennis.statistic.R.id.button_back)


        var btnSignIn = findViewById<Button>(io.tennis.statistic.R.id.btn_signIn)

        btnSignIn.setOnClickListener{

            if (ifSignIn == false) {
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                    1
                )
            } else {
                AuthUI.getInstance().signOut(this).addOnCompleteListener {
                    // user is now signed out
                    btnSignIn.setText("Sign in")
                    ifSignIn = false
                }
            }

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
