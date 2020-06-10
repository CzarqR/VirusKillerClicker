package com.myniprojects.viruskiller.screens.help

import androidx.lifecycle.ViewModel
import com.myniprojects.viruskiller.utils.Log
import timber.log.Timber

class HelpViewModel : ViewModel() {

    init {
        Log.i("Help VM init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("Help VM cleared")
    }
}