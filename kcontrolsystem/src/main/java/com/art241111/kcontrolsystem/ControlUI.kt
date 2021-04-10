package com.art241111.kcontrolsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.art241111.kcontrolsystem.data.ControlVM
import com.art241111.kcontrolsystem.data.MoveInTime
import com.art241111.kcontrolsystem.data.UIMoveByCoordinate
import com.art241111.kcontrolsystem.ui.buttons.ButtonsView
import com.art241111.kcontrolsystem.ui.utils.TiltControl
import com.github.poluka.kControlLibrary.enity.position.Point

@Composable
fun ControlView(
    modifier: Modifier = Modifier,
    coordinate: State<Point>,
    moveInTime: MoveInTime,
    moveByCoordinate: UIMoveByCoordinate,
    tiltControl: TiltControl,
    controlVM: ControlVM
) {
    Surface(
        shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10),
        modifier = modifier,
        elevation = 20.dp
    ) {
        Box(Modifier.fillMaxWidth()) {
            ButtonsView(
                modifier = Modifier.align(Alignment.CenterEnd),
                coordinate = coordinate,
                moveInTime = moveInTime,
                moveByCoordinate = moveByCoordinate,
                tiltControl = tiltControl,
                controlVM = controlVM
            )
        }
    }
}
