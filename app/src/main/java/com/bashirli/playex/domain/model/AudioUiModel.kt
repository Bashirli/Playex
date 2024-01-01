package com.bashirli.playex.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class AudioUiModel(
    val id: Long,
    val title: String,
    val album: String,
    val albumId: Long,
    val artist: String,
    val artistId: Long,
    val duration: Long,
    val path: String,
    val dateAdded: String,
    val image: String,
)
