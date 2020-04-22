package com.myniprojects.viruskiller.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myniprojects.viruskiller.R
import com.myniprojects.viruskiller.model.Bonus
import kotlinx.android.synthetic.main.layout_bonus_list_item.view.*
import timber.log.Timber

class BonusAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var items: List<Bonus> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        return BonusViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_bonus_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        when (holder)
        {
            is BonusViewHolder ->
            {
                holder.bind(items.get(position), position)
            }
        }
    }

    override fun getItemCount(): Int
    {
        return items.size
    }

    fun initList(bonusList: List<Bonus>)
    {
        items = bonusList

    }

    class BonusViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView)
    {
        private val currLvl: TextView = itemView.txtCurrent
        private val maxLvl: TextView = itemView.txtMax
        private val price: TextView = itemView.txtPrice
        private val upgrade: TextView = itemView.butUpgrade

        fun bind(bonus: Bonus, position: Int)
        {
            currLvl.text = bonus.currLvl.toString()
            maxLvl.text = bonus.maxLvl.toString()
            price.text = bonus.prices[bonus.currLvl.toInt()].toString()
            upgrade.setOnClickListener {
                Timber.i("Clicked update pos $position")
            }
        }
    }

}