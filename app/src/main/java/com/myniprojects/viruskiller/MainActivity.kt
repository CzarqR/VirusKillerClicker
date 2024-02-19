package com.myniprojects.viruskiller

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.snackbar.Snackbar
import com.myniprojects.viruskiller.databinding.ActivityMainBinding
import com.myniprojects.viruskiller.utils.App
import com.myniprojects.viruskiller.utils.Log
import timber.log.Timber


class MainActivity : AppCompatActivity()
{
    private var isAdBannerLoaded = false
    private var loadAdBannerRequests = 0
    private var loadAdInterstitialRequests = 0
    private val animAd: Animation by lazy {
        AnimationUtils.loadAnimation(App.context, R.anim.zoom_ad)
    }
    private lateinit var binding: ActivityMainBinding

    companion object
    {
        private var mInterstitialAd: InterstitialAd? = null
        fun showInterstitialAd(activity: Activity)
        {
            mInterstitialAd?.show(activity) ?: run {
                Log.i("The interstitial wasn't loaded yet.")
            }
        }

        fun showSnackbar(
            v: View,
            content: String,
            buttonText: String? = null,
            onClickListener: View.OnClickListener? = null,
            isTop: Boolean = true,
            duration: Int = Snackbar.LENGTH_SHORT
        )
        {
            val snack: Snackbar = Snackbar.make(v, content, duration)
            if (buttonText != null)
            {
                snack.setAction(buttonText, onClickListener)
            }

            if (isTop)
            {
                val view = snack.view
                val params = view.layoutParams as FrameLayout.LayoutParams
                params.gravity = Gravity.TOP
                view.layoutParams = params
            }
            snack.show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) {}

        // banner
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.adView.adListener = object : AdListener()
        {
            override fun onAdFailedToLoad(p0: LoadAdError)
            {
                super.onAdFailedToLoad(p0)
                if (loadAdBannerRequests++ < 5)
                {
                    binding.adView.loadAd(adRequest)
                }
            }

            override fun onAdLoaded()
            {
                super.onAdLoaded()
                loadAdBannerRequests = 0
                isAdBannerLoaded = true
            }
        }

        // interstitial
        loadFullScreenAd()


        Timber.plant(Timber.DebugTree())
        binding.adView.startAnimation(animAd)

        //RxJava bus test
        //RxBus.publish(RxEvent.EventAdWatched("Attack"))
    }

    private fun loadFullScreenAd()
    {
        val adRequestFullScreen = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            getString(R.string.fullscreen_ad),
            adRequestFullScreen,
            object : InterstitialAdLoadCallback()
            {
                override fun onAdLoaded(interstitialAd: InterstitialAd)
                {
                    loadAdInterstitialRequests = 0
                    mInterstitialAd = interstitialAd
                    mInterstitialAd!!.fullScreenContentCallback =
                        object : FullScreenContentCallback()
                        {
                            override fun onAdDismissedFullScreenContent()
                            {
                                loadFullScreenAd()
                            }
                        }
                }


                override fun onAdFailedToLoad(loadAdError: LoadAdError)
                {
                    mInterstitialAd = null
                    if (loadAdInterstitialRequests++ < 5)
                    {
                        loadFullScreenAd()
                    }
                }
            }
        )
    }

    //Hide navigation bar permanently
//    private fun fullScreenCall()
//    {
//        window.decorView.systemUiVisibility =
//            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//    }
}

