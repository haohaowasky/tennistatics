package io.tennis.statistic.dataStore
import java.util.ArrayList


class gameData :  AbstractMessage() {
    var spots: MutableList<MutableList<Int>> = ArrayList()
    var serve: Boolean = false
    var playerName: String = ""

    fun gameData(name: String, server: Boolean,  spot: MutableList<MutableList<Int>>) {
        this.playerName = name
        this.serve = server
        this.spots = spot
    }
}


