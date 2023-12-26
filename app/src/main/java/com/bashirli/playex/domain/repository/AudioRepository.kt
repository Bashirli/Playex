package com.bashirli.playex.domain.repository

import com.bashirli.playex.common.Resource
import com.bashirli.playex.domain.model.AlbumUiModel
import com.bashirli.playex.domain.model.AudioUiModel
import kotlinx.coroutines.flow.Flow

interface AudioRepository {

    fun getAudioFiles(limit: Int): Flow<Resource<List<AudioUiModel>>>

    fun getAlbums(limit: Int): Flow<Resource<List<AlbumUiModel>>>


}