package com.bashirli.playex.presentation.ui.screens.player

import android.media.MediaPlayer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.bashirli.playex.common.base.BaseViewModel
import com.bashirli.playex.common.base.Effect
import com.bashirli.playex.common.base.Event
import com.bashirli.playex.common.base.State
import com.bashirli.playex.domain.model.AudioUiModel
import com.bashirli.playex.domain.useCase.AudioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val useCase: AudioUseCase,
    private val mediaPlayer: MediaPlayer,
) : BaseViewModel<PlayerUiState, PlayerUiEvent, PlayerUiEffect>() {


    private val isFinished = mutableStateOf(false)

    private val pathState = mutableStateOf(false)

    init {
        if (mediaPlayer.isPlaying) {
            stopAudio()
            isFinished.value = true
        }
    }


    override fun setInitialState(): PlayerUiState = PlayerUiState()

    override fun handleEvent(event: PlayerUiEvent) {
        when (event) {
            is PlayerUiEvent.GetAudio -> {
                getAudio(event.id)
            }

            PlayerUiEvent.PauseAudio -> pauseAudio()
            is PlayerUiEvent.PlayAudio -> playAudio(event.path)
            PlayerUiEvent.StopAudio -> stopAudio()
            PlayerUiEvent.GetCurrentTime -> getCurrentTime()
            is PlayerUiEvent.ChangePlayTime -> changePlayTime(event.mSec)
        }
    }

    private fun getAudio(id: Long) {
        viewModelScope.launch {
            setState(getCurrentState().copy(isLoading = true, audioUiModel = null))
            useCase.getSingleAudio(id).collectLatest {
                setState(getCurrentState().copy(isLoading = false, audioUiModel = it))
            }
        }
    }

    private fun playAudio(path: String) {
        if (isFinished.value) {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepare()
            pathState.value = !pathState.value
        } else {
            if (!pathState.value) {
                mediaPlayer.setDataSource(path)
                mediaPlayer.prepare()
                pathState.value = !pathState.value
            }
        }
        isFinished.value = false
        mediaPlayer.start()
    }

    private fun pauseAudio() {
        if (mediaPlayer.isPlaying) mediaPlayer.pause()
    }

    private fun stopAudio() {
        mediaPlayer.stop()
    }

    private fun changePlayTime(mSec: Int) {
        if (pathState.value) {
            mediaPlayer.seekTo(mSec)
            getCurrentTime()
        }
    }

    private fun getCurrentTime() {
        if (mediaPlayer.isPlaying) {
            setState(getCurrentState().copy(currentTime = mediaPlayer.currentPosition))
        }
    }

}

data class PlayerUiState(
    val isLoading: Boolean = true,
    val currentTime: Int = 0,
    val audioUiModel: AudioUiModel? = null,
) : State

sealed interface PlayerUiEvent : Event {

    data class GetAudio(val id: Long) : PlayerUiEvent

    data class PlayAudio(val path: String) : PlayerUiEvent

    data object GetCurrentTime : PlayerUiEvent

    data object PauseAudio : PlayerUiEvent

    data object StopAudio : PlayerUiEvent

    data class ChangePlayTime(val mSec: Int) : PlayerUiEvent

}

sealed interface PlayerUiEffect : Effect {

    data class ShowMessage(val message: String) : PlayerUiEffect

}