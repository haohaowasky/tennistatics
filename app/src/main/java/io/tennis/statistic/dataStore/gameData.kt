package io.tennis.statistic.dataStore
import java.util.ArrayList


class gameData :  AbstractMessage() {
    var spots: MutableList<Int> = ArrayList()
    var spotsTotal: MutableList<Int> = ArrayList()
    var serve: Boolean = false
    var playerName: String = ""
    var result:String =  ""
    var against: String = ""
    var timeStamp: String = ""

    fun gameData(playerName: String, serve: Boolean,  spots: MutableList<Int>, result:String, against: String, spotsTotal: MutableList<Int>, timeStamp: String) {
        this.playerName = playerName
        this.serve = serve
        this.spots = spots
        this.result = result
        this.against = against
        this.spotsTotal = spotsTotal
        this.timeStamp = timeStamp

    }
}


