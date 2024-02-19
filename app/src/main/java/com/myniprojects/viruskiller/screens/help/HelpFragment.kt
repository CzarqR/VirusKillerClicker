package com.myniprojects.viruskiller.screens.help

import android.app.AlertDialog
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.myniprojects.viruskiller.R
import com.myniprojects.viruskiller.databinding.FragmentHelpBinding

class HelpFragment : Fragment()
{
    private lateinit var binding: FragmentHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_help,
            container,
            false
        )

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val animationDrawable = binding.back.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(0)
        animationDrawable.setExitFadeDuration(resources.getInteger(R.integer.background_animation_frame))
        animationDrawable.start()

        //region info click help

        binding.butBaseInfo.setOnClickListener {
            showDialog(R.string.base_info, R.string.base_info_desc)
        }

        binding.butAttacs.setOnClickListener {
            showDialog(R.string.attacks, R.string.attacks_desc)
        }

        binding.butKillingVirus.setOnClickListener {
            showDialog(R.string.killing_virus, R.string.killing_virus_desc)
        }

        binding.butSavedLives.setOnClickListener {
            showDialog(R.string.saved_lives, R.string.saved_lives_desc)
        }

        binding.butStorage.setOnClickListener {
            showDialog(R.string.storage, R.string.storage_desc_help)
        }

        binding.butAd.setOnClickListener {
            showDialog(R.string.ads, R.string.ads_desc)
        }

        binding.butCredits.setOnClickListener {
            showDialog(R.string.credits, R.string.credits_desc)
        }

        //endregion

        super.onViewCreated(view, savedInstanceState)
    }


    private fun showDialog(title: Int, text: Int, butText: Int = R.string.ok)
    {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle(requireContext().getString(title))
        alertDialog.setMessage(requireContext().getString(text))
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, requireContext().getString(butText))
        { dialog, _ ->
            dialog.dismiss()
        }

        alertDialog.setIcon(R.drawable.ic_baseline_info_24)
        alertDialog.window?.setBackgroundDrawableResource(R.drawable.gradient_background_msg)
        alertDialog.show()
    }

}
