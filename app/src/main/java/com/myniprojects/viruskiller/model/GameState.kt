package com.myniprojects.viruskiller.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    private var _virus: Virus
    val virus: Virus
        get() = _virus

    private lateinit var _bonus: Bonus
    val bonus: Bonus
        get() = _bonus

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
        _bonus = Bonus(0, 0, 0, 0, 40, 1, 120, 1F, 2)
        _virus = Virus()
        _virus.setNewVirus((0.._lvl.value!!).random().toByte())

    }


    fun attackViruses()
    {
        var dmg: Int = 0
        for (i in 1..bonus.numbersAttackPerClick.value!!)
        {
            dmg += if ((1..100).random() >= bonus.criticalAttack.value!!) // crit
            {
                Timber.i("Crit")
                2
            }
            else
            {
                Timber.i("Normal")
                1
            }
        }

        Timber.i("%s dmg total", dmg.toString())

        if (virus.attackVirus(dmg))//virus dead
        {
            Timber.i("Dead")

            _money.value = _money.value!!.plus(virus.reward.value!!)
            _xp.value = _xp.value!!.plus((virus.reward.value!!).div(10))

            if (_xp.value!! >= _xpToNextLvl.value!!) //lvl upgrade
            {
                Timber.i("New Lvl")
                _lvl.value = _lvl.value!!.plus(1)
                _xpToNextLvl.value = xpArray[_lvl.value!!]
            }
            _killedViruses.value = _killedViruses.value!!.plus(1)

            _virus.setNewVirus((0.._lvl.value!!).random().toByte())
        }
        else
        {
            Timber.i("Is alive")
        }
    }

    fun saveGame()
    {
        //todo
        val gs = GameStateData(this)
        Timber.i(gs.toString())

        val gson = Gson()
        val jsonString = gson.toJson(gs)
        Timber.i(jsonString)

        val sType = object : TypeToken<GameStateData>()
        {}.type

        val newGS = gson.fromJson<GameStateData>(jsonString, sType)
        Timber.i(newGS.toString())
    }


}

private data class GameStateData(
    val money: Int,
    val killedViruses: Int,
    val savedLives: Int,
    val lvl: Int,
    val xp: Int,
    val virus_hp: Int,
    val virus_reward: Int,
    val virus_lvl: Byte,
    val bonus_savedLivesMultiplier0: Byte,
    val bonus_savedLivesMultiplier1: Byte,
    val bonus_savedLivesMultiplier2: Byte,
    val bonus_savedLivesMultiplier3: Byte,
    val bonus_criticalAttack: Byte,
    val bonus_numbersAttackPerClick: Byte,
    val bonus_coinsPerMinutes: Int,
    val bonus_rewardMultiplier: Float,
    val bonus_storage: Int
)
{
    constructor(g: GameState) : this(
        g.money.value!!,
        g.killedViruses.value!!,
        g.savedLives.value!!,
        g.lvl.value!!,
        g.xp.value!!,
        g.virus.hp.value!!,
        g.virus.reward.value!!,
        g.virus.lvl.value!!,
        g.bonus.savedLivesMultiplier0.value!!,
        g.bonus.savedLivesMultiplier1.value!!,
        g.bonus.savedLivesMultiplier2.value!!,
        g.bonus.savedLivesMultiplier3.value!!,
        g.bonus.criticalAttack.value!!,
        g.bonus.numbersAttackPerClick.value!!,
        g.bonus.coinsPerMinutes.value!!,
        g.bonus.rewardMultiplier.value!!,
        g.bonus.storage.value!!
    )


}

