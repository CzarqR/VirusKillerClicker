package com.myniprojects.viruskiller.screens.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.myniprojects.viruskiller.R
import kotlinx.android.synthetic.main.fragment_menu.*
import timber.log.Timber

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
