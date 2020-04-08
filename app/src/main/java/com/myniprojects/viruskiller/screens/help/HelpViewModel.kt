package com.myniprojects.viruskiller.screens.help

import androidx.lifecycle.ViewModel
import timber.log.Timber

class HelpViewModel : ViewModel() {

    init {
        Timber.i("Help VM init")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Help VM cleared")
    }
}