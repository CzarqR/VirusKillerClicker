package com.myniprojects.viruskiller.screens.help

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.myniprojects.viruskiller.R
import com.myniprojects.viruskiller.screens.game.GameViewModel

class HelpFragment : Fragment() {

    private lateinit var viewModel: HelpViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HelpViewModel::class.java)
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

}
