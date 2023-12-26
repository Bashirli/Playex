package com.bashirli.playex.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bashirli.playex.R
import com.bashirli.playex.common.util.ellipsis25String
import com.bashirli.playex.domain.model.AlbumUiModel
import com.bashirli.playex.presentation.ui.theme.Pink63
import com.bashirli.playex.presentation.ui.theme.White99
import com.bashirli.playex.presentation.ui.theme.fontFamily

@Composable
fun MainAlbumItem(
    modifier: Modifier = Modifier,
    item: AlbumUiModel,
    isLast: Boolean,
    onClickShowAll: () -> Unit,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 24.dp)
                .clickable {

                },
        ) {
            Card(

                colors = CardDefaults.cardColors(
                    containerColor = Pink63
                ),
                shape = RoundedCornerShape(16.dp),

                ) {

                AsyncImage(
                    modifier = modifier.size(200.dp), model = item.image, contentDescription = ""
                )
            }

            Spacer(modifier = modifier.size(12.dp))

            Text(
                text = if (item.album.length > 25) item.album.ellipsis25String() else item.album,
                fontSize = 16.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                color = Color.White
            )
            Text(
                text = if (item.artist.length > 25) item.artist.ellipsis25String() else item.artist + " - ${
                    stringResource(
                        id = R.string.album
                    )
                }",
                fontSize = 12.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                color = White99
            )


        }
        if (isLast) {
            Column(
                modifier = modifier
                    .padding(end = 24.dp)
                    .clickable {
                        onClickShowAll()
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = {
                    onClickShowAll()
                }) {
                    Icon(
                        modifier = modifier.size(200.dp),
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "",
                        tint = Pink63
                    )
                }
                Spacer(modifier = modifier.size(12.dp))
                Text(
                    text = stringResource(id = R.string.show_all_playlists),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }

    }

}
