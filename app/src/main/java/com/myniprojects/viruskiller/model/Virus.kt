package com.myniprojects.viruskiller.model

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myniprojects.viruskiller.R
import timber.log.Timber


class Virus(virusLvl: Int)
{

    companion object
    {
        private val virus0 = arrayOf(15, 50, R.drawable.virus_0)
        private val virus1 = arrayOf(25, 100, R.drawable.virus_1)
        private val virus2 = arrayOf(45, 200, R.drawable.virus_2)
        private val virus3 = arrayOf(85, 400, R.drawable.virus_3)
        private val virus4 = arrayOf(25, 100, R.drawable.virus_4)
        private val virus5 = arrayOf(25, 100, R.drawable.virus_5)
        private val virus6 = arrayOf(25, 100, R.drawable.virus_6)
        private val virus7 = arrayOf(25, 100, R.drawable.virus_7)
        private val virus8 = arrayOf(25, 100, R.drawable.virus_8)
        private val virus9 = arrayOf(25, 100, R.drawable.virus_9)
        private val virus10 = arrayOf(25, 100, R.drawable.virus_10)
        private val virus11 = arrayOf(25, 100, R.drawable.virus_11)
        private val virus12 = arrayOf(25, 100, R.drawable.virus_12)
        private val virus13 = arrayOf(25, 100, R.drawable.virus_13)

        private val viruses = arrayOf(
            virus0,
            virus1,
            virus2,
            virus3,
            virus4,
            virus4,
            virus5,
            virus6,
            virus7,
            virus8,
            virus9,
            virus10,
            virus11,
            virus12,
            virus13
        )

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

    private val _img = MutableLiveData<Int>()
    val img: LiveData<Int>
        get() = _img

    init
    {
        if (virusLvl >= maxLvl)
        {
            throw Exception("Virus lvl is too big")
        }
        _hp.value = viruses[virusLvl][0]
        _reward.value = viruses[virusLvl][1]
        _lvl.value = virusLvl
        _img.value = viruses[virusLvl][2]
        Timber.i("Init $this")
    }

    fun attackVirus(): Boolean //true virus killed,
    {
        if (_hp.value == 0)//already dead
        {
            return true
        }
        _hp.value = _hp.value!! - 1
        if (_hp.value == 0) //killed
        {
            return true
        }
        return false //alive
    }

    override fun toString(): String
    {
        return "Virus ${_lvl.value} lvl. ${_hp.value} HP. ${_reward.value} reward"
    }

}