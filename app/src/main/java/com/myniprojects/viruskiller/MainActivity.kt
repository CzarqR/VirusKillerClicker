package com.myniprojects.viruskiller

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.*


class MainActivity : AppCompatActivity()
{

    companion object
    {
        private lateinit var mInterstitialAd: InterstitialAd
        fun showAd()
        {
            if (mInterstitialAd.isLoaded)
            {
                mInterstitialAd.show()
            }
            else
            {
                Timber.d("The interstitial wasn't loaded yet.")
            }
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

        // interstitial
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.fullscreen_ad)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }


        Timber.plant(Timber.DebugTree())
        Timber.i("onCreate Called")
    }


    //Hide navigation bar permanently
    private fun fullScreenCall()
    {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }

    /** Lifecycle Methods **/
    override fun onStart()
    {
        super.onStart()
        Timber.i("onStart Called")
    }

    override fun onResume()
    {
        super.onResume()
        Timber.i("onResume Called")
        //fullScreenCall()
    }

    override fun onPause()
    {
        super.onPause()
        Timber.i("onPause Called")
    }

    override fun onStop()
    {
        super.onStop()
        Timber.i("onStop Called")
    }

    override fun onDestroy()
    {
        super.onDestroy()
        Timber.i("onDestroy Called")
    }

    override fun onRestart()
    {
        super.onRestart()
        Timber.i("onRestart Called")
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)
        Timber.i("onSavedInstance Called")
    }
}
