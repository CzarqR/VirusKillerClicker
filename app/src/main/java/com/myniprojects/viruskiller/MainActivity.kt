package com.myniprojects.viruskiller

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber


class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())
        Timber.i("onCreate Called")

    }

    //Hide navigation bar permanently
    private fun fullScreenCall()
    {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
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
