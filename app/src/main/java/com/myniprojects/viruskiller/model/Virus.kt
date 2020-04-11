package com.myniprojects.viruskiller.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber


class Virus(virusLvl: Int)
{
    companion object
    {
        private val virus1 = arrayOf(15, 50)
        private val virus2 = arrayOf(25, 100)

        val viruses = arrayOf(virus1, virus2)
        val maxLvl = viruses.size
    }

    private val _hp = MutableLiveData<Int>()
    val hp: LiveData<Int>
        get() = _hp

    private val _reward = MutableLiveData<Int>()
    val reward: LiveData<Int>
        get() = _reward

    private val _lvl = MutableLiveData<Int>()
    val lvl: LiveData<Int>
        get() = _lvl


    fun attackVirus()
    {
        _hp.value = _hp.value!! - 1
    }

    override fun toString(): String
    {
        return "Virus ${_lvl.value} lvl. ${_hp.value} HP. ${_reward.value} reward"
    }

    init
    {
        if (virusLvl >= maxLvl)
        {
            throw Exception("Virus lvl is too big")
        }
        _hp.value = GameState.viruses[virusLvl][0]
        _reward.value = viruses[virusLvl][1]
        _lvl.value = virusLvl
        Timber.i("Init $this")
    }


}