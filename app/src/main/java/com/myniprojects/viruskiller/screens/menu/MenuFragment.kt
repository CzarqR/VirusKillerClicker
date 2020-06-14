package com.myniprojects.viruskiller.screens.menu

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.myniprojects.viruskiller.R
import com.myniprojects.viruskiller.databinding.FragmentMenuBinding

class MenuFragment : Fragment()
{
    private lateinit var viewModel: MenuViewModel
    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_menu,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)
        binding.menuViewModel = viewModel
        binding.lifecycleOwner = this

        binding.txtHelp.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.menu_to_help)
        )

        binding.imgHelp.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.menu_to_help)
        )

        binding.txtLaboratory.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.menu_to_game)
        )

        binding.imgLaboratory.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.menu_to_game)
        )


        val animationDrawable =
            binding.back.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()

        return binding.root

    }

}