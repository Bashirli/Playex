package com.bashirli.playex.data.repository

import com.bashirli.playex.common.Resource
import com.bashirli.playex.data.mapper.toAlbumUiModel
import com.bashirli.playex.data.mapper.toAudioUiModel
import com.bashirli.playex.data.source.AudioSource
import com.bashirli.playex.domain.model.AlbumUiModel
import com.bashirli.playex.domain.model.AudioUiModel
import com.bashirli.playex.domain.repository.AudioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AudioRepositoryImpl @Inject constructor(
    private val source: AudioSource,
) : AudioRepository {
    override fun getAudioFiles(limit: Int): Flow<Resource<List<AudioUiModel>>> = flow {
        emit(Resource.Loading)
        when (val response = source.getAudioFiles(limit)) {
            is Resource.Error -> emit(Resource.Error(response.throwable))
            Resource.Loading -> Unit
            is Resource.Success -> emit(Resource.Success(response.result?.toAudioUiModel()))
        }
    }

    override fun getAlbums(limit: Int): Flow<Resource<List<AlbumUiModel>>> = flow {
        emit(Resource.Loading)
        when (val response = source.getAlbums(limit)) {
            is Resource.Error -> emit(Resource.Error(response.throwable))
            Resource.Loading -> Unit
            is Resource.Success -> emit(Resource.Success(response.result?.toAlbumUiModel()))
        }
    }


}