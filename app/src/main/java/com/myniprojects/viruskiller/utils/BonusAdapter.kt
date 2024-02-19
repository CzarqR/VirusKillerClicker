package com.myniprojects.viruskiller.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myniprojects.viruskiller.databinding.FragmentGameBinding
import com.myniprojects.viruskiller.databinding.LayoutBonusListItemBinding
import com.myniprojects.viruskiller.model.Bonus
import com.myniprojects.viruskiller.screens.shop.ShopViewModel

class BonusAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var items: List<Bonus> = ArrayList()
    lateinit var shopViewModel: ShopViewModel
    private lateinit var binding: FragmentGameBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        return BonusViewHolder(
            binding = LayoutBonusListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            shopViewModel = shopViewModel
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

    class BonusViewHolder(
        binding: LayoutBonusListItemBinding,
        private var shopViewModel: ShopViewModel
    ) : RecyclerView.ViewHolder(binding.root)
    {
        private val price: TextView = binding.txtPrice
        private val img: ImageView = binding.imgBonus
        private val upgrade: TextView = binding.txtUpdate
        private val name: TextView = binding.txtName

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