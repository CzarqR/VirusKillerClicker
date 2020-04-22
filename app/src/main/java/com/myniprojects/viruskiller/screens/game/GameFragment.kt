package com.myniprojects.viruskiller.screens.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.gson.Gson

import com.myniprojects.viruskiller.R
import  com.myniprojects.viruskiller.databinding.FragmentGameBinding
import com.myniprojects.viruskiller.model.Bonuses
import com.myniprojects.viruskiller.model.BonusesData
import kotlinx.android.synthetic.main.fragment_game.*
import timber.log.Timber


class GameFragment : Fragment()
{

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        binding.butShop.setOnClickListener {

            val gson = Gson()
            val bonusesDataString = gson.toJson(BonusesData(viewModel.gameState.bonuses))

            val action = GameFragmentDirections.gameToShop(
                bonusesDataString,
                viewModel.gameState.money.value!!
            )

            Navigation.findNavController(requireView()).navigate(action)
        }

        return binding.root
    }


    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)
        viewModel.saveGame() //saving when shutting off app
    }

}
