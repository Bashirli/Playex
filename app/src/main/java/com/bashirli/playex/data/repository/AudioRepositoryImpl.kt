package com.bashirli.playex.data.repository

import com.bashirli.playex.data.mapper.toAlbumModel
import com.bashirli.playex.data.mapper.toAlbumUiModel
import com.bashirli.playex.data.mapper.toAudioModel
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

    override fun getSingleAudio(id: Long): Flow<AudioUiModel> = flow {
        service.getSingleAudio(id).collect {
            it[0]?.let { item ->
                emit(item.toAudioModel())
            }
        }
    }

    override fun searchAudio(query: String): Flow<List<AudioUiModel>> = flow {
        service.searchAudio(query).collect {
            val data = it.toAudioUiModel()
            emit(data)
        }
    }


}