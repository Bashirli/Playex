package com.bashirli.playex.domain.model

data class AlbumUiModel(
    val id: Long,
    val albumId: Long,
    val album: String,
    val artist: String,
    val numberOfSongs: Long,
    val image: String,
)
