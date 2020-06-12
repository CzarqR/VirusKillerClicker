package com.myniprojects.viruskiller.model

import android.content.res.Resources
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import com.myniprojects.viruskiller.R
import com.myniprojects.viruskiller.utils.App
import com.myniprojects.viruskiller.utils.Log
import timber.log.Timber

data class Bonus(
    var currLvl: Byte,
    val prices: IntArray,
    val values: Array<Number>,
    val name: String,
    val desc: String,
    val image: Int
)
{
    val isMax: Boolean
        get() = currLvl == maxLvl

    private val maxLvl: Byte
        get() = prices.size.toByte()

    val currPrice: Int
        get()
        {
            return if (isMax)
            {
                -1 //Max lvl
            }
            else
            {
                prices[currLvl.toInt()]
            }
        }

    val currVal
        get() = values[currLvl.toInt()]

    private val nextVal: Number
        get()
        {
            return if (currLvl == maxLvl)
            {
                -1 //Max lvl
            }
            else
            {
                values[currLvl.toInt() + 1]
            }

        }

    val currValString
        get() = values[currLvl.toInt()].toString()

    val nextValString: String
        get()
        {
            return if (nextVal.toDouble() < 0)
            {
                App.context!!.getString(R.string.max_lvl)
            }
            else
            {
                nextVal.toString()
            }
        }


    val currLvlString: String
        get() = currLvl.toString()


    val maxLvlString
        get() = maxLvl.toString()

    val currPriceString: String
        get()
        {
            return if (currPrice < 0)
            {
                App.context!!.getString(R.string.max_lvl)
            }
            else
            {
                "$currPrice $"
            }
        }

    val updateString: String?
        get()
        {
            return if (isMax)
            {
                App.context?.getString(R.string.max_lvl_format, currValString )
            }
            else
            {
                App.context?.getString(R.string.update_format, currValString, nextValString)
            }

        }



}


