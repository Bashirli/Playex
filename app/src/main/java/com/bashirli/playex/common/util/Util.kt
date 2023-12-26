package com.bashirli.playex.common.util


fun convertLongToTime(duration: Long): String {
    var out: String = ""
    var hours: Long = 0
    hours = try {
        (duration / 3600000).toLong()
    } catch (e: Exception) {
        e.printStackTrace()
        return out
    }
    val remainingMinutes = (duration - hours * 3600000) / 60000
    var minutes = remainingMinutes.toString()
    if (minutes == "0") {
        minutes = "00"
    }
    val remainingSeconds = duration - hours * 3600000 - remainingMinutes * 60000
    var seconds = remainingSeconds.toString()
    seconds = if (seconds.length < 2) {
        "00"
    } else {
        seconds.substring(0, 2)
    }
    out = if (hours > 0) {
        "$hours:$minutes:$seconds"
    } else {
        "$minutes:$seconds"
    }

    return out
}