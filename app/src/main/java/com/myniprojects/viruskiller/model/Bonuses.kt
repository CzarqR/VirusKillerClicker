package com.myniprojects.viruskiller.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myniprojects.viruskiller.R
import com.myniprojects.viruskiller.utils.App

class Bonuses(
    multiplier0: Byte,
    multiplier1: Byte,
    multiplier2: Byte,
    multiplier3: Byte,
    critAttack: Byte,
    coinsPM: Byte,
    store: Byte,
    rewardMulti: Byte,
    attackPC: Byte
)
{

    companion object
    {
        val pricesSLM =
            intArrayOf(100, 200, 400, 800, 1600, 3200, 6400, 12_800, 25_600, 51_200, 102_400)
        val valuesSLM = arrayOf(0, 1, 2, 4, 8, 16, 32, 64, 128, 252, 512, 1024)

        val pricesCA = intArrayOf(500, 2000, 5000, 12000, 28000, 56000)
        val valuesCA = arrayOf(1, 5, 10, 25, 50, 75, 100)

        val pricesNAPC = intArrayOf(10_000, 25_000, 35_000, 50_000)
        val valuesNAPC = arrayOf(1, 2, 3, 4, 5)

        val pricesCPM = intArrayOf(1200, 3600, 6000, 18_000, 30_000)
        val valuesCPM = arrayOf(1, 2, 5, 10, 25, 50)

        val pricesRM = intArrayOf(2_000, 5_000, 12_000, 30_000)
        val valuesRM = arrayOf(1F, 1.05F, 1.1F, 1.2F, 1.5F)

        val pricesS = intArrayOf(600, 1200, 3000, 15000, 60000, 120000, 240000)
        val valuesS = arrayOf(60, 120, 300, 1500, 3000, 6000, 12000, 24000)
    }

    //region bonuses


    private val _savedLivesMultiplier0 = MutableLiveData<Byte>()
    val savedLivesMultiplier0: LiveData<Byte>
        get() = _savedLivesMultiplier0

    private val savedLivesMultiplier0Value: Int
        get() = valuesSLM[_savedLivesMultiplier0.value!!.toInt()]

    private val _savedLivesMultiplier1 = MutableLiveData<Byte>()
    val savedLivesMultiplier1: LiveData<Byte>
        get() = _savedLivesMultiplier1

    private val savedLivesMultiplier1Value: Int
        get() = valuesSLM[_savedLivesMultiplier1.value!!.toInt()]

    private val _savedLivesMultiplier2 = MutableLiveData<Byte>()
    val savedLivesMultiplier2: LiveData<Byte>
        get() = _savedLivesMultiplier2

    private val savedLivesMultiplier2Value: Int
        get() = valuesSLM[_savedLivesMultiplier2.value!!.toInt()]

    private val _savedLivesMultiplier3 = MutableLiveData<Byte>()
    val savedLivesMultiplier3: LiveData<Byte>
        get() = _savedLivesMultiplier3

    private val savedLivesMultiplier3Value: Int
        get() = valuesSLM[_savedLivesMultiplier3.value!!.toInt()]

    val savedLivesSum: Int
        get() = savedLivesMultiplier0Value + savedLivesMultiplier1Value + savedLivesMultiplier2Value + savedLivesMultiplier3Value


    private val _criticalAttack = MutableLiveData<Byte>() //percentage value to give critical attack
    val criticalAttack: LiveData<Byte>
        get() = _criticalAttack

    val criticalAttackValue: Int
        get() = valuesCA[_criticalAttack.value!!.toInt()]


    private val _numbersAttackPerClick = MutableLiveData<Byte>()
    val numbersAttackPerClick: LiveData<Byte>
        get() = _numbersAttackPerClick

    val numbersAttackPerClickValue: Int
        get() = valuesNAPC[_numbersAttackPerClick.value!!.toInt()]


    private val _coinsPerMinutes = MutableLiveData<Byte>()
    val coinsPerMinutes: LiveData<Byte>
        get() = _coinsPerMinutes

    val coinsPerMinutesValue: Int
        get() = valuesCPM[_coinsPerMinutes.value!!.toInt()]


    private val _rewardMultiplier =
        MutableLiveData<Byte>()//multiplier the reward after killing virus
    val rewardMultiplier: LiveData<Byte>
        get() = _rewardMultiplier

    val rewardMultiplierValue: Float
        get() = valuesRM[_rewardMultiplier.value!!.toInt()]


    private val _storage = MutableLiveData<Byte>()
    val storage: LiveData<Byte>
        get() = _storage

    val storageValue: Int
        get() = valuesS[_storage.value!!.toInt()]


    //endregion

    init
    {
        _savedLivesMultiplier0.value = multiplier0
        _savedLivesMultiplier1.value = multiplier1
        _savedLivesMultiplier2.value = multiplier2
        _savedLivesMultiplier3.value = multiplier3
        _criticalAttack.value = critAttack
        _coinsPerMinutes.value = coinsPM
        _rewardMultiplier.value = rewardMulti
        _numbersAttackPerClick.value = attackPC
        _storage.value = store
    }

    constructor(bD: BonusesData) : this(
        bD.savedLivesMultiplier0,
        bD.savedLivesMultiplier1,
        bD.savedLivesMultiplier2,
        bD.savedLivesMultiplier3,
        bD.criticalAttack,
        bD.coinsPerMinutes,
        bD.storage,
        bD.rewardMultiplier,
        bD.numbersAttackPerClick
    )


    @Suppress("UNCHECKED_CAST")
    fun getBonusList(): MutableList<Bonus>
    {
        val bonusSavedLivesMultiplier0 =
            Bonus(
                _savedLivesMultiplier0.value!!,
                pricesSLM,
                valuesSLM as Array<Number>,
                App.context!!.getString(R.string.masks),
                App.context!!.getString(R.string.masks_desc),
                R.drawable.mask

            )

        val bonusSavedLivesMultiplier1 =
            Bonus(
                _savedLivesMultiplier1.value!!, pricesSLM, valuesSLM as Array<Number>,
                App.context!!.getString(R.string.gloves),
                App.context!!.getString(R.string.gloves_desc),
                R.drawable.gloves
            )

        val bonusSavedLivesMultiplier2 =
            Bonus(
                _savedLivesMultiplier2.value!!, pricesSLM, valuesSLM as Array<Number>,
                App.context!!.getString(R.string.hand_washing_preparations),
                App.context!!.getString(R.string.hand_washing_preparations_desc),
                R.drawable.hend_wash
            )

        val bonusSavedLivesMultiplier3 =
            Bonus(
                _savedLivesMultiplier3.value!!, pricesSLM, valuesSLM as Array<Number>,
                App.context!!.getString(R.string.vaccine),
                App.context!!.getString(R.string.vaccine_desc),
                R.drawable.vaccine
            )

        val bonusCriticalAttack =
            Bonus(
                _criticalAttack.value!!, pricesCA, valuesCA as Array<Number>,
                App.context!!.getString(R.string.critical_attack),
                App.context!!.getString(R.string.critical_attack_desc),
                R.drawable.attack
            )

        val bonusNumberAttackPerClick =
            Bonus(
                _numbersAttackPerClick.value!!, pricesNAPC, valuesNAPC as Array<Number>,
                App.context!!.getString(R.string.attack_numbers_per_click),
                App.context!!.getString(R.string.attack_numbers_per_click_desc),
                R.drawable.click
            )

        val bonusCoinsPerMinutes =
            Bonus(
                _coinsPerMinutes.value!!, pricesCPM, valuesCPM as Array<Number>,
                App.context!!.getString(R.string.coins_per_minute),
                App.context!!.getString(R.string.coins_per_minute_desc),
                R.drawable.coin
            )

        val bonusStorage = Bonus(
            _storage.value!!, pricesS, valuesS as Array<Number>,
            App.context!!.getString(R.string.storage),
            App.context!!.getString(R.string.storage_desc),
            R.drawable.chest
        )

        val bonusRewardMultiplier =
            Bonus(
                _rewardMultiplier.value!!,
                pricesRM,
                valuesRM as Array<Number>,
                App.context!!.getString(R.string.reward_multiplier),
                App.context!!.getString(R.string.reward_multiplier_desc),
                R.drawable.multiplier
            )

        return mutableListOf(
            bonusSavedLivesMultiplier0,
            bonusSavedLivesMultiplier1,
            bonusSavedLivesMultiplier2,
            bonusSavedLivesMultiplier3,
            bonusCriticalAttack,
            bonusNumberAttackPerClick,
            bonusCoinsPerMinutes,
            bonusRewardMultiplier,
            bonusStorage

        )
    }


}

data class BonusesData(
    val savedLivesMultiplier0: Byte,
    val savedLivesMultiplier1: Byte,
    val savedLivesMultiplier2: Byte,
    val savedLivesMultiplier3: Byte,
    val criticalAttack: Byte,
    val numbersAttackPerClick: Byte,
    val coinsPerMinutes: Byte,
    val rewardMultiplier: Byte,
    val storage: Byte
)
{
    constructor(b: Bonuses) : this(
        b.savedLivesMultiplier0.value!!,
        b.savedLivesMultiplier1.value!!,
        b.savedLivesMultiplier2.value!!,
        b.savedLivesMultiplier3.value!!,
        b.criticalAttack.value!!,
        b.numbersAttackPerClick.value!!,
        b.coinsPerMinutes.value!!,
        b.rewardMultiplier.value!!,
        b.storage.value!!
    )


    constructor() : this(
        0, 0, 0, 0, 0, 0, 0, 0, 0
    )
}