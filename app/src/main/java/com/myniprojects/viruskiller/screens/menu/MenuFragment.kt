package com.myniprojects.viruskiller.screens.menu

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.myniprojects.viruskiller.R
import com.myniprojects.viruskiller.utils.Log
import com.myniprojects.viruskiller.utils.RxBus
import com.myniprojects.viruskiller.utils.RxEvent
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_menu.*
import timber.log.Timber

class MenuFragment : Fragment()
{
    private lateinit var adDisposable: Disposable
    private lateinit var viewModel: MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {

        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        butHelp.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.menu_to_help)
        )

        butPlay.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.menu_to_game)
        )
//        butPlay.setOnClickListener {
//            makeSnackbar(it)
//        }

        adDisposable = RxBus.listen(RxEvent.EventAdWatched::class.java).subscribe() {
            Log.i("Listen")
            Log.i(it.adName)
        }

    }

    override fun onDestroy()
    {
        super.onDestroy()
        if (!adDisposable.isDisposed)
        {
            adDisposable.dispose()
        }
    }

//    private fun makeSnackbar(view: View)
//    {
//        val snackbar = Snackbar.make(
//            view, "Replace with your own action",
//            Snackbar.LENGTH_LONG
//        ).setAction("Action", View.OnClickListener {
//            Log.i("Clicked")
//        })
//
//        snackbar.setActionTextColor(Color.YELLOW)
//
//
//        val snackbarView = snackbar.view
//        snackbarView.setBackgroundColor(Color.LTGRAY)
////
////        val textView =
////            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
////        textView.setTextColor(Color.BLUE)
////        textView.textSize = 28f
//
//        snackbar.show()
//    }

}