package com.art241111.kprizes.ui.settingScreen.addPoints

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.art241111.kcontrolsystem.ControlView
import com.art241111.kcontrolsystem.data.ControlVM
import com.art241111.kcontrolsystem.data.MoveInTime
import com.art241111.kcontrolsystem.data.UIMoveByCoordinateKRobot
import com.art241111.kprizes.data.robot.RobotVM
import com.github.poluka.kControlLibrary.enity.position.Point

/**
 * Wrapper for getting the coordinates of points.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
fun GetPoint(
    robotVM: RobotVM,
    controlVM: ControlVM,
    addPoint: (Point) -> Unit,
    moveInTime: MoveInTime
) {
    Column {
        val coordinate = robotVM.coordinate.collectAsState()
        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { addPoint(coordinate.value) }
        ) {
            Text(
                text = "Добавить точку",
                style = MaterialTheme.typography.h2
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        ControlView(
            coordinate = coordinate,
            moveInTime = moveInTime,
            moveByCoordinate = UIMoveByCoordinateKRobot(coordinate) {},
            controlVM = controlVM
        )
    }
}
