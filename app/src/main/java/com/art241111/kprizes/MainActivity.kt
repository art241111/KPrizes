package com.art241111.kprizes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.art241111.kprizes.ui.Background
import com.art241111.kprizes.ui.startScreen.StartScreen
import com.art241111.kprizes.ui.theme.KPrizesTheme

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KPrizesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Background(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        StartScreen()
                    }
                }
            }
        }
    }
}
