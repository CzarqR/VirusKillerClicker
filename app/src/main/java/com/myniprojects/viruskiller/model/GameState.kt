package com.myniprojects.viruskiller.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.myniprojects.viruskiller.R
import com.myniprojects.viruskiller.utils.App
import com.myniprojects.viruskiller.utils.Log
import java.util.*
import java.util.Timer
import kotlin.concurrent.schedule

class GameState(private val context: Context)
{

    companion object
    {
        val xpArray =
            arrayOf(
                50,
                200,
                500,
                1150,
                2650,
                4000,
                10_000,
                20_000,
                42_000,
                85_000,
                174_000,
                356_000,
                720_000
            )
        val maxLvl = xpArray.size
    }

    //region properties

    private val _money = MutableLiveData<Long>()
    val money: LiveData<Long>
        get() = _money

    private val _killedViruses = MutableLiveData<Int>()
    val killedViruses: LiveData<Int>
        get() = _killedViruses

    private val _savedLives = MutableLiveData<Long>()
    val savedLives: LiveData<Long>
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

    private val _xpString = MutableLiveData<String>()
    val xpString: LiveData<String>
        get() = _xpString


    private var _xp: Int = 0
        set(value)
        {
            field = value
            Log.i(_xpToNextLvl)
            val nextLvl: String? = if (_lvl.value == maxLvl)
            {
                App.context?.getString(R.string.infinite_sign)
            }
            else
            {
                _xpToNextLvl.toString()
            }
            _xpString.postValue(App.context?.getString(R.string.xp_format, _xp.toString(), nextLvl))
        }

    val xp: Int
        get() = _xp


    private var _xpToNextLvl: Int = 0
        set(value)
        {
            field = value
            val nextLvl: String? = if (_lvl.value == maxLvl)
            {
                App.context?.getString(R.string.infinite_sign)
            }
            else
            {
                _xpToNextLvl.toString()
            }
            _xpString.postValue(App.context?.getString(R.string.xp_format, _xp.toString(), nextLvl))
        }


    private val _storage = MutableLiveData<String>()
    val storage: LiveData<String>
        get() = _storage

    private var currStorage: Int = 0
        set(value)
        {
            field = if (value > _bonuses.storageValue)
            {
                _bonuses.storageValue
            }
            else
            {
                value
            }
            _storage.value = "$field / ${_bonuses.storageValue}"
        }

    private val _crit = MutableLiveData<String>()
    val crit: LiveData<String>
        get() = _crit

    private var bonusAttackMultiplier: Int = 1
        set(value)
        {
            field = value
            currAttacksPerClick = (_bonuses.numbersAttackPerClickValue * bonusAttackMultiplier)
        }


    private var currCrit: Float = 0F
        set(value)
        {
            field = value
            Log.i(((field - 1.0)).format(2))
            Log.i(field)
            _crit.postValue(
                App.context?.getString(
                    R.string.crit_desc,
                    ((field - 1.0)).format(2)
                )
            )
        }


    private val _attackPerClick = MutableLiveData<String>()
    val attackPerClick: LiveData<String>
        get() = _attackPerClick


    private var currAttacksPerClick: Int = 1
        set(value)
        {
            field = value
            _attackPerClick.postValue(App.context?.getString(R.string.attack_per_click, value))
        }

    private val _dmg = MutableLiveData<String>()
    val dmg: LiveData<String>
        get() = _dmg

    //endregion

    init
    {
        Log.i("Init  GameState")
        loadGame()
        //todo load ad
//        MainActivity.mRewardedVideoAd
    }


    fun attackViruses(longClick: Int = 1)
    {
        Log.i("Base attack per click: ${bonuses.numbersAttackPerClickValue}. Critical Attack: ${bonuses.criticalAttackValue}. Bonus attack multiplier: $bonusAttackMultiplier")

        var dmg = 0
        for (i in 1..((bonuses.numbersAttackPerClickValue * bonusAttackMultiplier) * longClick))
        {
            dmg += if ((1..100).random() < bonuses.criticalAttackValue) // crit
            {
                Log.i("Crit")
                2
            }
            else
            {
                Log.i("Normal")
                1
            }
        }

        Log.i("$dmg dmg total")
        _dmg.value = dmg.toString()

        if (virus.attackVirus(dmg))//virus dead
        {
            Log.i("Dead")
            _money.value = _money.value!!.plus(
                (virus.reward.times(_bonuses.rewardMultiplierValue).toInt())
            )

            _xp = _xp.plus((virus.reward).div(10))

            if (_lvl.value!! < maxLvl && _xp >= _xpToNextLvl) //lvl upgrade
            {
                Log.i("New Lvl")
                _lvl.value = _lvl.value!!.plus(1)
                _xpToNextLvl = if (_lvl.value!! != maxLvl)
                {
                    xpArray[_lvl.value!!]
                }
                else
                {
                    Int.MAX_VALUE
                }
            }
            _killedViruses.value = _killedViruses.value!!.plus(1)

            _virus.setNewVirus((0.._lvl.value!!).random().toByte())
        }
        else
        {
            Log.i("Is alive")
        }
    }

    fun saveGame()
    {
        Log.i("Save game in GameState")
        val gameStateData = GameStateData(this)
        val virusData = VirusData(_virus)


        val gson = Gson()

        val gameStateDataString = gson.toJson(gameStateData)
        val virusDataString = gson.toJson(virusData)


        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context) ?: return
        with(sharedPreferences.edit()) {

            putString(context.getString(R.string.game_state_key), gameStateDataString)
            putString(context.getString(R.string.virus_key), virusDataString)
            putLong(context.getString(R.string.money_key), _money.value!!)
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

        val mon: Long = sharedPreferences.getLong(
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
                gson.fromJson(bonusesDataString, BonusesData::class.java)
            }


        _money.value = mon
        _killedViruses.value = gameStateData.killedViruses
        _lvl.value = gameStateData.lvl
        _xp = gameStateData.xp
        _savedLives.value = gameStateData.savedLives
        _xpToNextLvl = if (_lvl.value!! != maxLvl)
        {
            xpArray[_lvl.value!!]
        }
        else
        {
            Int.MAX_VALUE
        }

        Log.i("Load instance: $bonusesData")
        _bonuses = Bonuses(bonusesData)
        currStorage = sto
        currCrit = (_bonuses.criticalAttackValue + 100).toFloat() / 100
        _virus = Virus(virusData)

    }

    fun oneMinutePassed()
    {
        currStorage += _bonuses.coinsPerMinutesValue
        _savedLives.value = _savedLives.value!!.plus(_bonuses.savedLivesSum)

        val current = Calendar.getInstance().time

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context) ?: return
        with(sharedPreferences.edit()) {

            putLong(context.getString(R.string.last_minute_passed_key), current.time / 60_000)
            commit()
        }

    }


    fun loadMoneyAndBonuses()
    {

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        val mon: Long = sharedPreferences.getLong(
            context.getString(R.string.money_key),
            0
        )

        val bonusesDataString = sharedPreferences.getString(
            context.getString(R.string.bonuses_key),
            context.getString(R.string.NotLoaded)
        )

        val gson = Gson()
        val bonusesData =
            if (bonusesDataString!! == context.getString(R.string.NotLoaded)) //first run, no saved game
            {
                BonusesData()
            }
            else
            {
                gson.fromJson(bonusesDataString, BonusesData::class.java)
            }


        _bonuses = Bonuses(bonusesData)
        currCrit = (_bonuses.criticalAttackValue + 100).toFloat() / 100
        currAttacksPerClick = (_bonuses.numbersAttackPerClickValue * bonusAttackMultiplier)

        _money.value = mon
    }

    fun collectStorage()
    {
        _money.value = _money.value!!.plus(currStorage)
        currStorage = 0

    }

    fun updateAfterBreak()
    {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        val previous: Long = sharedPreferences.getLong(
            context.getString(R.string.last_minute_passed_key),
            -1
        )

        if (previous > 0)
        {
            val current = Calendar.getInstance().time
            val passed = (current.time / 60_000 - previous)

            currStorage += (_bonuses.coinsPerMinutesValue * passed).toInt()
            _savedLives.value = _savedLives.value!!.plus(_bonuses.savedLivesSum.times(passed))
            with(sharedPreferences.edit()) {
                putLong(context.getString(R.string.last_minute_passed_key), current.time / 60_000)
                commit()
            }
        }
    }

    fun bonusAttack(millis: Long)
    {
        bonusAttackMultiplier += 1

        Timer("SettingUp", false).schedule(millis) {
            Log.i("Ad bonus ends")
            bonusAttackMultiplier -= 1
        }

    }
}


private data class GameStateData(
    val killedViruses: Int,
    val savedLives: Long,
    val lvl: Int,
    val xp: Int
)
{
    constructor(g: GameState) : this(
        g.killedViruses.value!!,
        g.savedLives.value!!,
        g.lvl.value!!,
        g.xp
    )

    constructor() : this(
        0, 0, 0, 0
    )

}

fun Double.format(digits: Int) = "%.${digits}f".format(this)