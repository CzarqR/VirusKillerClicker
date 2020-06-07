package com.myniprojects.viruskiller.utils

import android.app.Application
import android.content.Context
import timber.log.Timber


class App : Application()
{
    override fun onCreate()
    {
        super.onCreate()
        mContext = this
    }

    companion object
    {
        private var mContext: Context? = null
        val context: Context?
            get()
            = mContext
    }
}