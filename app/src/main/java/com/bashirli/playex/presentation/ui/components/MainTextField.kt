package com.bashirli.playex.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bashirli.playex.presentation.ui.theme.Pink63
import com.bashirli.playex.presentation.ui.theme.White99

@Composable
fun MainTextField(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    @StringRes label: Int,
    @DrawableRes icon: Int?,
    enabled: Boolean = true,
    action: ImeAction = ImeAction.Default,
    type: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
) {

    TextField(
        modifier = modifier,
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text(text = stringResource(id = label), fontSize = 14.sp) },
        enabled = enabled,
        leadingIcon = {
            if (icon != null) {
                Icon(painter = painterResource(id = icon), contentDescription = "", tint = White99)
            }
        },
        colors = TextFieldDefaults.colors(
            focusedLabelColor = White99,
            unfocusedLabelColor = White99,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White,
            unfocusedContainerColor = Pink63,
            focusedContainerColor = Pink63,
            disabledContainerColor = Pink63,
            disabledLabelColor = White99,
            disabledTextColor = Color.White,
            focusedTextColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = action,
            keyboardType = type
        ),
        singleLine = singleLine

    )

}