package com.myniprojects.viruskiller.screens.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.google.gson.Gson
import com.myniprojects.viruskiller.MainActivity

import com.myniprojects.viruskiller.R
import  com.myniprojects.viruskiller.databinding.FragmentGameBinding
import com.myniprojects.viruskiller.model.BonusesData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_game.*
import timber.log.Timber


class GameFragment : Fragment(), RewardedVideoAdListener
{
    private lateinit var mRewardedVideoAd: RewardedVideoAd
    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        Timber.i("Game ViewModel OnCreate")
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
//        Timber.i("SaveInstanceState")
//        viewModel.saveGame() //saving when shutting off app
//    }

    override fun onPause()
    {
        super.onPause()
        Timber.i("Pause GameFragment")
        viewModel.saveGame()
        viewModel.onPause()
    }

    override fun onResume()
    {
        super.onResume()
        Timber.i("Resume GameFragment")
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
    }

    override fun onRewardedVideoAdClosed()
    {
        Timber.i("onRewardedVideoAdClosed")
        loadRewardedVideoAd()
    }

    override fun onRewardedVideoAdLeftApplication()
    {
        Timber.i("onRewardedVideoAdLeftApplication")
    }

    override fun onRewardedVideoAdLoaded()
    {
        Timber.i("onRewardedVideoAdLoaded")
        button.isEnabled = true
    }

    override fun onRewardedVideoAdOpened()
    {
        Timber.i("onRewardedVideoAdOpened")
    }

    override fun onRewardedVideoCompleted()
    {
        Timber.i("onRewardedVideoCompleted")
    }

    override fun onRewarded(p0: RewardItem?)
    {
        Timber.i("onRewarded")
        viewModel.rewardAttack(p0!!)
    }

    override fun onRewardedVideoStarted()
    {
        Timber.i("onRewardedVideoStarted")
    }

    override fun onRewardedVideoAdFailedToLoad(p0: Int)
    {
        Timber.i("onRewardedVideoAdFailedToLoad")
    }

    //endregion

}
