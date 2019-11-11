package io.tennis.statistic.dataStore


class StatsDataDisplay :  AbstractMessage() {
    var totalWinPercent: String = ""
    var serveWinPercent: String = ""
    var returnWinPercent: String = ""
    var shortWin: String = ""
    var longwin:String =  ""
    var total_round: Int = 0
    var total_win: Int = 0
    var serve_win: Int = 0
    var return_win: Int = 0
    var short_win: Int = 0
    var long_win: Int = 0

    fun gameData(totalWinPercent: String, serveWinPercent: String,  returnWinPercent: String, shortWin:String, longwin: String) {
        this.totalWinPercent = totalWinPercent
        this.serveWinPercent = serveWinPercent
        this.returnWinPercent = returnWinPercent
        this.shortWin = shortWin
        this.longwin = longwin
    }
}


