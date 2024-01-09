@file:OptIn(ExperimentalAnimationApi::class)

package com.bashirli.playex.presentation.ui.screens.player

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.bashirli.playex.R
import com.bashirli.playex.common.util.convertLongToTime
import com.bashirli.playex.domain.model.AudioUiModel
import com.bashirli.playex.presentation.ui.components.BackButton
import com.bashirli.playex.presentation.ui.components.FlipFace
import com.bashirli.playex.presentation.ui.components.MainFlipView
import com.bashirli.playex.presentation.ui.components.MainLottie
import com.bashirli.playex.presentation.ui.theme.GradientBackground
import com.bashirli.playex.presentation.ui.theme.Pink29
import com.bashirli.playex.presentation.ui.theme.Pink63
import com.bashirli.playex.presentation.ui.theme.PinkFE
import com.bashirli.playex.presentation.ui.theme.White99
import com.bashirli.playex.presentation.ui.theme.fontFamily
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = hiltViewModel(),
    audioId: Long,
    onBack: () -> Unit,
) {


    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect.collectAsStateWithLifecycle(initialValue = null)
    val context = LocalContext.current

    val flipFace = remember { mutableStateOf(FlipFace.Front) }

    val currentValue = remember { mutableStateOf(0f) }

    val isCalled = remember { mutableStateOf(false) }

    val isPlaying = remember { mutableStateOf(true) }

    val currentTime = remember { mutableStateOf(0) }

    val audioUiModel = remember {
        mutableStateOf(
            AudioUiModel(
                0, "", "", 0, "", 0, 0, "", "", ""
            )
        )
    }

    LaunchedEffect(key1 = state.value, block = {
        if (!state.value.isLoading) {
            when (effect.value) {
                is PlayerUiEffect.ShowMessage -> {
                    val ev = effect.value as PlayerUiEffect.ShowMessage
                    Toast.makeText(context, ev.message, Toast.LENGTH_LONG).show()
                }

                else -> Unit
            }
        }
    })

    if (isPlaying.value) {
        LaunchedEffect(key1 = state.value, block = {
            delay(1000)
            viewModel.setEvent(PlayerUiEvent.GetCurrentTime)
            currentTime.value = state.value.currentTime
            currentValue.value = state.value.currentTime.toFloat() / audioUiModel.value.duration
        })
    }

    viewModel.setEvent(PlayerUiEvent.GetAudio(audioId))
    state.value.audioUiModel?.let {
        audioUiModel.value = it
        if (!isCalled.value) {
            viewModel.setEvent(PlayerUiEvent.PlayAudio(it.path))
            isCalled.value = true
        }
    }


    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Pink29)
        ) {
            Spacer(modifier = modifier.fillMaxSize(0.04f))

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                BackButton(
                    modifier = modifier
                ) {
                    onBack()
                }

                Column(
                    modifier = modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.player_title),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W400,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Text(
                        text = audioUiModel.value.album,
                        textAlign = TextAlign.Center,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W600,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
                IconButton(
                    modifier = modifier,
                    onClick = {

                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.options_icon),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }

            MainFlipView(
                flipFace = flipFace.value,
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(24.dp),
                back = {
                    MainLottie(rawRes = R.raw.dance_anim, modifier = modifier.fillMaxSize())
                },
                onClick = {
                    flipFace.value = it.next
                },
                front = {
                    AsyncImage(
                        modifier = modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Pink63),
                        model = audioUiModel.value.image,
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            )

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = modifier.weight(1f)
                ) {
                    Text(
                        text = audioUiModel.value.title,
                        lineHeight = 32.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W700,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                    Text(
                        text = audioUiModel.value.artist,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp,
                        color = White99
                    )
                }
                IconButton(onClick = {

                }) {
                    Icon(
                        painterResource(id = R.drawable.heart_icon),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }

            Slider(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                value = currentValue.value,
                onValueChange = {
                    currentValue.value = it
                    viewModel.setEvent(
                        PlayerUiEvent.ChangePlayTime(
                            (audioUiModel.value.duration * it).toInt()
                        )
                    )
                },
                thumb = {
                    Card(
                        shape = RoundedCornerShape(100f)
                    ) {
                        Box(
                            modifier = modifier
                                .background(GradientBackground)
                                .size(24.dp)
                        )
                    }
                },
                colors = SliderDefaults.colors(
                    activeTrackColor = PinkFE,
                    inactiveTickColor = Color.White
                )
            )

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AnimatedContent(
                    targetState = currentTime.value,
                    transitionSpec = {
                        slideInVertically { it } togetherWith slideOutVertically { -it }
                    },
                    label = ""
                ) {
                    Text(
                        text = convertLongToTime(it.toLong()),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W400,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
                Text(
                    text = convertLongToTime(audioUiModel.value.duration),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }

            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Row(
                    modifier = modifier.fillMaxWidth(0.6f),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        modifier = modifier.size(60.dp),
                        onClick = { /*TODO*/ }) {
                        Icon(
                            modifier = modifier.size(40.dp),
                            painter = painterResource(id = R.drawable.skip_back_icon),
                            contentDescription = "", tint = Color.White
                        )
                    }
                    IconButton(
                        modifier = modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(100f))
                            .background(GradientBackground),
                        onClick = {
                            if (isPlaying.value) {
                                viewModel.setEvent(PlayerUiEvent.PauseAudio)
                            } else {
                                viewModel.setEvent(PlayerUiEvent.PlayAudio(audioUiModel.value.path))
                            }
                            isPlaying.value = !isPlaying.value
                        }) {
                        Icon(
                            modifier = modifier.size(36.dp),
                            painter = if (!isPlaying.value) painterResource(id = R.drawable.play_icon) else painterResource(
                                id = R.drawable.pause_icon
                            ),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                    IconButton(modifier = modifier.size(60.dp),
                        onClick = { /*TODO*/ }) {
                        Icon(
                            modifier = modifier.size(40.dp),
                            painter = painterResource(id = R.drawable.skip_next_icon),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}