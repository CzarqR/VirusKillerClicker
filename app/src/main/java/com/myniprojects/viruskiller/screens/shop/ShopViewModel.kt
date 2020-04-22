package com.myniprojects.viruskiller.screens.shop

import androidx.lifecycle.ViewModel
import com.myniprojects.viruskiller.model.Bonuses
import timber.log.Timber

class ShopViewModel(money: Int, bonuses: Bonuses) : ViewModel()
{
    init
    {
        Timber.i("Shop VM init. $money")
    }

    override fun onCleared()
    {
        super.onCleared()
        Timber.i("Shop VM cleared")
    }
}