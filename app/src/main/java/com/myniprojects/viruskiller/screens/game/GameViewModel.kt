package com.myniprojects.viruskiller.screens.game

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myniprojects.viruskiller.model.GameState
import timber.log.Timber

class GameViewModel : ViewModel()
{

    private val _gameState: GameState = GameState()
    val gameState: GameState
        get() = _gameState


    override fun onCleared()
    {
        super.onCleared()
        saveGame() //saving when closing game fragment
    }

    fun saveGame()
    {
        gameState.saveGame()
        Timber.i("55555555555555555555555555555555555555555555555555555555555")
    }


    fun virusClick()
    {
        gameState.attackViruses()
    }


}

