package com.bashirli.playex.presentation.ui.components

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun MainLottie(
    modifier: Modifier = Modifier,
    @RawRes rawRes: Int,
) {

    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(rawRes))

    LottieAnimation(
        composition = composition.value,
        modifier = modifier,
        iterations = LottieConstants.IterateForever
    )

}