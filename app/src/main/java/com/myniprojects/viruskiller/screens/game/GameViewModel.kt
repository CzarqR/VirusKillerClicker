package com.myniprojects.viruskiller.screens.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.myniprojects.viruskiller.model.GameState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil
import com.google.android.gms.ads.reward.RewardItem
import com.myniprojects.viruskiller.utils.Log

class GameViewModel(application: Application) : AndroidViewModel(application)
{

    private val _gameState: GameState = GameState(getApplication())
    val gameState: GameState
        get() = _gameState
    private lateinit var parentJob: Job

    init
    {
        Log.i("Init GameViewModel")
    }

    fun onResume()
    {
        parentJob = viewModelScope.launch {
            // suspend till first minute comes after some seconds
            delay((ceil(System.currentTimeMillis() / 60_000.0).toLong() * 60_000) - System.currentTimeMillis())
            while (true)
            {
                launch {
                    oneMinutePassed()
                }
                delay(60_000)  // 1 minute delay (suspending)
            }
        }
    }

    fun onPause()
    {
        parentJob.cancel()
    }


    private fun oneMinutePassed()
    {
        _gameState.oneMinutePassed()
    }

    fun saveGame()
    {
        gameState.saveGame()
    }

    fun loadMoneyAndBonuses()
    {
        gameState.loadMoneyAndBonuses()
    }

    fun rewardAttack(r: RewardItem)
    {
        Log.i("${r.amount} ${r.type}")
        _gameState.bonusAttack(120_000)
    }

}

