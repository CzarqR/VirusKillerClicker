package com.myniprojects.viruskiller.screens.shop

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.myniprojects.viruskiller.R
import com.myniprojects.viruskiller.model.Bonus
import com.myniprojects.viruskiller.model.Bonuses
import com.myniprojects.viruskiller.model.BonusesData
import com.myniprojects.viruskiller.utils.Log
import timber.log.Timber

class ShopViewModel(money: Long, private var bonuses: Bonuses, private var context: Context) :
    ViewModel()
{

    private val _money = MutableLiveData<Long>()
    val money: LiveData<Long>
        get() = _money

    private var _bonusList = MutableLiveData<MutableList<Bonus>>()
    val bonusList: LiveData<MutableList<Bonus>>
        get() = _bonusList

    val mon: Long
        get() = _money.value!!


    init
    {
        Log.i("Shop VM init $money")
        _money.value = money
        _bonusList.value = bonuses.getBonusList()
    }

    override fun onCleared()
    {
        super.onCleared()
        Log.i("Shop VM cleared")
    }


    fun saveBonuses(cost: Int)
    {
        Log.i("Save bonuses")
        _money.value = mon.minus(cost)

        val gson = Gson()
        val bonusesData = BonusesData(
            _bonusList.value!![0].currLvl,
            _bonusList.value!![1].currLvl,
            _bonusList.value!![2].currLvl,
            _bonusList.value!![3].currLvl,
            _bonusList.value!![4].currLvl,
            _bonusList.value!![5].currLvl,
            _bonusList.value!![6].currLvl,
            _bonusList.value!![7].currLvl,
            _bonusList.value!![8].currLvl
        )
        val bonusesDataString = gson.toJson(bonusesData)
        Log.i("Saved instance: $bonusesData")
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context) ?: return
        with(sharedPreferences.edit()) {

            putString(context.getString(R.string.bonuses_key), bonusesDataString)
            putLong(context.getString(R.string.money_key), mon)
            commit()
        }
    }





}