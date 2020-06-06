package com.myniprojects.viruskiller.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myniprojects.viruskiller.R
import com.myniprojects.viruskiller.model.Bonus
import com.myniprojects.viruskiller.screens.shop.ShopViewModel
import kotlinx.android.synthetic.main.layout_bonus_list_item.view.*
import timber.log.Timber

class BonusAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var items: List<Bonus> = ArrayList()
    lateinit var shopViewModel: ShopViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {

        return BonusViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_bonus_list_item, parent, false), shopViewModel
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        when (holder)
        {
            is BonusViewHolder ->
            {

                holder.bind(items[position], position)
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
        itemView: View, var shopViewModel: ShopViewModel
    ) : RecyclerView.ViewHolder(itemView)
    {
        private val currLvl: TextView = itemView.txtCurrent
        private val maxLvl: TextView = itemView.txtMax
        private val price: TextView = itemView.txtPrice
        private val upgrade: TextView = itemView.butUpgrade
        private val currValue: TextView = itemView.txtCurrentValue
        private val nextValue: TextView = itemView.txtNextValue


        fun bind(bonus: Bonus, position: Int)
        {
            currLvl.text = bonus.currLvl.toString()
            maxLvl.text = bonus.prices.size.toString()
            price.text = bonus.prices[bonus.currLvl.toInt()].toString()
            currValue.text = bonus.currVal().toString()
            nextValue.text = bonus.nextVal().toString()

            upgrade.setOnClickListener {
                Timber.i("Clicked update pos $position")
                Timber.i("Current lvl ${bonus.currLvl}")
                Timber.i("Price current ${bonus.currPrice()}")
                Timber.i("Price next ${bonus.nextPrice()}")
                Timber.i("Value curr ${bonus.currVal()}")
                Timber.i("Value next ${bonus.nextVal()}")

                if (bonus.currPrice() < shopViewModel.Money)
                {
                    Timber.i("Updating")
                    shopViewModel.update(bonus.currPrice())
                    bonus.currLvl = bonus.currLvl.plus(1).toByte()
                    currLvl.text = (bonus.currLvl).toString()
                    price.text = bonus.currPrice().toString()
                    currValue.text = bonus.currVal().toString()
                    nextValue.text = bonus.nextVal().toString()
                }
                else
                {
                    Timber.i("Cannot update")
                }

            }
        }
    }

}