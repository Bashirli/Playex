package com.bashirli.playex.presentation.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.bashirli.playex.R

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = {
            onClick()
        }) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_left),
            contentDescription = "",
            tint = Color.White
        )
    }
}