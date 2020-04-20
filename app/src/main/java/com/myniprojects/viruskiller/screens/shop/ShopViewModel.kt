package com.myniprojects.viruskiller.screens.shop

import androidx.lifecycle.ViewModel
import timber.log.Timber

class ShopViewModel : ViewModel()
{
    init {
        Timber.i("Shop VM init")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Shop VM cleared")
    }
}