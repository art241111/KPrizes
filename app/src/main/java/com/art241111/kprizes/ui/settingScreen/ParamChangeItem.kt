package com.art241111.kprizes.ui.settingScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.art241111.kprizes.ui.settingScreen.sendingSettings.EditText

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
internal fun ParamChangeItem(
    modifier: Modifier = Modifier,
    defaultValue: Double,
    labelText: String,
    onValueChange: (Double) -> Unit,
) {
    EditText(
        modifier = modifier.fillMaxWidth(),
        value = defaultValue,
        labelText = labelText,
        onValueChange = {
            onValueChange(it)
        }
    )
}
