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
                    MediaStore.Audio.Media.IS_MUSIC + "!=0"
                selection += if (albumId != 0L) {
                    " and album_id = $albumId"
                } else {
                    " AND duration>10000 AND ${MediaStore.Audio.Media.DATA} NOT LIKE ?"
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
                    if (albumId != 0L) null else arrayOf("%Whatsapp Audio%"),
                    MediaStore.Audio.Media.DATE_ADDED + " DESC",
                    null
                )
                cursor?.let {
                    if (it.moveToFirst()) {
                        while (cursor.moveToNext() && (if (limit == 0) true else this.size < limit)) {
                            val title =
                                it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
                            val id =
                                it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
                            val album =
                                it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))
                            val path =
                                it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                            val artist =
                                it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                            val duration =
                                it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                            val artistId =
                                it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID))
                            val _albumId =
                                it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))
                            val image =
                                Uri.withAppendedPath(
                                    Uri.parse(Constants.CONTENT_URI),
                                    _albumId.toString()
                                )
                                    .toString()
                            val dateAdded =
                                it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED))

                            val audio = AudioDTO(
                                id,
                                title,
                                album,
                                _albumId,
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
                cursor?.close()
            }
        }

    fun getAlbums(limit: Int = 0) =
        contentResolver.observe(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI).map {
            buildList {
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
                        while (cursor.moveToNext() && (if (limit == 0) true else this.size < limit)) {
                            val id =
                                it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Albums._ID))
                            val album =
                                it.getStringOrNull(it.getColumnIndex(MediaStore.Audio.Albums.ALBUM))
                            val albumId =
                                it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID))
                            val artist =
                                it.getStringOrNull(it.getColumnIndex(MediaStore.Audio.Albums.ARTIST))
                            val numberOfSongs =
                                it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS))
                            val image =
                                Uri.withAppendedPath(
                                    Uri.parse(Constants.CONTENT_URI),
                                    albumId.toString()
                                )
                                    .toString()

                            val albumData = AlbumDTO(
                                id, albumId, album, artist, numberOfSongs, image
                            ).let(::add)


                        }
                    }
                }
                cursor?.close()
            }
        }

    fun getSingleAlbum(albumId: Long) =
        contentResolver.observe(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI).map {
            buildMap {

                val selection = "album_id = $albumId"

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
                    selection,
                    null,
                    MediaStore.Audio.Media.ALBUM + " DESC",
                    null
                )
                cursor?.let {
                    if (it.moveToFirst()) {
                        val id =
                            it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Albums._ID))
                        val album =
                            it.getStringOrNull(it.getColumnIndex(MediaStore.Audio.Albums.ALBUM))
                        val album_id =
                            it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID))
                        val artist =
                            it.getStringOrNull(it.getColumnIndex(MediaStore.Audio.Albums.ARTIST))
                        val numberOfSongs =
                            it.getLongOrNull(it.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS))
                        val image =
                            Uri.withAppendedPath(
                                Uri.parse(Constants.CONTENT_URI),
                                album_id.toString()
                            )
                                .toString()

                        val albumData = AlbumDTO(
                            id, album_id, album, artist, numberOfSongs, image
                        )
                        put(0, albumData)
                    }
                }
                cursor?.close()
            }
        }

    fun getSingleAudio(_id: Long) =
        contentResolver.observe(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI).map {
            buildMap {
                val selection =
                    MediaStore.Audio.Media.IS_MUSIC + "!=0 and ${MediaStore.Audio.Media._ID}=$_id"
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
                        val title =
                            it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
                        val id =
                            it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
                        val album =
                            it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))
                        val path =
                            it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                        val artist =
                            it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                        val duration =
                            it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                        val artistId =
                            it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID))
                        val _albumId =
                            it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))
                        val image =
                            Uri.withAppendedPath(
                                Uri.parse(Constants.CONTENT_URI),
                                _albumId.toString()
                            )
                                .toString()
                        val dateAdded =
                            it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED))

                        val audio = AudioDTO(
                            id,
                            title,
                            album,
                            _albumId,
                            artist,
                            artistId,
                            duration,
                            path,
                            dateAdded,
                            image
                        )
                        put(0, audio)
                    }
                }
                cursor?.close()
            }

        }

}