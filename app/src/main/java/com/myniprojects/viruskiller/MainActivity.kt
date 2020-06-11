package com.myniprojects.viruskiller

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.material.snackbar.Snackbar
import com.myniprojects.viruskiller.utils.App
import com.myniprojects.viruskiller.utils.Log
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : AppCompatActivity()
{
    private var isAdBannerLoaded = false
    private var loadAdBannerRequests = 0
    private var loadAdInterstitialRequests = 0
    private val animAd: Animation by lazy {
        AnimationUtils.loadAnimation(App.context, R.anim.zoom_ad)
    }
    lateinit var mainHandler: Handler
    private val updateTextTask = object : Runnable
    {
        override fun run()
        {
            animateAd()
            mainHandler.postDelayed(
                this,
                resources.getInteger(R.integer.ad_animation_time).toLong()
            )
        }
    }

    private fun animateAd()
    {
        Log.i("In")

        if (isAdBannerLoaded) //ad loaded, animate it
        {
            Log.i("Animate")
            adView.startAnimation(animAd)
        }
    }

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
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}

        // banner
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        adView.adListener = object : AdListener()
        {
            override fun onAdFailedToLoad(p0: Int)
            {
                Timber.d("Ad banner failed to load")
                super.onAdFailedToLoad(p0)
                if (loadAdBannerRequests++ < 5)
                {
                    adView.loadAd(adRequest)
                }
            }


            override fun onAdLoaded()
            {
                Timber.d("Ad banner loaded")
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
                Timber.wtf("Ad interstitial failed to load")
                super.onAdFailedToLoad(p0)
                if (loadAdInterstitialRequests++ < 5)
                {
                    mInterstitialAd.loadAd(AdRequest.Builder().build())
                }
            }


            override fun onAdLoaded()
            {
                Timber.wtf("Ad interstitial loaded")
                super.onAdLoaded()
                loadAdInterstitialRequests = 0
            }
        }


        Timber.plant(Timber.DebugTree())
        Log.i("onCreate Called i")
        mainHandler = Handler(Looper.getMainLooper())


        //RxJava bus test
        //RxBus.publish(RxEvent.EventAdWatched("Attack"))
    }


    //Hide navigation bar permanently
    private fun fullScreenCall()
    {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }

    // region Lifecycle Methods
    override fun onStart()
    {
        super.onStart()
        Log.i("onStart Called")
    }

    override fun onResume()
    {
        super.onResume()
        Log.i("onResume Called")
        mainHandler.post(updateTextTask)
        //fullScreenCall()
    }

    override fun onPause()
    {
        super.onPause()
        Log.i("onPause Called")
        mainHandler.removeCallbacks(updateTextTask)
    }

    override fun onStop()
    {
        super.onStop()
        Log.i("onStop Called")
    }

    override fun onDestroy()
    {
        super.onDestroy()
        Log.i("onDestroy Called")
    }

    override fun onRestart()
    {
        super.onRestart()
        Log.i("onRestart Called")
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)
        Log.i("onSavedInstance Called")
    }

    // endregion


}

