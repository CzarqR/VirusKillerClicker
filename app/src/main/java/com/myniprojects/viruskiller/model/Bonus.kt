package com.myniprojects.viruskiller.model

data class Bonus(
    var currLvl: Byte,
    val prices: IntArray,
    val values: Array<Number>
)
{
    fun currPrice() = prices[currLvl.toInt()]
    fun nextPrice() = prices[currLvl.toInt() + 1]
    fun currVal() = values[currLvl.toInt()]
    fun nextVal() = values[currLvl.toInt() + 1]

}
