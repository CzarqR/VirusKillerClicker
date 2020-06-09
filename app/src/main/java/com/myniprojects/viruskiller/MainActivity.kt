package com.myniprojects.viruskiller

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.myniprojects.viruskiller.generated.callback.OnClickListener
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : AppCompatActivity()
{


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
        mInterstitialAd.adListener = object : AdListener()
        {
            override fun onAdClosed()
            {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }


        Timber.plant(Timber.DebugTree())
        Timber.i("onCreate Called")


//        but_test.setOnClickListener {
//            mRewardedVideoAd.show()
//        }


        //RxJava bus test
        button2.setOnClickListener {
            Timber.i("Publish")
            //            RxBus.publish(RxEvent.EventAdWatched("Attack"))

            val onClickListener = View.OnClickListener {  _->
                Timber.i("Clicked !!!")
            }
            makeSnackbar(button2, "Text", "Action", onClickListener)

        }

    }


    private fun makeSnackbar(
        v: View,
        content: String,
        buttonText: String = "",
        onClickListener: View.OnClickListener? = null
    )
    {
//        val snackbar = Snackbar.make(
//            view, "Replace with your own action",
//            Snackbar.LENGTH_LONG
//        ).setAction("Action", View.OnClickListener {
//            Timber.i("Clicked")
//        })
//
//        snackbar.setActionTextColor(Color.YELLOW)
//
//
//        val snackbarView = snackbar.view
//        snackbarView.setBackgroundColor(Color.LTGRAY)
////
////        val textView =
////            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
////        textView.setTextColor(Color.BLUE)
////        textView.textSize = 28f
//
//        snackbar.show()

        val snack: Snackbar = Snackbar.make(v, content, Snackbar.LENGTH_LONG)
        if (onClickListener != null)
        {
            snack.setAction(buttonText, onClickListener)
        }
        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snack.show()

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

    // endregion


}
