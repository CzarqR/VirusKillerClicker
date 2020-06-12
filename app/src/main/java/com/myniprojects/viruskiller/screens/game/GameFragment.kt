package com.myniprojects.viruskiller.screens.game

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
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
import es.dmoral.toasty.Toasty
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
    private val virusAnimArray by lazy {
        arrayOf(anim0, anim1, anim2, anim3)
    }

    private val animLong0: Animation by lazy {
        AnimationUtils.loadAnimation(App.context, R.anim.virus_longclick_0)
    }
    private val animLong1: Animation by lazy {
        AnimationUtils.loadAnimation(App.context, R.anim.virus_longclick_1)
    }
    private val virusAnimArrayLong by lazy {
        arrayOf(animLong0, animLong1)
    }

    private lateinit var toast: Toast

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

        binding.imgShop.setOnClickListener {

            val gson = Gson()
            val bonusesDataString = gson.toJson(BonusesData(viewModel.gameState.bonuses))

            val action = GameFragmentDirections.gameToShop(
                bonusesDataString,
                viewModel.gameState.money.value!!
            )
            //MainActivity.showInterstitialAd()
            Navigation.findNavController(requireView()).navigate(action)
        }

        // rewarded ad
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context)
        mRewardedVideoAd.rewardedVideoAdListener = this
        loadRewardedVideoAd()

        val animationDrawable =
            binding.conLayBack.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()

        binding.imgVirus.setOnClickListener {
            viewModel.gameState.attackViruses()

            it.startAnimation(virusAnimArray.random())
        }

        binding.imgVirus.setOnLongClickListener {
            it.startAnimation(virusAnimArrayLong.random())
            viewModel.gameState.attackViruses(7)
            true
        }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding.imgAdReward.setOnClickListener {
            if (mRewardedVideoAd.isLoaded)
            {
                mRewardedVideoAd.show()
                binding.imgAdReward.setImageResource(R.drawable.ad_loading)
            }
            else
            {
                infoToast(R.string.ad_not_loaded)
            }
        }

        //region long click info

        binding.imgKilled.setOnLongClickListener {
            infoToast(R.string.killed_viruses)
            true
        }

        binding.imgSaved.setOnLongClickListener {
            infoToast(R.string.saved_lives)
            true
        }

        binding.imgMoney.setOnLongClickListener {
            infoToast(R.string.money)
            true
        }

        binding.imgLvl.setOnLongClickListener {
            infoToast(R.string.level)
            true
        }

        binding.imgXp.setOnLongClickListener {
            infoToast(R.string.xp)
            true
        }

        binding.imgStorage.setOnLongClickListener {
            infoToast(R.string.storage)
            true
        }

        binding.imgAdReward.setOnLongClickListener {
            infoToast(R.string.ad_reward_info)
            true
        }

        binding.imgShop.setOnLongClickListener {
            infoToast(R.string.shop)
            true
        }

        //endregion


    }

    private fun infoToast(textId: Int)
    {

        if (this::toast.isInitialized)
        {
            toast.cancel()
        }

        toast = Toasty.custom(
            requireContext(),
            requireContext().getString(textId),
            R.drawable.ic_baseline_info_24,
            R.color.toast_1,
            R.color.toast_2,
            true,
            true
        )
        toast.show()

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
                imgShop,
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
        binding.imgAdReward.setImageResource(R.drawable.ad_loaded)
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

