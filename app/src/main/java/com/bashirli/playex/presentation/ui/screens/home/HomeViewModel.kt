package com.bashirli.playex.presentation.ui.screens.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bashirli.playex.common.base.BaseViewModel
import com.bashirli.playex.common.base.Effect
import com.bashirli.playex.common.base.Event
import com.bashirli.playex.common.base.State
import com.bashirli.playex.common.util.ellipsisString
import com.bashirli.playex.domain.model.AlbumUiModel
import com.bashirli.playex.domain.model.AudioUiModel
import com.bashirli.playex.domain.useCase.AudioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val audioUseCase: AudioUseCase,
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiEffect>() {

    init {
        getInitialData()
    }

    override fun setInitialState(): HomeUiState = HomeUiState()


    override fun handleEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.GetAlbums -> getAlbums()
            is HomeUiEvent.GetLimitedAlbums -> getLimitedAlbums(event.limit)
            HomeUiEvent.GetInitialData -> getInitialData()
        }
    }

    private fun getInitialData() {
        viewModelScope.launch {
            audioUseCase.getAlbums(8).handleResult(
                onComplete = {
                    val nameList =
                        ArrayList(it.map { if (it.album.length > 10) it.album.ellipsisString() else it.album })
                    setState(
                        getCurrentState().copy(
                            isLoading = false,
                            limitedAlbums = it,
                            albumNames = nameList
                        )
                    )
                    getAudio(5)
                },
                onError = {
                    setState(getCurrentState().copy(isLoading = false))
                    setEffect(HomeUiEffect.ShowMessage(it.localizedMessage as String))
                },
                onLoading = {
                    setState(getCurrentState().copy(isLoading = true))
                }
            )
        }
    }

    private fun getAlbums() {
        viewModelScope.launch {
            audioUseCase.getAlbums().handleResult(
                onComplete = {
                    setState(
                        getCurrentState().copy(
                            isLoading = false,
                            albums = it
                        )
                    )
                },
                onError = {
                    setState(getCurrentState().copy(isLoading = false))
                    setEffect(HomeUiEffect.ShowMessage(it.localizedMessage as String))
                },
                onLoading = {
                    setState(getCurrentState().copy(isLoading = true))
                }
            )
        }
    }

    private fun getLimitedAlbums(limit: Int = 0) {
        viewModelScope.launch {
            audioUseCase.getAlbums(limit).handleResult(
                onComplete = {
                    val nameList =
                        ArrayList(it.map { if (it.album.length > 10) it.album.ellipsisString() else it.album })
                    setState(
                        getCurrentState().copy(
                            isLoading = false,
                            limitedAlbums = it,
                            albumNames = nameList
                        )
                    )
                },
                onError = {
                    setState(getCurrentState().copy(isLoading = false))
                    setEffect(HomeUiEffect.ShowMessage(it.localizedMessage as String))
                },
                onLoading = {
                    setState(getCurrentState().copy(isLoading = true))
                }
            )
        }
    }

    private fun getAudio(limit: Int = 0) {
        viewModelScope.launch {
            audioUseCase.getAudioFiles(limit).handleResult(
                onComplete = {
                    setState(
                        getCurrentState().copy(
                            isLoading = false,
                            audioFiles = it
                        )
                    )
                },
                onError = {
                    setState(getCurrentState().copy(isLoading = false))
                    setEffect(HomeUiEffect.ShowMessage(it.localizedMessage as String))
                },
                onLoading = {
                    setState(getCurrentState().copy(isLoading = true))
                }
            )
        }
    }

}

data class HomeUiState(
    val isLoading: Boolean = true,
    val albums: List<AlbumUiModel> = emptyList(),
    val limitedAlbums: List<AlbumUiModel> = emptyList(),
    val albumNames: ArrayList<String> = arrayListOf(),
    val audioFiles: List<AudioUiModel> = emptyList(),
) : State

sealed interface HomeUiEvent : Event {

    data object GetInitialData : HomeUiEvent

    data object GetAlbums : HomeUiEvent

    data class GetLimitedAlbums(val limit: Int) : HomeUiEvent
}

sealed interface HomeUiEffect : Effect {
    data class ShowMessage(val message: String = "") : HomeUiEffect
}