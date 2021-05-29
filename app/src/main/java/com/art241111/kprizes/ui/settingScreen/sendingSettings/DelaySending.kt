package com.art241111.kprizes.ui.settingScreen.sendingSettings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.art241111.kcontrolsystem.data.DELAY_SEND_SP
import com.art241111.kprizes.data.robot.RobotVM
import com.art241111.saveandloadinformation.sharedPreferences.SharedPreferencesHelperForString

/**
 * Setting the delay between sending messages.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
internal fun DelaySending(
    modifier: Modifier = Modifier,
    robot: RobotVM,
    sharedPreferences: SharedPreferencesHelperForString
) {
    EditText(
        modifier = modifier.fillMaxWidth(),
        value = robot.delaySending.value.toDouble(),
        labelText = "Задержка отправки сообщений",
        onValueChange = {
            robot.delaySending.value = it.toLong()
            sharedPreferences.save(DELAY_SEND_SP, it.toLong().toString())
        }
    )
}
