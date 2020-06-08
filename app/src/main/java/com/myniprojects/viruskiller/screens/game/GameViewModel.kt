package com.myniprojects.viruskiller.screens.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.myniprojects.viruskiller.model.GameState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.ceil

class GameViewModel(application: Application) : AndroidViewModel(application)
{

    private val _gameState: GameState = GameState(getApplication())
    val gameState: GameState
        get() = _gameState
    private lateinit var parentJob: Job

    init
    {
        Timber.i("Init GameViewModel")
    }

    fun onResume()
    {
        parentJob = viewModelScope.launch {
            // suspend till first minute comes after some seconds
            delay((ceil(System.currentTimeMillis() / 10_000.0).toLong() * 10_000) - System.currentTimeMillis())
            while (true) {
                launch {
                    oneMinutePassed()
                }
                delay(10_000)  // 1 minute delay (suspending)
            }
        }
    }

    fun onPause()
    {
        parentJob.cancel()
    }




    private fun oneMinutePassed()
    {
        Timber.i("Execute")
        _gameState.oneMinutePassed()
    }

    override fun onCleared()
    {
        super.onCleared()
        Timber.i("Game view model cleared")
        //saveGame() //saving when closing game fragment
    }



    fun printBonuses()
    {
        _gameState.test()
    }

    fun saveGame()
    {
        gameState.saveGame()
    }

    fun loadMoneyAndBonuses()
    {
        gameState.loadMoneyAndBonuses()
    }

    fun virusClick()
    {
        gameState.attackViruses()
    }

}

