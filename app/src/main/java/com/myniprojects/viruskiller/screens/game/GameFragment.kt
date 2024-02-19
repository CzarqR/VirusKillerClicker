package com.myniprojects.viruskiller.screens.game

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.myniprojects.viruskiller.MainActivity
import com.myniprojects.viruskiller.R
import com.myniprojects.viruskiller.databinding.FragmentGameBinding
import com.myniprojects.viruskiller.model.BonusesData
import com.myniprojects.viruskiller.utils.App
import com.myniprojects.viruskiller.utils.Log
import es.dmoral.toasty.Toasty


class GameFragment : Fragment()
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
    private var rewardedAd: RewardedAd? = null
    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding
    private var wasRewarded: Boolean = false
    private var adLoadRequests = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        binding.imgShop.setOnClickListener {

            val gson = Gson()
            val bonusesDataString = gson.toJson(BonusesData(viewModel.gameState.bonuses))

            val action = GameFragmentDirections.gameToShop(
                bonusesDataString,
                viewModel.gameState.money.value!!
            )
            MainActivity.showInterstitialAd(requireActivity())
            Navigation.findNavController(requireView()).navigate(action)
        }

        // rewarded ad
        loadRewardedVideoAd()

        val animationDrawable =
            binding.conLayBack.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(0)
        animationDrawable.setExitFadeDuration(resources.getInteger(R.integer.background_animation_frame))
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
            if (rewardedAd != null)
            {
                rewardedAd?.show(requireActivity(), ::rewardUser)
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


        viewModel.gameState.dmg.observe(viewLifecycleOwner) {
            with(binding.txtDmg)
            {
                text = it
                alpha = 1F
                scaleY = 1F
                scaleX = 1F
                translationY = 0F

                animate().alpha(0.4F).scaleX(1.5F).scaleY(1.5F).translationY(-110F).setDuration(100)
                    .withEndAction { binding.txtDmg.text = "" }
            }
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
        adLoadRequests++
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            requireContext(),
            getString(R.string.reward_ad),
            adRequest,
            object : RewardedAdLoadCallback()
            {
                override fun onAdFailedToLoad(adError: LoadAdError)
                {
                    rewardedAd = null
                    loadRewardedVideoAd()
                }

                override fun onAdLoaded(ad: RewardedAd)
                {
                    adLoadRequests = 0
                    rewardedAd = ad
                    rewardedAd?.fullScreenContentCallback = adCallback
                    binding.imgAdReward.setImageResource(R.drawable.ad_loaded)
                }
            }
        )
    }

    private val adCallback = object : FullScreenContentCallback()
    {
        override fun onAdClicked() = Unit
        override fun onAdImpression() = Unit
        override fun onAdShowedFullScreenContent() = Unit

        override fun onAdDismissedFullScreenContent()
        {
            rewardedAd = null
            loadRewardedVideoAd()
            showSuccessSnackbar()
        }

        override fun onAdFailedToShowFullScreenContent(adError: AdError)
        {
            rewardedAd = null
            loadRewardedVideoAd()
        }
    }

    private fun rewardUser(rewardItem: RewardItem)
    {
        wasRewarded = true
        loadRewardedVideoAd()
    }

    private fun showSuccessSnackbar()
    {
        if (!wasRewarded)
        {
            return
        }
        viewModel.rewardAttack()
        MainActivity.showSnackbar(
            binding.imgShop,
            getString(R.string.snackbar_attack, 2),
            getString(R.string.cool),
            {
                Log.i("Snackbar cool clicked")
            },
            duration = Snackbar.LENGTH_LONG
        )
        wasRewarded = false
    }

    //endregion

}

