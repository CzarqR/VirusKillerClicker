package com.myniprojects.viruskiller.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myniprojects.viruskiller.R
import timber.log.Timber


class Virus()
{

    companion object
    {

        private val viruses = arrayOf(
            arrayOf(3, 50, R.drawable.virus_0),
            arrayOf(5, 100, R.drawable.virus_1),
            arrayOf(8, 200, R.drawable.virus_2),
            arrayOf(10, 400, R.drawable.virus_3),
            arrayOf(12, 100, R.drawable.virus_4),
            arrayOf(25, 100, R.drawable.virus_5),
            arrayOf(25, 100, R.drawable.virus_6),
            arrayOf(25, 100, R.drawable.virus_7),
            arrayOf(25, 100, R.drawable.virus_8),
            arrayOf(25, 100, R.drawable.virus_9),
            arrayOf(25, 100, R.drawable.virus_10),
            arrayOf(25, 100, R.drawable.virus_11),
            arrayOf(25, 100, R.drawable.virus_12),
            arrayOf(25, 100, R.drawable.virus_13)
        )

        val maxLvl = viruses.size
    }


    private val _hp = MutableLiveData<Int>()
    val hp: LiveData<Int>
        get() = _hp

    private val _reward = MutableLiveData<Int>()
    val reward: LiveData<Int>
        get() = _reward

    private val _lvl = MutableLiveData<Byte>()
    val lvl: LiveData<Byte>
        get() = _lvl

    private val _img = MutableLiveData<Int>()
    val img: LiveData<Int>
        get() = _img


    fun setNewVirus(virusLvl: Byte)
    {
        if (virusLvl >= maxLvl)
        {
            throw Exception("Virus lvl is too big")
        }
        _lvl.value = virusLvl
        _hp.value = viruses[virusLvl.toInt()][0]
        _reward.value = viruses[virusLvl.toInt()][1]
        _img.value = viruses[virusLvl.toInt()][2]
        Timber.i("Set new virus:  $this")
    }


    fun attackVirus(dmg: Int): Boolean //true virus killed,
    {
        return when
        {
            _hp.value == 0//already dead
            ->
            {
                true
            }
            hp.value!! - dmg <= 0 //ll be dead after this attack
            ->
            {
                _hp.value = 0
                true
            }
            else //alive
            ->
            {
                _hp.value = _hp.value!!.minus(dmg)
                false
            }
        }
    }

    override fun toString(): String
    {
        return "Virus ${_lvl.value} lvl. ${_hp.value} HP. ${_reward.value} reward"
    }

}