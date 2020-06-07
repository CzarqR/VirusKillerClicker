package com.myniprojects.viruskiller.screens.game

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myniprojects.viruskiller.R
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
        _gameState.printBonuses()
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

