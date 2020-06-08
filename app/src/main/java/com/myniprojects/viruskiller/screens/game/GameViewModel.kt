package com.myniprojects.viruskiller.screens.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.myniprojects.viruskiller.model.GameState
import timber.log.Timber

class GameViewModel(application: Application) : AndroidViewModel(application)
{

    private val _gameState: GameState = GameState(getApplication())
    val gameState: GameState
        get() = _gameState


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

    fun loadMoney()
    {
        gameState.loadMoney()
    }

    fun virusClick()
    {
        gameState.attackViruses()
    }

}

