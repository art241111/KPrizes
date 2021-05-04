package com.art241111.kprizes.ui.settingScreen.addPoints

import android.graphics.Point
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.art241111.kprizes.data.robot.RobotVM

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
internal fun AddSetPoint(
    modifier: Modifier = Modifier,
    robot: RobotVM,
    moveToAddPoint: (EditPoints) -> Unit,
    text: String = "Положение контейнера ",
    coordinatePoint: String = robot.setPoint.toString(),
    pointFlag: EditPoints = EditPoints.SET_POINT
) {
    Row(modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = text,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = coordinatePoint,
        )

        Button(
            onClick = {
                moveToAddPoint(pointFlag)
            }
        ) {
            Text(
                text = "Изменить точку",
            )
        }
    }
}
