package com.art241111.kprizes.navigation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.Stack

/**
 * Interface from which all states are inherited.
 */
interface AppState

/**
 * Shared states between two games.
 */
enum class GeneralScreen : AppState {
    HOME, TIME_UP_SCREEN
}

/**
 * The main class that is responsible for navigation in the app.
 * Stores a stack of screens in itself, allows you to navigate through the shared screen.
 * Responsible for the operation of the back button.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
class MainNavigationVM : ViewModel() {
    private val stack = Stack<AppState>()

    private val _state = mutableStateOf<AppState>(GeneralScreen.HOME)
    val state: State<AppState> = _state

    fun setScreen(screen: AppState) {
        stack.add(screen)
        _state.value = screen
    }

    fun moveToHome() {

        stack.clear()
        stack.add(GeneralScreen.HOME)
        _state.value = GeneralScreen.HOME
    }

    private fun back(): Boolean {
        return if (stack.empty()) {
            true
        } else {
            setScreen(stack.pop())
            false
        }
    }

    fun onBackButtonClick() = back()
}
