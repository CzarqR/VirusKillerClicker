package com.myniprojects.viruskiller.model

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

    private val _virus = MutableLiveData<Virus>()
    val virus: LiveData<Virus>
        get() = _virus

    private val _lvl = MutableLiveData<Int>()
    val lvl: LiveData<Int>
        get() = _lvl

    private val _xp = MutableLiveData<Int>()
    val xp: LiveData<Int>
        get() = _xp

    private val _xpToNextLvl = MutableLiveData<Int>()
    val xpToNextLvl: LiveData<Int>
        get() = _xpToNextLvl

    private val xpArray = arrayOf(10, 20, 40, 80, 160, 320, 640, 1280, 2480)


    init
    {
        //TODO load saved game state
        _money.value = 0
        _killedViruses.value = 0
        _savedLives.value = 0
        _lvl.value = 0
        _xp.value = 0
        _xpToNextLvl.value = xpArray[_lvl.value!!]
        loadRandomVirus()

    }


    fun attackViruses()
    {
        if (_virus.value!!.attackVirus())//virus dead
        {
            Timber.i("Dead")

            _money.value = _money.value!!.plus(_virus.value!!.reward.value!!)
            _xp.value = _xp.value!!.plus((_virus.value!!.reward.value!!).div(10))

            if (_xp.value!! >= _xpToNextLvl.value!!) //lvl upgrade
            {
                Timber.i("New Lvl")
                _lvl.value = _lvl.value!!.plus(1)
                _xpToNextLvl.value = xpArray[_lvl.value!!]
            }
            _killedViruses.value = _killedViruses.value!!.plus(1)

            loadRandomVirus()
        }
        else
        {
            Timber.i("Is alive")
        }
    }

    fun loadRandomVirus()
    {
        _virus.value = Virus((0.._lvl.value!!).random())
    }


}


