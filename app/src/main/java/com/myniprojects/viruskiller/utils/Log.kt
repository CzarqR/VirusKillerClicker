package com.myniprojects.viruskiller.utils

import timber.log.Timber

class Log
{

    companion object
    {
        private const val prefix = "My debug:"
        fun d(text: Any)
        {
            Timber.d("$prefix $text")
        }

        fun i(text: Any)
        {
            Timber.i("$prefix $text")
        }
    }
}