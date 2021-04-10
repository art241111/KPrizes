package com.art241111.kprizes.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.art241111.kprizes.R

/**
 * Background of the entire app.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
fun Background(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 20.dp, start = 50.dp)
                .size(width = 125.dp, height = 77.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = ""
        )

        content()
    }
}
