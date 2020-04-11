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

    //endregion

    init
    {
        //TODO load saved game state
        _money.value = 0
        _killedViruses.value = 0
        _savedLives.value = 0
        _virus.value = Virus(1)
        Timber.i(_virus.value.toString())

    }


    companion object
    {
        private val virus1 = arrayOf(15, 50)
        private val virus2 = arrayOf(25, 100)

        val viruses = arrayOf(virus1, virus2)
        val maxLvl = viruses.size
    }



    fun killedViruses()
    {
        Timber.i("Clicked")
        _virus.value!!.attackVirus()
        Timber.i(_virus.value.toString())

    }


}


