package com.myniprojects.viruskiller.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myniprojects.viruskiller.R
import com.myniprojects.viruskiller.utils.App
import com.myniprojects.viruskiller.utils.Log


class Virus()
{

    companion object
    {

        private val viruses = arrayOf(
            arrayOf(25, 50, R.drawable.virus_0),
            arrayOf(50, 110, R.drawable.virus_1),
            arrayOf(100, 220, R.drawable.virus_2),
            arrayOf(170, 390, R.drawable.virus_3),
            arrayOf(270, 650, R.drawable.virus_4),
            arrayOf(400, 1000, R.drawable.virus_5),
            arrayOf(460, 1200, R.drawable.virus_6),
            arrayOf(620, 1670, R.drawable.virus_7),
            arrayOf(820, 2300, R.drawable.virus_8),
            arrayOf(1040, 3010, R.drawable.virus_9),
            arrayOf(1300, 3900, R.drawable.virus_10),
            arrayOf(1600, 4960, R.drawable.virus_11),
            arrayOf(1940, 6210, R.drawable.virus_12),
            arrayOf(2320, 7650, R.drawable.virus_13)
        )

        val maxLvl = viruses.size
    }


    private val _hpString = MutableLiveData<String>()
    val hpString: LiveData<String>
        get() = _hpString

    private val _rewardString = MutableLiveData<String>()
    val rewardString: LiveData<String>
        get() = _rewardString

    private val _lvlString = MutableLiveData<String>()
    val lvlString: LiveData<String>
        get() = _lvlString

    private var _lvl: Byte = 0
        set(value)
        {
            field = value
            _lvlString.value= App.context?.getString(R.string.virus_lvl, _lvl)
        }

    val lvl: Byte
        get() = _lvl


    private var _hp: Int = 0
        set(value)
        {
            field = value
            _hpString.value= App.context?.getString(R.string.virus_hp, _hp)
        }

    val hp: Int
        get() = _hp


    private var _reward: Int = 0
        set(value)
        {
            field = value
            _rewardString.value = App.context?.getString(R.string.virus_reward, _reward)
        }

    val reward: Int
        get() = _reward




    private val _img = MutableLiveData<Int>()
    val img: LiveData<Int>
        get() = _img

    constructor(vD: VirusData) : this()
    {
        if (vD.lvl >= maxLvl)
        {
            throw Exception("Virus lvl is too big")
        }
        _lvl = vD.lvl
        _hp = vD.hp
        _reward = viruses[vD.lvl.toInt()][1]
        _img.value = viruses[vD.lvl.toInt()][2]
        Log.i("Load new virus from VirusData:  $this")
    }

    fun setNewVirus(virusLvl: Byte)
    {
        if (virusLvl >= maxLvl)
        {
            throw Exception("Virus lvl is too big")
        }
        _lvl = virusLvl
        _hp = viruses[virusLvl.toInt()][0]
        _reward = viruses[virusLvl.toInt()][1]
        _img.value = viruses[virusLvl.toInt()][2]
        Log.i("Set new virus:  $this")
    }



    fun attackVirus(dmg: Int): Boolean //true virus killed,
    {
        return when
        {
            _hp == 0//already dead
            ->
            {
                true
            }
            _hp - dmg <= 0 //ll be dead after this attack
            ->
            {
                _hp = 0
                true
            }
            else //alive
            ->
            {
                _hp = _hp.minus(dmg)
                false
            }
        }
    }

    override fun toString(): String
    {
        return "Virus ${_lvlString.value} lvl. $_hp HP. ${_rewardString.value} reward"
    }

}

data class VirusData(
    val hp: Int,
    val lvl: Byte
)
{
    constructor(v: Virus) : this(
        v.hp,
        v.lvl
    )

    constructor() : this(
        50, 0
    )
}