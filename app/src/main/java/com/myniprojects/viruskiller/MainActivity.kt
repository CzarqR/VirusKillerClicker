package com.myniprojects.viruskiller

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
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
        private lateinit var mInterstitialAd: InterstitialAd
        fun showInterstitialAd()
        {
            if (mInterstitialAd.isLoaded)
            {
                mInterstitialAd.show()
            }
            else
            {
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
            override fun onAdFailedToLoad(p0: Int)
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
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.fullscreen_ad)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object : AdListener()
        {
            override fun onAdClosed()
            {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }

            override fun onAdFailedToLoad(p0: Int)
            {
                super.onAdFailedToLoad(p0)
                if (loadAdInterstitialRequests++ < 5)
                {
                    mInterstitialAd.loadAd(AdRequest.Builder().build())
                }
            }


            override fun onAdLoaded()
            {
                super.onAdLoaded()
                loadAdInterstitialRequests = 0
            }
        }


        Timber.plant(Timber.DebugTree())

        binding.adView.startAnimation(animAd)

        //RxJava bus test
        //RxBus.publish(RxEvent.EventAdWatched("Attack"))
    }

    //Hide navigation bar permanently
//    private fun fullScreenCall()
//    {
//        window.decorView.systemUiVisibility =
//            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//    }
}

