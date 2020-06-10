package com.myniprojects.viruskiller.utils

import timber.log.Timber

class Log
{

    companion object
    {
        private const val prefix = "My debug:"
        fun d(text: String)
        {
            Timber.d("$prefix $text")
        }

        fun i(text: String)
        {
            Timber.i("$prefix $text")
        }
    }
}