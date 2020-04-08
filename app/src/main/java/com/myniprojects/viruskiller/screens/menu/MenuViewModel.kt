package com.myniprojects.viruskiller.screens.menu

import androidx.lifecycle.ViewModel
import timber.log.Timber

class MenuViewModel: ViewModel() {
    init {
        Timber.i("Menu VM init")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Menu VM cleared")
    }

}