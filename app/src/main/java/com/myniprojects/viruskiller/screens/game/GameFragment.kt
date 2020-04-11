package com.myniprojects.viruskiller.screens.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.myniprojects.viruskiller.R
import  com.myniprojects.viruskiller.databinding.FragmentGameBinding


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
        return binding.root
    }

}
