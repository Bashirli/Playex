package com.bashirli.playex.data.source

import com.bashirli.playex.common.Resource
import com.bashirli.playex.data.dto.AlbumDTO
import com.bashirli.playex.data.dto.AudioDTO

interface AudioSource {

    fun getAudioFiles(limit: Int): Resource<List<AudioDTO>>

    fun getAlbums(limit: Int): Resource<List<AlbumDTO>>


}