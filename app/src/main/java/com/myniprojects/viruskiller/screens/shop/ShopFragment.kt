package com.myniprojects.viruskiller.screens.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.myniprojects.viruskiller.R


class ShopFragment : Fragment()
{

    private lateinit var viewModel: ShopViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        viewModel = ViewModelProvider(this).get(ShopViewModel::class.java)

        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

}
