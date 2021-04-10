package com.art241111.kprizes.ui.settingScreen.addPoints

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.art241111.kprizes.data.robot.RobotVM
import com.github.poluka.kControlLibrary.enity.position.Point

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
internal fun AddHomePoint(
    modifier: Modifier = Modifier,
    robot: RobotVM,
    moveToAddPoint: (EditPoints) -> Unit
) {
    Row(modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = "Домшнее положение ",
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = robot.homePoint.toString(),
        )

        Button(
            onClick = {
                moveToAddPoint(EditPoints.HOME_POINT)
            }
        ) {
            Text(
                text = "Изменить точку",
            )
        }
    }
}
