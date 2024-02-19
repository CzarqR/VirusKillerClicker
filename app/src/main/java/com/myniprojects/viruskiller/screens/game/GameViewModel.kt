package com.myniprojects.viruskiller.screens.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.myniprojects.viruskiller.model.GameState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil

class GameViewModel(application: Application) : AndroidViewModel(application)
{

    private val _gameState: GameState = GameState(getApplication())
    val gameState: GameState
        get() = _gameState
    private lateinit var parentJob: Job


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

    fun rewardAttack()
    {
        _gameState.bonusAttack(120_000)
    }

}

