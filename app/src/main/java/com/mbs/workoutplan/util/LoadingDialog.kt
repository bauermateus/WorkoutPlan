package com.mbs.workoutplan.util

import android.app.AlertDialog
import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mbs.workoutplan.R

class LoadingDialog(context: Context, val view: View?) {

    private val alertView = View.inflate(context, R.layout.dialog_loading, null)
    private val dialog =
        AlertDialog.Builder(context).setView(alertView).setCancelable(false).create()

    private var isVisible = false

    init {
        init()
    }

    private fun init() {
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        view?.findNavController()?.addOnDestinationChangedListener { _, _, _ -> dismiss() }

    }

    fun show() {
        if (!isVisible) {
            isVisible = true
            view?.isVisible = !isVisible
            dialog.show()
        }
    }

    fun dismiss() {
        dialog.dismiss()
        isVisible = false
        view?.isVisible = !isVisible
    }
}

fun Fragment.initLoading(view: View?, context: Context? = null): LoadingDialog {
    return LoadingDialog(context = context ?: requireContext(), view = view)
}