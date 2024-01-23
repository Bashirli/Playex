package com.bashirli.playex.domain.useCase

import com.bashirli.playex.domain.repository.AudioRepository
import javax.inject.Inject

class AudioUseCase @Inject constructor(private val repo: AudioRepository) {

    fun getAlbums(limit: Int = 0) = repo.getAlbums(limit)

    fun getAudioFiles(limit: Int = 0, albumId: Long = 0L) = repo.getAudioFiles(limit, albumId)

    fun getSingleAlbum(albumId: Long) = repo.getSingleAlbum(albumId)

    fun getSingleAudio(id: Long) = repo.getSingleAudio(id)

    fun searchAudio(query: String) = repo.searchAudio(query)

}