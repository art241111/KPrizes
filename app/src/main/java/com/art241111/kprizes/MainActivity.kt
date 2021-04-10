package com.art241111.kprizes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art241111.kprizes.navigation.MainNavigateScreen
import com.art241111.kprizes.navigation.MainNavigationVM
import com.art241111.kprizes.ui.theme.KPrizesTheme

class MainActivity : ComponentActivity() {
    private lateinit var navigationVM: MainNavigationVM

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navigationVM = viewModel()
            navigationVM.moveToHome()

            KPrizesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainNavigateScreen(
                        navigate = navigationVM
                    )
                }
            }
        }
    }

    override fun onBackPressed() {
        if (navigationVM.onBackButtonClick()) {
            super.onBackPressed()
        }
    }
}
