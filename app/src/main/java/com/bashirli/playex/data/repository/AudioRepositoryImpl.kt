package com.bashirli.playex.data.repository

import android.util.Log
import com.bashirli.playex.data.mapper.toAlbumModel
import com.bashirli.playex.data.mapper.toAlbumUiModel
import com.bashirli.playex.data.mapper.toAudioUiModel
import com.bashirli.playex.data.service.AudioService
import com.bashirli.playex.domain.model.AlbumUiModel
import com.bashirli.playex.domain.model.AudioUiModel
import com.bashirli.playex.domain.repository.AudioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AudioRepositoryImpl @Inject constructor(
    private val service: AudioService,
) : AudioRepository {
    override fun getAudioFiles(limit: Int, albumId: Long): Flow<List<AudioUiModel>> = flow {
        service.getAudioFiles(limit, albumId)
            .collect {
                val data = it.toAudioUiModel()
                Log.e("test", "col")
                emit(data)
            }
    }

    override fun getAlbums(limit: Int): Flow<List<AlbumUiModel>> = flow {
        service.getAlbums(limit)
            .collect {
                val data = it.toAlbumUiModel()
                emit(data)
            }
    }

    override fun getSingleAlbum(albumId: Long): Flow<AlbumUiModel> = flow {
        service.getSingleAlbum(albumId).collect {
            it[0]?.let { item ->
                emit(item.toAlbumModel())
            }
        }
    }


}