package dev.jay.passgenius.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationScreenViewModel @Inject constructor() :
    ViewModel() {
    private val _clickedButtons = mutableStateListOf<Int>()
    private val _pinEntered = mutableStateOf(false)

    val clickedButtons: SnapshotStateList<Int> = _clickedButtons
    val pinEntered: MutableState<Boolean> = _pinEntered

    private var clickCount = 0

    init {
        initializeClickList()
    }

    private fun initializeClickList() {
        repeat(4) {
            _clickedButtons.add(-1)
        }
    }

    fun addClickedButton(number: Int) {
        if (clickCount < 4) {
            _clickedButtons[clickCount] = number
            clickCount++
            if (clickedButtons.size == 4 && clickedButtons.none { it == -1 }) {
                _pinEntered.value = true
            }
        }
    }

    fun clearList() {
        _clickedButtons.clear()
        initializeClickList()
        clickCount = 0
        _pinEntered.value = false
    }

    fun deleteLastElement() {
        if (clickCount > 0) {
            _clickedButtons[clickCount - 1] = -1
            clickCount--
            _pinEntered.value = false
        }
    }

    fun getUserPin(): String {
        return clickedButtons.joinToString()
    }
}