package com.bashirli.playex.common.util


fun convertLongToTime(duration: Long): String {
    var out: String = ""
    var hours: Long = 0
    var minutes: Long = 0
    var seconds: Long = 0

    hours = duration / 3600000
    minutes = (duration % 3600000) / 60000
    seconds = (duration % 60000) / 1000

    val formattedMinutes = if (minutes < 10) "0$minutes" else "$minutes"
    val formattedSeconds = if (seconds < 10) "0$seconds" else "$seconds"

    out = if (hours > 0) {
        "$hours:$formattedMinutes:$formattedSeconds"
    } else {
        "$formattedMinutes:$formattedSeconds"
    }

    return out
}