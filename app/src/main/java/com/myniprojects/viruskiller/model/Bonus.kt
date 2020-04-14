package com.myniprojects.viruskiller.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Bonus(
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


}