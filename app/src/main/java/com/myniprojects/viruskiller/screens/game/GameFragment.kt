package com.myniprojects.viruskiller.screens.game

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.myniprojects.viruskiller.MainActivity

import com.myniprojects.viruskiller.R
import  com.myniprojects.viruskiller.databinding.FragmentGameBinding
import com.myniprojects.viruskiller.model.BonusesData
import com.myniprojects.viruskiller.utils.App
import com.myniprojects.viruskiller.utils.Log
import kotlinx.android.synthetic.main.fragment_game.*


class GameFragment : Fragment(), RewardedVideoAdListener
{

    private val anim0: Animation by lazy {
        AnimationUtils.loadAnimation(App.context, R.anim.virus_click_0)
    }

    private val anim1: Animation by lazy {
        AnimationUtils.loadAnimation(App.context, R.anim.virus_click_1)
    }

    private val anim2: Animation by lazy {
        AnimationUtils.loadAnimation(App.context, R.anim.virus_click_2)
    }
    private val anim3: Animation by lazy {
        AnimationUtils.loadAnimation(App.context, R.anim.virus_click_3)
    }

    private val virusArray = arrayOf(anim0, anim1, anim2, anim3)

    private lateinit var mRewardedVideoAd: RewardedVideoAd
    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding
    private var wasRewarded: Boolean = false
    private var adLoadRequests = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        Log.i("Game ViewModel OnCreate")
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
            MainActivity.showInterstitialAd()
            Navigation.findNavController(requireView()).navigate(action)
        }

        // rewarded ad
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context)
        mRewardedVideoAd.rewardedVideoAdListener = this
        loadRewardedVideoAd()

        val animationDrawable =
            binding.conLay.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()

        binding.imgVirus.setOnClickListener {
            viewModel.gameState.attackViruses()

            it.startAnimation(virusArray.random())
        }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            it.isEnabled = false
            mRewardedVideoAd.show()
        }
    }


//    override fun onSaveInstanceState(outState: Bundle)
//    {
//        super.onSaveInstanceState(outState)
//        Log.i("SaveInstanceState")
//        viewModel.saveGame() //saving when shutting off app
//    }

    override fun onPause()
    {
        super.onPause()
        Log.i("Pause GameFragment")
        viewModel.saveGame()
        viewModel.onPause()
    }

    override fun onResume()
    {
        super.onResume()
        Log.i("Resume GameFragment")
        viewModel.loadMoneyAndBonuses()
        viewModel.gameState.updateAfterBreak()
        viewModel.onResume()
    }

    // region Reward ad

    private fun loadRewardedVideoAd()
    {
        mRewardedVideoAd.loadAd(
            getString(R.string.reward_ad),
            AdRequest.Builder().build()
        )
        adLoadRequests = 0
    }

    override fun onRewardedVideoAdClosed()
    {
        Log.i("onRewardedVideoAdClosed")
        loadRewardedVideoAd()
        if (wasRewarded)
        {
            MainActivity.showSnackbar(
                butShop,
                getString(R.string.snackbar_attack, 1, 3),
                getString(R.string.cool),
                View.OnClickListener {
                    Log.i("Snackbar cool clicked")
                },
                duration = Snackbar.LENGTH_LONG
            )
            wasRewarded = false
        }

    }

    override fun onRewardedVideoAdLeftApplication()
    {
        Log.i("onRewardedVideoAdLeftApplication")
    }

    override fun onRewardedVideoAdLoaded()
    {
        Log.i("onRewardedVideoAdLoaded")
        button.isEnabled = true
    }

    override fun onRewardedVideoAdOpened()
    {
        Log.i("onRewardedVideoAdOpened")
    }

    override fun onRewardedVideoCompleted()
    {
        Log.i("onRewardedVideoCompleted")
    }

    override fun onRewarded(p0: RewardItem?)
    {
        Log.i("onRewarded")
        wasRewarded = true
        viewModel.rewardAttack(p0!!)
    }

    override fun onRewardedVideoStarted()
    {
        Log.i("onRewardedVideoStarted")
    }

    override fun onRewardedVideoAdFailedToLoad(p0: Int)
    {
        Log.i("onRewardedVideoAdFailedToLoad")
        adLoadRequests++
        if (adLoadRequests < 5)
        {
            loadRewardedVideoAd()
        }
    }

    //endregion

}

