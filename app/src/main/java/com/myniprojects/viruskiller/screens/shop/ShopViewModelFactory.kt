package com.myniprojects.viruskiller.screens.shop

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myniprojects.viruskiller.model.Bonuses

class ShopViewModelFactory(
    private val money: Long,
    private val bonuses: Bonuses,
    private val context: Context
) : ViewModelProvider.Factory
{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(ShopViewModel::class.java))
        {
            return ShopViewModel(money, bonuses, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}