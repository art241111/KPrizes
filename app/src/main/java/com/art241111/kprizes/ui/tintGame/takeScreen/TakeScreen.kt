package com.art241111.kprizes.ui.tintGame.takeScreen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art241111.kprizes.ui.catHelper.CatAssistant
import com.art241111.kprizes.ui.timer.BigCircleButton

/**
 * The screen where the user should grab the toy.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@ExperimentalAnimationApi
@Composable
fun TakeScreen(
    modifier: Modifier = Modifier,
    timer: Float,
    onTake: () -> Unit
) {
    Box(modifier.fillMaxSize()) {
        BigCircleButton(
            modifier = Modifier.align(Alignment.Center),
            size = 640.dp,
            progress = timer,
            onClick = {
                onTake()
            }
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Захватить",
                style = MaterialTheme.typography.h1,
            )
        }

        val catTextVM = viewModel<CatTextTakeScreenVM>()
        val isUpdate = catTextVM.isUpdate
        CatAssistant(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            isUpdate = isUpdate
        ) {
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                text = catTextVM.text.value,
                textAlign = TextAlign.Center,
            )
        }
    }
}
