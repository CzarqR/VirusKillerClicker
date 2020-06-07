package com.myniprojects.viruskiller.model

data class Bonus(
    var currLvl: Byte,
    val prices: IntArray,
    val values: Array<Number>
)
{
    val maxLvl
        get() = prices.size

    val currPrice
        get() = prices[currLvl.toInt()]
    val nextPrice
        get() = prices[currLvl.toInt() + 1]
    val currVal
        get() = values[currLvl.toInt()]
    val nextVal
        get() = values[currLvl.toInt() + 1]

}
