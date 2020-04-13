package com.myniprojects.viruskiller.screens.game

import androidx.lifecycle.ViewModel
import com.myniprojects.viruskiller.model.GameState
import timber.log.Timber

class GameViewModel : ViewModel()
{

    private val _gameState: GameState =
        GameState()
    val gameState: GameState
        get() = _gameState


    override fun onCleared()
    {
        super.onCleared()
        Timber.i("Game VM cleared")
    }

    fun virusClick()
    {
        gameState.attackViruses()
    }
}