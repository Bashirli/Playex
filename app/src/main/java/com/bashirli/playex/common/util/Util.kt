package com.bashirli.playex.common.util


fun convertLongToTime(duration: Long): String {
    val out: String

    val hours: Long = duration / 3600000
    val minutes: Long = (duration % 3600000) / 60000
    val seconds: Long = (duration % 60000) / 1000

    val formattedMinutes = if (minutes < 10) "0$minutes" else "$minutes"
    val formattedSeconds = if (seconds < 10) "0$seconds" else "$seconds"

    out = if (hours > 0) {
        "$hours:$formattedMinutes:$formattedSeconds"
    } else {
        "$formattedMinutes:$formattedSeconds"
    }

    return out
}