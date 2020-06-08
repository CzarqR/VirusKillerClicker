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

    private val _storage = MutableLiveData<String>()
    val storage: LiveData<String>
        get() = _storage

    private var currStorage: Int = 0
        set(value)
        {
            field = value
            _storage.value = "$field / ${_bonuses.storageValue}"
        }

    private val xpArray = arrayOf(10, 20, 40, 80, 160, 320, 640, 1280, 2480)


    init
    {
        Timber.i("Init  GameState")
        loadGame()
    }


    fun attackViruses()
    {
        Timber.i("Number attack per click: ${bonuses.numbersAttackPerClickValue}. Critical Attack: ${bonuses.criticalAttackValue}")
        var dmg = 0
        for (i in 1..bonuses.numbersAttackPerClickValue)
        {
            dmg += if ((1..100).random() < bonuses.criticalAttackValue) // crit
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
            _money.value = _money.value!!.plus(
                (virus.reward.value!!.times(_bonuses.rewardMultiplierValue).toInt())
            )

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
        Timber.i("Save game in GameState")
        val gameStateData = GameStateData(this)
        val virusData = VirusData(_virus)


        val gson = Gson()

        val gameStateDataString = gson.toJson(gameStateData)
        val virusDataString = gson.toJson(virusData)


        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context) ?: return
        with(sharedPreferences.edit()) {

            putString(context.getString(R.string.game_state_key), gameStateDataString)
            putString(context.getString(R.string.virus_key), virusDataString)
            putInt(context.getString(R.string.money_key), _money.value!!)
            putInt(context.getString(R.string.storage_value_key), currStorage)
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

        val mon: Int = sharedPreferences.getInt(
            context.getString(R.string.money_key),
            0
        )

        val sto: Int = sharedPreferences.getInt(
            context.getString(R.string.storage_value_key),
            0
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
                Timber.i("Bonuses loaded")
                gson.fromJson(bonusesDataString, BonusesData::class.java)
            }


        _money.value = mon
        _killedViruses.value = gameStateData.killedViruses
        _lvl.value = gameStateData.lvl
        _xp.value = gameStateData.xp
        _savedLives.value = gameStateData.savedLives
        _xpToNextLvl.value = xpArray[_lvl.value!!]

        Timber.i("Load instance: $bonusesData")
        _bonuses = Bonuses(bonusesData)
        currStorage = sto
        _virus = Virus(virusData)

    }

    fun loadMoney()
    {

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        val mon: Int = sharedPreferences.getInt(
            context.getString(R.string.money_key),
            0
        )

        _money.value = mon.plus(1000000)

    }

    fun collectStorage()
    {
        _money.value = _money.value!!.plus(currStorage)
        currStorage = 0;
    }

    fun test()
    {
        Timber.i("${_bonuses.savedLivesMultiplier0Value}")
        Timber.i("${_bonuses.savedLivesMultiplier1Value}")
        Timber.i("${_bonuses.savedLivesMultiplier2Value}")
        Timber.i("${_bonuses.savedLivesMultiplier3Value}")
        Timber.i("${_bonuses.criticalAttackValue}")
        Timber.i("${_bonuses.numbersAttackPerClickValue}")
        Timber.i("${_bonuses.coinsPerMinutesValue}")
        Timber.i("${_bonuses.rewardMultiplierValue}")
        Timber.i("${_bonuses.storageValue}")
        currStorage += 10
    }


}

private data class GameStateData(
    val killedViruses: Int,
    val savedLives: Int,
    val lvl: Int,
    val xp: Int
)
{
    constructor(g: GameState) : this(
        g.killedViruses.value!!,
        g.savedLives.value!!,
        g.lvl.value!!,
        g.xp.value!!
    )

    constructor() : this(
        0, 0, 0, 0
    )

}

