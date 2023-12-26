package com.bashirli.playex.common.util

fun String.ellipsisString(): String {
    return String.format("%.15s...", this)
}

fun String.ellipsis25String(): String {
    return String.format("%.25s...", this)
}
