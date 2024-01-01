package com.bashirli.playex.presentation.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bashirli.playex.R
import com.bashirli.playex.presentation.ui.theme.fontFamily

@Composable
fun MainEmptyState(
    modifier: Modifier = Modifier,
    @StringRes message: Int,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MainLottie(rawRes = R.raw.empty_anim, modifier = Modifier.size(300.dp))
        Spacer(modifier = modifier.size(24.dp))
        Text(
            text = stringResource(id = message),
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )

    }
}