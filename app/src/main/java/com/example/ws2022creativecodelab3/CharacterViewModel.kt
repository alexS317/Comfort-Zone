package com.example.ws2022creativecodelab3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CharacterViewModel : ViewModel() {
    private var clickedCharacter = MutableLiveData<String>()

    val wasClicked: LiveData<String> get() = clickedCharacter

    fun characterClicked(id: String) {
        clickedCharacter.value = id
    }
}