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

    private lateinit var _virus: Virus
    val virus: Virus
        get() = _virus

    private lateinit var _bonuses: Bonuses
    val bonuses: Bonuses
        get() = _bonuses

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
        Timber.i("Init  GameState")
        loadGame()

    }


    fun attackViruses()
    {
        var dmg = 0
        for (i in 1..bonuses.numbersAttackPerClick.value!!)
        {
            dmg += if ((1..100).random() >= bonuses.criticalAttack.value!!) // crit
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
        val gameStateData = GameStateData(this)
        val virusData = VirusData(_virus)
        val bonusesData = BonusesData(_bonuses)
        Timber.i("Saving game. \nGameState $gameStateData\nBonuses$bonusesData\nVirus$virusData")
        val gson = Gson()

        val gameStateDataString = gson.toJson(gameStateData)
        val virusDataString = gson.toJson(virusData)
        val bonusesDataString = gson.toJson(bonusesData)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context) ?: return
        with(sharedPreferences.edit()) {

            putString(context.getString(R.string.game_state_key), gameStateDataString)
            putString(context.getString(R.string.virus_key), virusDataString)
            putString(context.getString(R.string.bonuses_key), bonusesDataString)
            commit()
        }


    }


    private fun loadGame()
    {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        val gameStateDataString = sharedPreferences.getString(
            context.getString(R.string.game_state_key),
            context.getString(R.string.NotLoaded)
        )
        val virusDataString = sharedPreferences.getString(
            context.getString(R.string.virus_key),
            context.getString(R.string.NotLoaded)
        )
        val bonusesDataString = sharedPreferences.getString(
            context.getString(R.string.bonuses_key),
            context.getString(R.string.NotLoaded)
        )

        val gson = Gson()

        val gameStateData: GameStateData

        gameStateData =
            if (gameStateDataString!! == context.getString(R.string.NotLoaded)) //first run, no saved game
            {
                GameStateData()
            }
            else
            {
                gson.fromJson(gameStateDataString, GameStateData::class.java)
            }


        val virusData: VirusData

        virusData =
            if (virusDataString!! == context.getString(R.string.NotLoaded)) //first run, no saved game
            {
                VirusData()
            }
            else
            {
                gson.fromJson(virusDataString, VirusData::class.java)
            }


        val bonusesData: BonusesData

        bonusesData =
            if (bonusesDataString!! == context.getString(R.string.NotLoaded)) //first run, no saved game
            {
                BonusesData()
            }
            else
            {
                gson.fromJson(bonusesDataString, BonusesData::class.java)
            }

        _money.value = gameStateData.money
        _killedViruses.value = gameStateData.killedViruses
        _lvl.value = gameStateData.lvl
        _xp.value = gameStateData.xp
        _savedLives.value = gameStateData.savedLives
        _xpToNextLvl.value = xpArray[_lvl.value!!]

        _bonuses = Bonuses(bonusesData)
        _virus = Virus(virusData)

    }


}

private data class GameStateData(
    val money: Int,
    val killedViruses: Int,
    val savedLives: Int,
    val lvl: Int,
    val xp: Int
)
{
    constructor(g: GameState) : this(
        g.money.value!!,
        g.killedViruses.value!!,
        g.savedLives.value!!,
        g.lvl.value!!,
        g.xp.value!!
    )

    constructor() : this(
        0, 0, 0, 0, 0
    )

}

