package com.bashirli.playex.data.source

import android.content.ContentResolver
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.bashirli.playex.common.Resource
import com.bashirli.playex.data.dto.AlbumDTO
import com.bashirli.playex.data.dto.AudioDTO
import com.bashirli.playex.data.service.AudioService
import java.io.File
import javax.inject.Inject

class AudioSourceImpl constructor(
    private val service: AudioService,
) : AudioSource {

    override fun getAudioFiles(limit: Int): Resource<List<AudioDTO>> =
        handleResult { service.getAudioFiles(limit) }

    override fun getAlbums(limit: Int): Resource<List<AlbumDTO>> =
        handleResult { service.getAlbums(limit) }


    private fun <T> handleResult(response: () -> T): Resource<T> {
        return try {
            val data = response.invoke()
            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}