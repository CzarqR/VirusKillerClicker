package com.myniprojects.viruskiller.screens.shop

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.myniprojects.viruskiller.R
import com.myniprojects.viruskiller.model.Bonus
import com.myniprojects.viruskiller.model.Bonuses
import com.myniprojects.viruskiller.model.BonusesData
import timber.log.Timber

class ShopViewModel(money: Int, private var bonuses: Bonuses, private var context: Context) :
    ViewModel()
{

    private val _money = MutableLiveData<Int>()
    val money: LiveData<Int>
        get() = _money

    private var _bonusList = MutableLiveData<MutableList<Bonus>>()
    val bonusList: LiveData<MutableList<Bonus>>
        get() = _bonusList

    val mon: Int
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
        _money.value = mon.minus(money)
        Timber.i("${_bonusList.value!![0].currLvl} lvl item index 0")
    }

    fun saveBonuses()
    {
        Timber.i("Save bonuses")
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

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context) ?: return
        with(sharedPreferences.edit()) {

            putString(context.getString(R.string.bonuses_key), bonusesDataString)
            commit()
        }

    }




}