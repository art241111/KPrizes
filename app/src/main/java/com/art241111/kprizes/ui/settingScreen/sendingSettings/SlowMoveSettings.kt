package com.art241111.kprizes.ui.settingScreen.sendingSettings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.art241111.kprizes.data.SHORT_MOVE_SP
import com.art241111.kprizes.data.robot.RobotVM
import com.art241111.saveandloadinformation.sharedPreferences.SharedPreferencesHelperForString

/**
 * Setting the distance per unit of time for fast movement.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
@Composable
internal fun SlowMoveSettings(
    modifier: Modifier = Modifier,
    robot: RobotVM,
    sharedPreferences: SharedPreferencesHelperForString
) {
    EditText(
        modifier = modifier.fillMaxWidth(),
        value = robot.defaultButtonDistanceShort,
        labelText = "Точечное перемещение",
        onValueChange = {
            robot.defaultButtonDistanceLong = it
            sharedPreferences.save(SHORT_MOVE_SP, it.toString())
        }
    )
}
