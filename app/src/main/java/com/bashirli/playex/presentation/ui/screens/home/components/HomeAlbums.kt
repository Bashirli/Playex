package com.bashirli.playex.presentation.ui.screens.home.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bashirli.playex.R
import com.bashirli.playex.domain.model.AlbumUiModel
import com.bashirli.playex.presentation.ui.theme.White99
import com.bashirli.playex.presentation.ui.theme.fontFamily


@Composable
fun HomeAlbums(
    sModifier: Modifier = Modifier,
    modifier: Modifier = Modifier,
    item: AlbumUiModel,
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val isPressed = interactionSource.collectIsPressedAsState()

    Card(
        modifier = sModifier
            .padding(
                horizontal = animateDpAsState(if (isPressed.value) 6.dp else 0.dp).value,
                vertical = animateDpAsState(if (isPressed.value) 6.dp else 0.dp).value
            )
            .clickable(
                indication = rememberRipple(bounded = true),
                interactionSource = interactionSource
            ) {

            },
        shape = RoundedCornerShape(
            animateDpAsState(
                if (isPressed.value) 16.dp else 0.dp,
                label = ""
            ).value
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = modifier
                .padding(
                    horizontal = 24.dp,
                    vertical = 12.dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                model = item.image, contentDescription = ""
            )

            Spacer(modifier = modifier.size(16.dp))

            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = item.album,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Text(
                    text = stringResource(id = R.string.by) + " ${item.artist}",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp,
                    color = White99
                )

            }

        }
    }


}