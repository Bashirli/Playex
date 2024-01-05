package com.bashirli.playex.presentation.ui.screens.albumDetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bashirli.playex.R
import com.bashirli.playex.domain.model.AudioUiModel
import com.bashirli.playex.presentation.ui.theme.White99
import com.bashirli.playex.presentation.ui.theme.fontFamily

@Composable
fun AlbumDetailsAudioItem(
    modifier: Modifier = Modifier,
    item: AudioUiModel,
) {

    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable {

            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = modifier.padding(start = 32.dp, end = 12.dp)
        ) {
            Text(
                text = item.title,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                color = Color.White
            )
            Text(
                text = item.artist,
                fontFamily = fontFamily,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.W400,
                fontSize = 12.sp,
                color = White99
            )
        }

        IconButton(
            modifier = modifier.padding(end = 24.dp),
            onClick = {

            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.options_icon),
                contentDescription = "",
                tint = White99
            )
        }
    }
}