package com.bashirli.playex.data.mapper

import com.bashirli.playex.data.dto.AlbumDTO
import com.bashirli.playex.data.dto.AudioDTO
import com.bashirli.playex.domain.model.AlbumUiModel
import com.bashirli.playex.domain.model.AudioUiModel

fun List<AlbumDTO>.toAlbumUiModel() = map {
    with(it) {
        AlbumUiModel(
            id = id ?: 0L,
            albumId = albumId ?: 0L,
            album = album.orEmpty(),
            artist = artist.orEmpty(),
            numberOfSongs = numberOfSongs ?: 0L,
            image = image.orEmpty()
        )
    }
}

fun List<AudioDTO>.toAudioUiModel() = map {
    with(it) {
        AudioUiModel(
            id = id ?: 0L,
            albumId = albumId ?: 0L,
            album = album.orEmpty(),
            artist = artist.orEmpty(),
            title = title.orEmpty(),
            artistId = artistId ?: 0L,
            duration = duration ?: 0L,
            path = path.orEmpty(),
            dateAdded = dateAdded.orEmpty(),
            image = image.orEmpty()
        )
    }
}