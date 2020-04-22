package com.myniprojects.viruskiller.screens.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.myniprojects.viruskiller.R
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    private lateinit var viewModel: MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        butHelp.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.menu_to_help)
        )
        butPlay.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.menu_to_game)
        )


    }

}