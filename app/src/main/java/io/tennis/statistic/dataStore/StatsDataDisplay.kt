package io.tennis.statistic.dataStore


class StatsDataDisplay :  AbstractMessage() {
    var totalWinPercent: String = ""
    var serveWinPercent: String = ""
    var returnWinPercent: String = ""
    var shortWin: String = ""
    var longwin:String =  ""


    fun gameData(totalWinPercent: String, serveWinPercent: String,  returnWinPercent: String, shortWin:String, longwin: String) {
        this.totalWinPercent = totalWinPercent
        this.serveWinPercent = serveWinPercent
        this.returnWinPercent = returnWinPercent
        this.shortWin = shortWin
        this.longwin = longwin
    }
}


