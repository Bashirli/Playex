package com.bashirli.playex.di

import android.content.ContentResolver
import android.content.Context
import com.bashirli.playex.data.repository.AudioRepositoryImpl
import com.bashirli.playex.data.service.AudioService
import com.bashirli.playex.data.source.AudioSource
import com.bashirli.playex.data.source.AudioSourceImpl
import com.bashirli.playex.domain.repository.AudioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AudioModule {

    @Provides
    @Singleton
    fun injectContentResolver(@ApplicationContext context: Context) = context.contentResolver

    @Provides
    @Singleton
    fun injectAudioService(contentResolver: ContentResolver) = AudioService(contentResolver)

    @Provides
    @Singleton
    fun injectSource(service: AudioService) = AudioSourceImpl(service) as AudioSource

    @Provides
    @Singleton
    fun injectRepo(source: AudioSource) = AudioRepositoryImpl(source) as AudioRepository

}