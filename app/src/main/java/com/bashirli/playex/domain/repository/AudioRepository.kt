package com.bashirli.playex.domain.repository

import com.bashirli.playex.domain.model.AlbumUiModel
import com.bashirli.playex.domain.model.AudioUiModel
import kotlinx.coroutines.flow.Flow

interface AudioRepository {

    fun getAudioFiles(limit: Int, albumId: Long): Flow<List<AudioUiModel>>

    fun getAlbums(limit: Int): Flow<List<AlbumUiModel>>

    fun getSingleAlbum(albumId: Long): Flow<AlbumUiModel>


}