package com.myniprojects.viruskiller.screens.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.myniprojects.viruskiller.R
import com.myniprojects.viruskiller.model.Bonuses
import com.myniprojects.viruskiller.model.BonusesData
import com.myniprojects.viruskiller.utils.BonusAdapter
import kotlinx.android.synthetic.main.fragment_shop.*
import timber.log.Timber


class ShopFragment : Fragment()
{

    private lateinit var viewModel: ShopViewModel
    private lateinit var viewModelFactory: ShopViewModelFactory
    private lateinit var bonusAdapter: BonusAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val gson = Gson()
        val bonusesData = gson.fromJson(
            ShopFragmentArgs.fromBundle(requireArguments()).bonuses,
            BonusesData::class.java
        )

        viewModelFactory = ShopViewModelFactory(
            ShopFragmentArgs.fromBundle(requireArguments()).money,
            Bonuses(bonusesData)
        )

        viewModel = ViewModelProvider(this, viewModelFactory).get(ShopViewModel::class.java)

        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        addDataSet()
    }

    private fun addDataSet()
    {
        val data = Bonuses.getBonusList()
        bonusAdapter.initList(data)
    }

    private fun initRecyclerView()
    {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ShopFragment.context)
            bonusAdapter = BonusAdapter()
            adapter = bonusAdapter
        }
    }

}
