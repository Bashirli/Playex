package com.bashirli.playex.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bashirli.playex.common.util.convertLongToTime
import com.bashirli.playex.domain.model.AudioUiModel
import com.bashirli.playex.presentation.ui.theme.White99
import com.bashirli.playex.presentation.ui.theme.fontFamily

@Composable
fun MainAudioItem(
    modifier: Modifier = Modifier,
    specialModifier: Modifier = Modifier,
    item: AudioUiModel,
) {

    Row(
        modifier = specialModifier
            .clickable {

            }
            .padding(horizontal = 24.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            modifier = modifier.weight(0.85f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = modifier,
                shape = RoundedCornerShape(8.dp)
            ) {
                AsyncImage(
                    modifier = modifier.size(56.dp),
                    model = item.image,
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
            }

            Spacer(modifier = modifier.size(20.dp))

            Column {
                Text(
                    text = item.title,
                    fontFamily = fontFamily,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W600
                )
                Text(
                    text = item.artist,
                    fontFamily = fontFamily,
                    fontSize = 12.sp,
                    color = White99,
                    fontWeight = FontWeight.W400
                )
            }
        }

        Text(
            modifier = modifier.weight(0.15f),
            text = convertLongToTime(item.duration),
            fontFamily = fontFamily,
            textAlign = TextAlign.End,
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.W400
        )

    }

}