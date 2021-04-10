package com.art241111.kprizes.ui.tintGame.tintScreen

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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art241111.kprizes.ui.catHelper.CatAssistant
import com.art241111.kprizes.ui.timer.BigCircleButton
import com.art241111.kprizes.ui.timer.TimerVM

/**
 * The screen on which the user controls the robot by tilting.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@ExperimentalAnimationApi
@Composable
fun TintScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxSize()) {
        val timer = viewModel<TimerVM>()
        BigCircleButton(
            modifier = Modifier.align(Alignment.Center),
            size = 640.dp,
            progress = timer.progress.value.toFloat(),
            onClick = {
                // TODO: Move to screen Z
            }
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Остановить перемещение",
                style = MaterialTheme.typography.h1,
                fontSize = 60.sp
            )
        }

        val catTextVM = viewModel<CatTextTintScreenVM>()
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
