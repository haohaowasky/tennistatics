package io.tennis.statistic.dataStore
import java.util.ArrayList


class gameData :  AbstractMessage() {
    var spots: MutableList<MutableList<Int>> = ArrayList()
    var serve: Boolean = false
    var playerName: String = ""
    var result:String =  ""
    var notes:String = ""

    fun gameData(playerName: String, serve: Boolean,  spots: MutableList<MutableList<Int>>, result:String, notes: String) {
        this.playerName = playerName
        this.serve = serve
        this.spots = spots
        this.result = result
        this.notes = notes
    }
}


