package com.myniprojects.viruskiller.screens.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myniprojects.viruskiller.model.Bonus
import com.myniprojects.viruskiller.model.Bonuses
import timber.log.Timber

class ShopViewModel(money: Int, private var bonuses: Bonuses) : ViewModel()
{

    private val _money = MutableLiveData<Int>()
    val money: LiveData<Int>
        get() = _money

    var _bonusList = MutableLiveData<MutableList<Bonus>>()
    val bonusList: LiveData<MutableList<Bonus>>
        get() = _bonusList

    val Money: Int
        get() = _money.value!!


    init
    {
        Timber.i("Shop VM init $money")
        _money.value = money
        _bonusList.value = bonuses.getBonusList()
    }

    override fun onCleared()
    {
        super.onCleared()
        Timber.i("Shop VM cleared")
    }

    fun update(money: Int)
    {
        Timber.i("Update in SVM")
        _money.value = Money.minus(money)
    }



}