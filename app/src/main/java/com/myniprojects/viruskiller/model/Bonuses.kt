package com.myniprojects.viruskiller.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber

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

    //region bonuses

    private val pricesSLM = intArrayOf(100, 200, 400, 800, 1600, 3200, 6400, 15000)
    private val valuesSLM = arrayOf(0, 1, 2, 4, 8, 16, 32, 64, 150)

    private val _savedLivesMultiplier0 = MutableLiveData<Byte>()
    val savedLivesMultiplier0: LiveData<Byte>
        get() = _savedLivesMultiplier0

    val savedLivesMultiplier0Value: Int
        get() = valuesSLM[_savedLivesMultiplier0.value!!.toInt()]

    private val _savedLivesMultiplier1 = MutableLiveData<Byte>()
    val savedLivesMultiplier1: LiveData<Byte>
        get() = _savedLivesMultiplier1

    val savedLivesMultiplier1Value: Int
        get() = valuesSLM[_savedLivesMultiplier1.value!!.toInt()]

    private val _savedLivesMultiplier2 = MutableLiveData<Byte>()
    val savedLivesMultiplier2: LiveData<Byte>
        get() = _savedLivesMultiplier2

    val savedLivesMultiplier2Value: Int
        get() = valuesSLM[_savedLivesMultiplier2.value!!.toInt()]

    private val _savedLivesMultiplier3 = MutableLiveData<Byte>()
    val savedLivesMultiplier3: LiveData<Byte>
        get() = _savedLivesMultiplier3

    val savedLivesMultiplier3Value: Int
        get() = valuesSLM[_savedLivesMultiplier3.value!!.toInt()]


    private val pricesCA = intArrayOf(200, 400, 800)
    private val valuesCA = arrayOf(0, 1, 2, 4)

    private val _criticalAttack = MutableLiveData<Byte>() //percentage value to give critical attack
    val criticalAttack: LiveData<Byte>
        get() = _criticalAttack

    val criticalAttackValue: Int
        get() = valuesCA[_criticalAttack.value!!.toInt()]


    private val pricesNAPC = intArrayOf(200, 300, 800, 2000)
    private val valuesNAPC = arrayOf(1, 2, 3, 4, 5)

    private val _numbersAttackPerClick = MutableLiveData<Byte>()
    val numbersAttackPerClick: LiveData<Byte>
        get() = _numbersAttackPerClick

    val numbersAttackPerClickValue: Int
        get() = valuesNAPC[_numbersAttackPerClick.value!!.toInt()]


    private val pricesCPM = intArrayOf(200, 400, 800)
    private val valuesCPM = arrayOf(1, 2, 4, 8)

    private val _coinsPerMinutes = MutableLiveData<Byte>()
    val coinsPerMinutes: LiveData<Byte>
        get() = _coinsPerMinutes

    val coinsPerMinutesValue: Int
        get() = valuesCPM[_coinsPerMinutes.value!!.toInt()]


    private val pricesRM = intArrayOf(20000, 45000, 90000)
    private val valuesRM = arrayOf(1F, 1.05F, 1.1F, 1.15F)

    private val _rewardMultiplier =
        MutableLiveData<Byte>()//multiplier the reward after killing virus
    val rewardMultiplier: LiveData<Byte>
        get() = _rewardMultiplier

    val rewardMultiplierValue: Float
        get() = valuesRM[_rewardMultiplier.value!!.toInt()]


    private val pricesS = intArrayOf(200, 400, 800)
    private val valuesS = arrayOf(200, 500, 900, 1200)

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
            Bonus(_savedLivesMultiplier0.value!!, pricesSLM, valuesSLM as Array<Number>)

        val bonusSavedLivesMultiplier1 =
            Bonus(_savedLivesMultiplier1.value!!, pricesSLM, valuesSLM as Array<Number>)

        val bonusSavedLivesMultiplier2 =
            Bonus(_savedLivesMultiplier2.value!!, pricesSLM, valuesSLM as Array<Number>)

        val bonusSavedLivesMultiplier3 =
            Bonus(_savedLivesMultiplier3.value!!, pricesSLM, valuesSLM as Array<Number>)

        val bonusCriticalAttack =
            Bonus(_criticalAttack.value!!, pricesCA, valuesCA as Array<Number>)

        val bonusNumberAttackPerClick =
            Bonus(_numbersAttackPerClick.value!!, pricesNAPC, valuesNAPC as Array<Number>)

        val bonusCoinsPerMinutes =
            Bonus(_coinsPerMinutes.value!!, pricesCPM, valuesCPM as Array<Number>)

        val bonusStorage = Bonus(_storage.value!!, pricesS, valuesS as Array<Number>)

        val bonusRewardMultiplier =
            Bonus(_rewardMultiplier.value!!, pricesRM, valuesRM as Array<Number>)

        return mutableListOf(
            bonusSavedLivesMultiplier0,
            bonusSavedLivesMultiplier1,
            bonusSavedLivesMultiplier2,
            bonusSavedLivesMultiplier3,
            bonusCriticalAttack,
            bonusNumberAttackPerClick,
            bonusCoinsPerMinutes,
            bonusStorage,
            bonusRewardMultiplier
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