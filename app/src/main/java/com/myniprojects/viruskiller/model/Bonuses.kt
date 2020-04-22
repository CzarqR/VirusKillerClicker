package com.myniprojects.viruskiller.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Bonuses(
    multiplier0: Byte,
    multiplier1: Byte,
    multiplier2: Byte,
    multiplier3: Byte,
    critAttack: Byte,
    coinsPM: Int,
    store: Int,
    rewardMulti: Float,
    attackPC: Byte
)
{

    private val _savedLivesMultiplier0 = MutableLiveData<Byte>()
    val savedLivesMultiplier0: LiveData<Byte>
        get() = _savedLivesMultiplier0

    private val _savedLivesMultiplier1 = MutableLiveData<Byte>()
    val savedLivesMultiplier1: LiveData<Byte>
        get() = _savedLivesMultiplier1

    private val _savedLivesMultiplier2 = MutableLiveData<Byte>()
    val savedLivesMultiplier2: LiveData<Byte>
        get() = _savedLivesMultiplier2

    private val _savedLivesMultiplier3 = MutableLiveData<Byte>()
    val savedLivesMultiplier3: LiveData<Byte>
        get() = _savedLivesMultiplier3

    private val _criticalAttack = MutableLiveData<Byte>()
    val criticalAttack: LiveData<Byte>
        get() = _criticalAttack

    private val _numbersAttackPerClick = MutableLiveData<Byte>()
    val numbersAttackPerClick: LiveData<Byte>
        get() = _numbersAttackPerClick

    private val _coinsPerMinutes = MutableLiveData<Int>()
    val coinsPerMinutes: LiveData<Int>
        get() = _coinsPerMinutes

    private val _rewardMultiplier = MutableLiveData<Float>()
    val rewardMultiplier: LiveData<Float>
        get() = _rewardMultiplier

    private val _storage = MutableLiveData<Int>()
    val storage: LiveData<Int>
        get() = _storage

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

    companion object
    {
        fun getBonusList(): List<Bonus>
        {
            //todo return list based on class instance and remove from companion
            val bonus1 = Bonus(10, 0, arrayOf(10, 20, 100, 400))
            val bonus2 = Bonus(15, 1, arrayOf(1, 25, 250, 600))
            val bonus3 = Bonus(10, 0, arrayOf(10, 20, 100, 400))
            val bonus4 = Bonus(15, 1, arrayOf(1, 25, 250, 600))
            val bonus5 = Bonus(10, 0, arrayOf(10, 20, 100, 400))
            val bonus6 = Bonus(15, 1, arrayOf(1, 25, 250, 600))
            val bonus7 = Bonus(10, 0, arrayOf(10, 20, 100, 400))
            val bonus8 = Bonus(15, 1, arrayOf(1, 25, 250, 600))
            return listOf(bonus1, bonus2, bonus3, bonus4, bonus5, bonus6, bonus7, bonus8)
        }
    }


}

data class BonusesData(
    val savedLivesMultiplier0: Byte,
    val savedLivesMultiplier1: Byte,
    val savedLivesMultiplier2: Byte,
    val savedLivesMultiplier3: Byte,
    val criticalAttack: Byte,
    val numbersAttackPerClick: Byte,
    val coinsPerMinutes: Int,
    val rewardMultiplier: Float,
    val storage: Int
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
        0, 0, 0, 0, 0, 1, 0, 0F, 0
    )
}