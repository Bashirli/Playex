package com.bashirli.playex.data.service

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.bashirli.playex.common.util.Constants
import com.bashirli.playex.data.dto.AlbumDTO
import com.bashirli.playex.data.dto.AudioDTO
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AudioService @Inject constructor(
    private val contentResolver: ContentResolver,
) {

    fun getAudioFiles(limit: Int = 0, albumId: Long = 0L) = contentResolver
        .observe(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
        .map {
            buildList {
                var selection =
                    MediaStore.Audio.Media.IS_MUSIC + "!=0 AND ${MediaStore.Audio.Media.DATA} NOT LIKE ?"
                if (albumId != 0L) {
                    selection += " and album_id = $albumId"
                }
                val projection = arrayOf(
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ALBUM,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.DATE_ADDED,
                    MediaStore.Audio.Media.ARTIST_ID,
                    MediaStore.Audio.Media.ALBUM_ID,
                )
                val cursor = contentResolver.query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    arrayOf("%Whatsapp Audio%"),
                    MediaStore.Audio.Media.DATE_ADDED + " DESC",
                    null
                )
                cursor?.let {
                    if (it.moveToFirst()) {
                        while (cursor.moveToNext() && (if (limit == 0) true else this.size < limit)) {
                            val title =
                                it.getStringOrNull(it.getColumnIndex(MediaStore.Audio.Media.TITLE))
                            val id = it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Media._ID))
                            val album =
                                it.getStringOrNull(it.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                            val path =
                                it.getStringOrNull(it.getColumnIndex(MediaStore.Audio.Media.DATA))
                            val artist =
                                it.getStringOrNull(it.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                            val duration =
                                it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Media.DURATION))
                            val artistId =
                                it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID))
                            val albumId =
                                it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
                            val image =
                                Uri.withAppendedPath(
                                    Uri.parse(Constants.CONTENT_URI),
                                    albumId.toString()
                                )
                                    .toString()
                            val dateAdded =
                                it.getStringOrNull(it.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED))

                            val audio = AudioDTO(
                                id,
                                title,
                                album,
                                albumId,
                                artist,
                                artistId,
                                duration,
                                path,
                                dateAdded,
                                image
                            ).let(::add)

                        }
                    }
                }
            }
        }

    /*
        fun getAudioFiles(limit: Int = 0, albumId: Long = 0L): List<AudioDTO> {
            val audioList = arrayListOf<AudioDTO>()
            var selection = MediaStore.Audio.Media.IS_MUSIC + "!=0 and duration > 60000"
            if (albumId != 0L) {
                selection += " and album_id = $albumId"
            }
            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATE_ADDED,
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Media.ALBUM_ID,
            )
            val cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                MediaStore.Audio.Media.DATE_ADDED + " DESC",
                null
            )
            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        val title = it.getStringOrNull(it.getColumnIndex(MediaStore.Audio.Media.TITLE))
                        val id = it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Media._ID))
                        val album = it.getStringOrNull(it.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                        val path = it.getStringOrNull(it.getColumnIndex(MediaStore.Audio.Media.DATA))
                        val artist =
                            it.getStringOrNull(it.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                        val duration =
                            it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Media.DURATION))
                        val artistId =
                            it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID))
                        val albumId =
                            it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
                        val image =
                            Uri.withAppendedPath(Uri.parse(Constants.CONTENT_URI), albumId.toString())
                                .toString()
                        val dateAdded =
                            it.getStringOrNull(it.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED))

                        val audio = AudioDTO(
                            id,
                            title,
                            album,
                            albumId,
                            artist,
                            artistId,
                            duration,
                            path,
                            dateAdded,
                            image
                        )

                        val file = File(audio.path.orEmpty())
                        if (file.exists()) {
                            audioList.add(audio)
                        }
                    } while (cursor.moveToNext() && (if (limit == 0) true else audioList.size < limit))
                }
            }
            return audioList
        }


     */
    fun getAlbums(limit: Int = 0): List<AlbumDTO> {

        val albumList = arrayListOf<AlbumDTO>()

        val projection = arrayOf(
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM_ID,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ARTIST
        )
        val cursor = contentResolver.query(
            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            MediaStore.Audio.Media.ALBUM + " DESC",
            null
        )
        cursor?.let {
            if (it.moveToFirst()) {
                do {
                    val id = it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Albums._ID))
                    val album = it.getStringOrNull(it.getColumnIndex(MediaStore.Audio.Albums.ALBUM))
                    val albumId =
                        it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID))
                    val artist =
                        it.getStringOrNull(it.getColumnIndex(MediaStore.Audio.Albums.ARTIST))
                    val numberOfSongs =
                        it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS))
                    val image =
                        Uri.withAppendedPath(Uri.parse(Constants.CONTENT_URI), albumId.toString())
                            .toString()

                    val albumData = AlbumDTO(
                        id, albumId, album, artist, numberOfSongs, image
                    )
                    albumList.add(albumData)

                } while (cursor.moveToNext() && (if (limit == 0) true else albumList.size < limit))
            }
        }
        return albumList
    }


}