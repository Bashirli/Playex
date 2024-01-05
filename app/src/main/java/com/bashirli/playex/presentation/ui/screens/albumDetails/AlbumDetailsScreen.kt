package com.bashirli.playex.presentation.ui.screens.albumDetails

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.bashirli.playex.R
import com.bashirli.playex.domain.model.AlbumUiModel
import com.bashirli.playex.domain.model.AudioUiModel
import com.bashirli.playex.presentation.ui.components.MainEmptyState
import com.bashirli.playex.presentation.ui.screens.albumDetails.components.AlbumDetailsAudioItem
import com.bashirli.playex.presentation.ui.theme.GradientBackground
import com.bashirli.playex.presentation.ui.theme.Pink29
import com.bashirli.playex.presentation.ui.theme.Pink63
import com.bashirli.playex.presentation.ui.theme.White99
import com.bashirli.playex.presentation.ui.theme.fontFamily

@Composable
fun AlbumDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: AlbumDetailsViewModel = hiltViewModel(),
    albumId: Long,
    onBack: () -> Unit,
    onNavigate: (String) -> Unit,
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect.collectAsStateWithLifecycle(initialValue = null)

    val context = LocalContext.current

    val audioFiles = remember { mutableStateOf(emptyList<AudioUiModel>()) }
    val albumUiModel = remember { mutableStateOf<AlbumUiModel?>(null) }


    LaunchedEffect(key1 = state.value, block = {
        if (!state.value.isLoading) {
            when (effect.value) {
                is AlbumDetailsEffect.ShowMessage -> {
                    val ev = effect.value as AlbumDetailsEffect.ShowMessage
                    Toast.makeText(context, ev.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    )

    viewModel.setEvent(AlbumDetailsEvent.GetAudioFiles(albumId))
    viewModel.setEvent(AlbumDetailsEvent.GetAlbum(albumId))

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Pink29)
        )
        {

            Spacer(modifier = modifier.fillMaxSize(0.04f))

            Box(modifier = modifier.fillMaxWidth()) {

                IconButton(
                    modifier = modifier.padding(start = 24.dp),
                    onClick = {
                        onBack()
                    }) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = ""
                    )

                }

                state.value.albumItem?.let { item ->
                    AsyncImage(
                        modifier = modifier
                            .fillMaxWidth(0.55f)
                            .fillMaxHeight(0.35f)
                            .padding(vertical = 21.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Pink63)
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop,
                        model = item.image,
                        contentDescription = ""
                    )
                }

            }

            audioFiles.value = state.value.audioFiles
            albumUiModel.value = state.value.albumItem
            if (!state.value.isLoading && audioFiles.value.isNotEmpty()) {

                Text(
                    text = albumUiModel.value?.album.orEmpty(),
                    modifier = modifier.padding(horizontal = 32.dp),
                    fontSize = 24.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W700,
                    color = Color.White
                )

                Text(
                    text = albumUiModel.value?.artist.orEmpty(),
                    modifier = modifier.padding(horizontal = 32.dp),
                    fontSize = 14.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W600,
                    color = White99
                )

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Row {
                        IconButton(onClick = {

                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.heart_icon),
                                contentDescription = ""
                            )
                        }

                        IconButton(onClick = {

                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.options_icon),
                                contentDescription = ""
                            )
                        }

                    }

                    IconButton(
                        modifier = modifier.background(
                            GradientBackground,
                            RoundedCornerShape(100f)
                        ),
                        onClick = {

                        }) {
                        Icon(
                            modifier = modifier.size(32.dp),
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "", tint = Color.White
                        )
                    }
                }

                Spacer(modifier = modifier.size(4.dp))

                LazyColumn {
                    items(
                        count = audioFiles.value.size,
                        key = {
                            audioFiles.value[it].id
                        }
                    ) { index ->
                        AlbumDetailsAudioItem(
                            item = audioFiles.value[index]
                        )
                    }
                }
            } else {
                MainEmptyState(message = R.string.empty_state)
            }

            if (!state.value.isLoading && audioFiles.value.isEmpty() && state.value.albumItem == null) {
                MainEmptyState(message = R.string.empty_state)
            }

        }
    }
}