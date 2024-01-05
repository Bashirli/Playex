package com.bashirli.playex.presentation.ui.screens.albumDetails


import androidx.lifecycle.viewModelScope
import com.bashirli.playex.common.base.BaseViewModel
import com.bashirli.playex.common.base.Effect
import com.bashirli.playex.common.base.Event
import com.bashirli.playex.common.base.State
import com.bashirli.playex.domain.model.AlbumUiModel
import com.bashirli.playex.domain.model.AudioUiModel
import com.bashirli.playex.domain.useCase.AudioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    private val useCase: AudioUseCase,
) : BaseViewModel<AlbumDetailsUiState, AlbumDetailsEvent, AlbumDetailsEffect>() {

    override fun setInitialState(): AlbumDetailsUiState = AlbumDetailsUiState()

    override fun handleEvent(event: AlbumDetailsEvent) {
        when (event) {
            is AlbumDetailsEvent.GetAudioFiles -> {
                getAudios(event.albumId)
            }

            is AlbumDetailsEvent.GetAlbum -> {
                getSingleAlbum(event.albumId)
            }
        }
    }

    private fun getAudios(albumId: Long) {
        viewModelScope.launch {
            setState(getCurrentState().copy(isLoading = true))
            useCase.getAudioFiles(albumId = albumId).collectLatest {
                setState(getCurrentState().copy(isLoading = false, audioFiles = it))
            }
        }
    }

    private fun getSingleAlbum(albumId: Long) {
        viewModelScope.launch {
            setState(getCurrentState().copy(isLoading = true))
            useCase.getSingleAlbum(albumId).collectLatest {
                setState(getCurrentState().copy(isLoading = false, albumItem = it))
            }
        }
    }


}

data class AlbumDetailsUiState(
    val isLoading: Boolean = true,
    val audioFiles: List<AudioUiModel> = emptyList(),
    val albumItem: AlbumUiModel? = null,
) : State

interface AlbumDetailsEvent : Event {
    data class GetAudioFiles(val albumId: Long) : AlbumDetailsEvent

    data class GetAlbum(val albumId: Long) : AlbumDetailsEvent
}

interface AlbumDetailsEffect : Effect {

    data class ShowMessage(val message: String) : AlbumDetailsEffect

}