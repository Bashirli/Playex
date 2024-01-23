package com.bashirli.playex.presentation.ui.screens.search

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
class SearchViewModel @Inject constructor(
    private val useCase: AudioUseCase,
) : BaseViewModel<SearchUiState, SearchUiEvent, SearchUiEffect>() {

    init {
        getAlbums()
    }

    override fun setInitialState(): SearchUiState = SearchUiState()

    override fun handleEvent(event: SearchUiEvent) {
        when (event) {
            SearchUiEvent.InitialData -> {
                getAlbums()
            }

            is SearchUiEvent.SearchAudio -> {
                searchAudio(event.query)
            }
        }
    }

    private fun searchAudio(query: String) {
        setState(getCurrentState().copy(isLoading = true))
        viewModelScope.launch {
            useCase.searchAudio(query).collectLatest {
                setState(getCurrentState().copy(isLoading = false, audioList = it))
            }
        }
    }

    private fun getAlbums() {
        setState(getCurrentState().copy(isLoading = true))
        viewModelScope.launch {
            useCase.getAlbums(7).collectLatest {
                setState(getCurrentState().copy(albumList = it))
                getInitialAudio()
            }
        }
    }

    private fun getInitialAudio() {
        viewModelScope.launch {
            useCase.getAudioFiles(7).collectLatest {
                setState(getCurrentState().copy(isLoading = false, initialAudio = it))
            }
        }
    }


}

data class SearchUiState(
    val isLoading: Boolean = true,
    val audioList: List<AudioUiModel> = emptyList(),
    val initialAudio: List<AudioUiModel> = emptyList(),
    val albumList: List<AlbumUiModel> = emptyList(),
) : State

sealed interface SearchUiEffect : Effect {
    data class ShowMessage(val message: String) : SearchUiEffect
}

sealed interface SearchUiEvent : Event {

    data class SearchAudio(val query: String) : SearchUiEvent

    data object InitialData : SearchUiEvent

}