package com.bashirli.playex.presentation.ui.screens.home

import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bashirli.playex.R
import com.bashirli.playex.domain.model.AlbumUiModel
import com.bashirli.playex.domain.model.AudioUiModel
import com.bashirli.playex.presentation.ui.components.MainAlbumItem
import com.bashirli.playex.presentation.ui.components.MainAudioItem
import com.bashirli.playex.presentation.ui.components.MainEmptyState
import com.bashirli.playex.presentation.ui.components.MainTextField
import com.bashirli.playex.presentation.ui.screens.home.components.HomeAlbums
import com.bashirli.playex.presentation.ui.theme.GradientIndicator
import com.bashirli.playex.presentation.ui.theme.Pink29
import com.bashirli.playex.presentation.ui.theme.White99
import com.bashirli.playex.presentation.ui.theme.fontFamily

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val tabIndex = remember { mutableStateOf(0) }

    val tabs = remember { mutableStateOf(arrayListOf<String>()) }

    val isSearchVisible = remember { mutableStateOf(true) }

    val searchState = remember {
        mutableStateOf("")
    }

    val audioFiles = remember { mutableStateOf(emptyList<AudioUiModel>()) }
    val albums = remember { mutableStateOf(emptyList<AlbumUiModel>()) }

    val isCalled = remember { mutableStateOf(true) }
    val isTabChanged = remember { mutableStateOf(false) }

    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect.collectAsStateWithLifecycle(initialValue = null)
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                isCalled.value = true
                viewModel.setEvent(HomeUiEvent.GetInitialData)
            } else {
                Toast.makeText(context, R.string.permission_denied, Toast.LENGTH_LONG).show()
            }
        }

    LaunchedEffect(key1 = null) {
        if (ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.READ_MEDIA_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            isCalled.value = true
            viewModel.setEvent(HomeUiEvent.GetInitialData)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_AUDIO)
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }


    LaunchedEffect(key1 = state.value) {
        if (!state.value.isLoading) {
            when (effect.value) {
                is HomeUiEffect.ShowMessage -> {
                    val ev = effect.value as HomeUiEffect.ShowMessage
                    Log.e("testdata", ev.message)
                    Toast.makeText(context, ev.message, Toast.LENGTH_LONG).show()
                }

                else -> Unit
            }
        }

    }

    val tabNames =
        listOf(stringResource(id = R.string.featured), stringResource(id = R.string.all_music))
    if (!tabs.value.containsAll(tabNames)) {
        tabs.value.addAll(tabNames)
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.welcome_back),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 24.sp
                    )
                    Text(
                        text = stringResource(id = R.string.home_title),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = White99,
                        fontSize = 14.sp
                    )
                }
                Row {
                    IconButton(
                        onClick = { },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.library_icon),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.settings_icon),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = modifier.size(16.dp))

            AnimatedVisibility(visible = isSearchVisible.value) {
                MainTextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 16.dp)
                        .clickable {

                        },
                    text = searchState,
                    label = R.string.search_title,
                    icon = R.drawable.search_icon,
                    enabled = false
                )
            }

            if (state.value.albumNames.isNotEmpty()) {
                val nameList = state.value.albumNames
                if (!tabs.value.containsAll(nameList)) {
                    tabs.value.addAll(nameList)
                    tabs.value.add(stringResource(id = R.string.all_playlists))
                }
            }
                ScrollableTabRow(
                    selectedTabIndex = tabIndex.value,
                    containerColor = Color.Transparent,
                    indicator = {
                        TabRowDefaults.Indicator(
                            modifier = modifier
                                .tabIndicatorOffset(it[tabIndex.value])
                                .background(GradientIndicator), color = Color.Transparent
                        )
                    },
                    divider = {

                    }) {
                    tabs.value.forEachIndexed { index, s ->
                        Tab(
                            selected = index == tabIndex.value,
                            onClick = {
                                tabIndex.value = index
                                isTabChanged.value = true
                            },
                            text = {
                                Text(
                                    text = s,
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.W600,
                                    fontSize = 14.sp,
                                )
                            },
                            selectedContentColor = Color.White,
                            unselectedContentColor = White99
                        )
                    }
                }
                when (tabIndex.value) {
                    0 -> {
                        if (!isSearchVisible.value) {
                            isSearchVisible.value = true
                        }
                        if (!isCalled.value) {
                            viewModel.setEvent(HomeUiEvent.GetInitialData)
                            isCalled.value = true
                        }
                        audioFiles.value = state.value.audioFiles
                        albums.value = state.value.limitedAlbums

                        if ((audioFiles.value.isNotEmpty() || albums.value.isNotEmpty()) && !state.value.isLoading) {
                            LazyColumn {
                                item {
                                    Spacer(modifier = modifier.size(24.dp))
                                    LazyRow {
                                        items(
                                            count = albums.value.size,
                                            key = {
                                                albums.value[it].id
                                            }
                                        ) {
                                            MainAlbumItem(item = albums.value[it],
                                                isLast = state.value.limitedAlbums.size - 1 == it,
                                                onClickShowAll = {
                                                    tabIndex.value = tabs.value.lastIndex
                                                    isTabChanged.value = true
                                                }
                                            )
                                        }
                                    }

                                    Text(
                                        modifier = modifier
                                            .padding(horizontal = 24.dp)
                                            .padding(top = 24.dp, bottom = 16.dp),
                                        text = stringResource(id = R.string.music_title),
                                        fontFamily = fontFamily,
                                        fontSize = 20.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.W600
                                    )

                                }
                                items(
                                    count = audioFiles.value.size,
                                    key = {
                                        audioFiles.value[it].id
                                    },
                                ) {
                                    MainAudioItem(
                                        item = audioFiles.value[it],
                                        specialModifier = modifier.fillParentMaxWidth()
                                    )
                                }

                            }
                        } else if (!state.value.isLoading) {
                            MainEmptyState(message = R.string.empty_state)
                        }
                    }
                    1 -> {
                        if (isCalled.value) {
                            isCalled.value = !isCalled.value
                        }

                        if (isTabChanged.value) {
                            isTabChanged.value = false
                            viewModel.setEvent(HomeUiEvent.GetAudios())
                        }

                        audioFiles.value = state.value.audioFiles
                        if (isSearchVisible.value) isSearchVisible.value = false

                        if (audioFiles.value.isNotEmpty()) {
                            LazyColumn {
                                items(
                                    count = audioFiles.value.size,
                                    key = {
                                        audioFiles.value[it].id
                                    }
                                ) {
                                    MainAudioItem(
                                        item = audioFiles.value[it],
                                        specialModifier = modifier.fillParentMaxWidth()
                                    )
                                }
                            }
                        } else {
                            MainEmptyState(message = R.string.empty_state)
                        }
                    }

                    tabs.value.lastIndex -> {
                        if (isCalled.value) {
                            isCalled.value = !isCalled.value
                        }
                        if (isTabChanged.value) {
                            isTabChanged.value = false
                            viewModel.setEvent(HomeUiEvent.GetAlbums)
                        }

                        albums.value = state.value.albums

                        if (albums.value.isNotEmpty() && !state.value.isLoading) {
                            LazyColumn {
                                items(
                                    count = albums.value.size,
                                    key = {
                                        albums.value[it].id
                                    }
                                ) {
                                    HomeAlbums(
                                        sModifier = modifier.fillParentMaxWidth(),
                                        item = albums.value[it]
                                    )
                                }
                            }
                        } else if (!state.value.isLoading) {
                            MainEmptyState(message = R.string.empty_state)
                        }

                    }

                    else -> {
                        if (isCalled.value) isCalled.value = !isCalled.value
                        if (isSearchVisible.value) isSearchVisible.value = false
                        viewModel.setEvent(
                            HomeUiEvent.GetAudios(
                                albumId = albums.value[tabIndex.value - 2].albumId
                            )
                        )

                        audioFiles.value = state.value.audioFiles
                        if (audioFiles.value.isNotEmpty()) {
                            LazyColumn(
                                modifier = modifier.fillMaxWidth()
                            ) {
                                items(
                                    count = audioFiles.value.size,
                                    key = {
                                        audioFiles.value[it].id
                                    },
                                ) {
                                    MainAudioItem(
                                        item = audioFiles.value[it],
                                        specialModifier = modifier.fillParentMaxWidth()
                                    )
                                }
                            }
                        } else {
                            MainEmptyState(message = R.string.empty_state)
                        }
                    }
                }
        }
    }
}


