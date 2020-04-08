package com.myniprojects.viruskiller

import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber

class GameState
{
    private val _money = MutableLiveData<Int>()
    val money: LiveData<Int>
        get() = _money

    private val _killedViruses = MutableLiveData<Int>()
    val killedViruses: LiveData<Int>
        get() = _killedViruses

    private val _savedLives = MutableLiveData<Int>()
    val savedLives: LiveData<Int>
        get() = _savedLives

    init
    {
        //TODO load saved game state
        _money.value = 0
        _killedViruses.value = 0
        _savedLives.value = 0
    }

    fun killedViruses()
    {

    }


}


