package com.bashirli.playex.presentation.ui.screens.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bashirli.playex.R
import com.bashirli.playex.domain.model.AlbumUiModel
import com.bashirli.playex.domain.model.AudioUiModel
import com.bashirli.playex.presentation.ui.components.MainAlbumItem
import com.bashirli.playex.presentation.ui.components.MainAudioItem
import com.bashirli.playex.presentation.ui.components.MainEmptyState
import com.bashirli.playex.presentation.ui.components.MainTextField
import com.bashirli.playex.presentation.ui.theme.Pink29
import com.bashirli.playex.presentation.ui.theme.White99
import com.bashirli.playex.presentation.ui.theme.fontFamily
import kotlinx.coroutines.delay

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {

    val searchText = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val albumList = remember { mutableStateOf(emptyList<AlbumUiModel>()) }

    val shortAudioList = remember { mutableStateOf(emptyList<AudioUiModel>()) }

    val audioList = remember { mutableStateOf(emptyList<AudioUiModel>()) }

    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(key1 = state.value, block = {
        if (!state.value.isLoading) {
            when (effect.value) {
                is SearchUiEffect.ShowMessage -> {
                    val ev = effect.value as SearchUiEffect.ShowMessage
                    Toast.makeText(context, ev.message, Toast.LENGTH_LONG).show()
                }

                else -> Unit
            }
        }
    })

    LaunchedEffect(key1 = searchText.value, block = {
        if (searchText.value.isNotEmpty()) {
            delay(300)
            viewModel.setEvent(SearchUiEvent.SearchAudio(searchText.value))
        }
    })


    Surface(modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Pink29)
        ) {

            Spacer(modifier = modifier.fillMaxSize(0.04f))

            Text(
                modifier = modifier.padding(horizontal = 24.dp),
                text = stringResource(id = R.string.search_screen),
                fontSize = 24.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W700,
                color = Color.White
            )

            Text(
                modifier = modifier.padding(horizontal = 24.dp),
                text = stringResource(id = R.string.search_screen_title),
                fontSize = 14.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                color = White99
            )
            Spacer(modifier = modifier.size(16.dp))

            MainTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                text = searchText,
                label = R.string.search_title,
                icon = R.drawable.search_icon,
                action = ImeAction.Search,
            )


            audioList.value = state.value.audioList
            shortAudioList.value = state.value.initialAudio
            albumList.value = state.value.albumList


            if (searchText.value.isEmpty()) {
                LazyColumn {
                    item {
                        Text(
                            modifier = modifier
                                .padding(horizontal = 24.dp)
                                .padding(top = 16.dp),
                            text = stringResource(id = R.string.fav_album),
                            fontSize = 24.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.W600,
                            color = Color.White
                        )
                        Spacer(modifier = modifier.size(16.dp))

                        if (albumList.value.isEmpty()) {
                            MainEmptyState(message = R.string.empty_state)
                        } else {
                            LazyRow {
                                items(
                                    count = albumList.value.size,
                                    key = {
                                        albumList.value[it].id
                                    }
                                ) { position ->
                                    MainAlbumItem(
                                        onNavigate = {
                                            onNavigate(it + "?${albumList.value[position].albumId}")
                                        },
                                        onClickShowAll = {

                                        },
                                        isLast = albumList.value.size - 1 == position,
                                        item = albumList.value[position]
                                    )
                                }
                            }
                        }

                        if (shortAudioList.value.isNotEmpty()) {
                            Text(
                                modifier = modifier
                                    .padding(horizontal = 24.dp)
                                    .padding(top = 16.dp),
                                text = stringResource(id = R.string.did_you_like_it),
                                fontSize = 24.sp,
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.W600,
                                color = Color.White
                            )
                        }
                    }
                    items(
                        count = shortAudioList.value.size,
                        key = {
                            shortAudioList.value[it].id
                        }
                    ) { position ->
                        MainAudioItem(item = shortAudioList.value[position], onClick = {
                            onNavigate(it + "?${shortAudioList.value[position].id}")
                        })
                    }
                }
            } else {
                if (audioList.value.isNotEmpty()) {
                    Spacer(modifier = modifier.size(16.dp))
                    LazyColumn {
                        items(
                            count = audioList.value.size,
                            key = {
                                audioList.value[it].id
                            }
                        ) { position ->
                            MainAudioItem(item = audioList.value[position], onClick = {
                                onNavigate(it + "?${audioList.value[position].id}")
                            })
                        }
                    }
                } else if (audioList.value.isEmpty() && !(state.value.isLoading)) {
                    MainEmptyState(
                        modifier = modifier.fillMaxSize(),
                        message = R.string.empty_state
                    )
                }
            }


        }

    }


}