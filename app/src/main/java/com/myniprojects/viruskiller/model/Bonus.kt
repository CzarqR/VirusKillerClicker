package com.myniprojects.viruskiller.model

data class Bonus(
    val maxLvl: Byte,
    var currLvl: Byte,
    val prices: Array<Int>

)
