package com.myniprojects.viruskiller.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.myniprojects.viruskiller.R
import timber.log.Timber

class GameState(private val context: Context)
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

    private var _bonus: Bonus
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
        Timber.i("INIT GameState")
        val gameStateData: GameStateData = loadGame()
        Timber.i("Load saved game instance: $gameStateData")

        _money.value = gameStateData.money
        _killedViruses.value = gameStateData.killedViruses
        _savedLives.value = gameStateData.savedLives
        _lvl.value = gameStateData.lvl
        _xp.value = gameStateData.xp
        _xpToNextLvl.value = xpArray[_lvl.value!!]
        _bonus = Bonus(
            gameStateData.bonus_savedLivesMultiplier0,
            gameStateData.bonus_savedLivesMultiplier1,
            gameStateData.bonus_savedLivesMultiplier2,
            gameStateData.bonus_savedLivesMultiplier3,
            gameStateData.bonus_criticalAttack,
            gameStateData.bonus_coinsPerMinutes,
            gameStateData.bonus_storage,
            gameStateData.bonus_rewardMultiplier,
            gameStateData.bonus_numbersAttackPerClick
        )
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
        val gs = GameStateData(this)
        Timber.i("Saving game. Instance $gs")
        val gson = Gson()
        val jsonString = gson.toJson(gs)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context) ?: return
        with(sharedPreferences.edit()) {

            putString(context.getString(R.string.game_state_key), jsonString)
            commit()
        }


    }

    private fun loadGame() : GameStateData
    {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val jsonString = sharedPreferences.getString(context.getString(R.string.game_state_key), "")
        val gson = Gson()
        return gson.fromJson(jsonString, GameStateData::class.java)


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

