package com.myniprojects.viruskiller.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myniprojects.viruskiller.R
import com.myniprojects.viruskiller.model.Bonus
import com.myniprojects.viruskiller.screens.shop.ShopViewModel
import kotlinx.android.synthetic.main.layout_bonus_list_item.view.*

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

                holder.bind(items[position])
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
        itemView: View, private var shopViewModel: ShopViewModel
    ) : RecyclerView.ViewHolder(itemView)
    {
        private val price: TextView = itemView.txtPrice
        private val img: ImageView = itemView.imgBonus
        private val upgrade: TextView = itemView.txtUpdate
        private val name: TextView = itemView.txtName

        fun bind(bonus: Bonus)
        {

            img.setImageResource(bonus.image)
            price.text = bonus.currPriceString
            upgrade.text = bonus.updateString
            name.text = bonus.name

            price.setOnClickListener {

                if (bonus.isMax) //lvl is max cannot update
                {
                    Log.i("Lvl is max cannot update")
                }
                else
                {
                    if (bonus.currPrice <= shopViewModel.mon)
                    {
                        Log.i("Updating")
                        val cost = bonus.currPrice
                        bonus.currLvl = bonus.currLvl.plus(1).toByte()

                        price.text = bonus.currPriceString
                        upgrade.text = bonus.updateString


                        shopViewModel.saveBonuses(cost)
                    }
                    else
                    {
                        Log.i("Cannot update, not enough money")
                    }
                }

            }
        }




    }
}