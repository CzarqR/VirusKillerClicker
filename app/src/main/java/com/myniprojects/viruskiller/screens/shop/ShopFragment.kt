package com.myniprojects.viruskiller.screens.shop

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.myniprojects.viruskiller.R
import com.myniprojects.viruskiller.databinding.FragmentShopBinding
import com.myniprojects.viruskiller.model.Bonuses
import com.myniprojects.viruskiller.model.BonusesData
import com.myniprojects.viruskiller.utils.BonusAdapter

class ShopFragment : Fragment()
{
    private lateinit var viewModel: ShopViewModel
    private lateinit var viewModelFactory: ShopViewModelFactory
    private lateinit var bonusAdapter: BonusAdapter
    private lateinit var binding: FragmentShopBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        val gson = Gson()
        val bonusesData = gson.fromJson(
            ShopFragmentArgs.fromBundle(requireArguments()).bonuses,
            BonusesData::class.java
        )

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shop,
            container,
            false
        )

        viewModelFactory = ShopViewModelFactory(
            ShopFragmentArgs.fromBundle(requireArguments()).money,
            Bonuses(bonusesData),
            requireContext()
        )

        viewModel = ViewModelProvider(this, viewModelFactory).get(ShopViewModel::class.java)


        binding.shopViewModel = viewModel
        binding.lifecycleOwner = this

        binding.butLaboratory.setOnClickListener {
            activity?.onBackPressed()
        }


        val animationDrawable =
            binding.back.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(0)
        animationDrawable.setExitFadeDuration(resources.getInteger(R.integer.background_animation_frame))
        animationDrawable.start()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        addDataSet()
    }

    private fun addDataSet()
    {
        val data = viewModel.bonusList
        bonusAdapter.initList(data.value!!)
    }

    private fun initRecyclerView()
    {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ShopFragment.context)
            bonusAdapter = BonusAdapter()
            bonusAdapter.shopViewModel = viewModel
            adapter = bonusAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun onPause()
    {
        super.onPause()
        viewModel.saveBonuses(0)
    }


}
