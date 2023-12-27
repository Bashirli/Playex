package com.bashirli.playex.data.repository

import com.bashirli.playex.common.Resource
import com.bashirli.playex.data.mapper.toAudioUiModel
import com.bashirli.playex.data.service.AudioService
import com.bashirli.playex.domain.model.AlbumUiModel
import com.bashirli.playex.domain.model.AudioUiModel
import com.bashirli.playex.domain.repository.AudioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AudioRepositoryImpl @Inject constructor(
    private val service: AudioService,
) : AudioRepository {
    override fun getAudioFiles(limit: Int, albumId: Long): Flow<List<AudioUiModel>> = flow {
        service.getAudioFiles(limit, albumId)
            .collect {
                emit(it.toAudioUiModel())
            }

    }.flowOn(Dispatchers.IO)

    override fun getAlbums(limit: Int): Flow<Resource<List<AlbumUiModel>>> = flow {
        emit(Resource.Loading)

    }


}