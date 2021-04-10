package com.art241111.kprizes.ui.catHelper

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.art241111.kprizes.R
import com.art241111.kprizes.ui.theme.Yellow200

/**
 * View for displaying the assistant cat.
 *
 * @param isUpdate - if you want to make an animation as if the message
 * disappears and then appears, then you need to pass state. If true,
 * the message is updated (disappears on the screen), if false,
 * the value is displayed on the screen. Important, if you change
 * the message in this way, you need to make a delay between
 * the state change so that the animation has time to flow.
 *
 * @param content - the content that will be output is the words of the assistant cat.
 * It is worth considering that the maximum width is 144.dp.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@ExperimentalAnimationApi
@Composable
fun CatAssistant(
    modifier: Modifier = Modifier,
    isUpdate: State<Boolean>,
    content: @Composable ColumnScope.() -> Unit
) {
    val imageWidth = 231.dp
    val imageHeight = 144.dp
    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = !isUpdate.value,
        ) {
            CatChat(
                modifier = Modifier
                    .widthIn(max = imageWidth)
                    .padding(bottom = imageHeight + 15.dp),
                content = content
            )
        }

        Image(
            modifier = modifier
                .size(imageWidth, imageHeight)
                .padding(0.dp),
            painter = painterResource(id = R.drawable.cathelper),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
private fun CatHelperPreview() {
    Box(modifier = Modifier.background(Yellow200)) {
        CatAssistant(
            isUpdate = mutableStateOf(true)
        ) {
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                text = "Вытяни игрушку\n" +
                    "с помощью\n" +
                    " промышленного\n" +
                    "робота ",
                textAlign = TextAlign.Center,
            )
        }
    }
}
