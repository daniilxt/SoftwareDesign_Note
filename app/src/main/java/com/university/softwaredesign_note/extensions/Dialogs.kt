package com.university.softwaredesign_note.extensions

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.university.softwaredesign_note.R

fun Fragment.showCustomDialog(title: String, text: String, actionAfter: () -> Unit) {
    //inflate view
    val mDialogView = LayoutInflater.from(requireContext())
        .inflate(R.layout.custom_doalog, null)

    val mBuilder = AlertDialog.Builder(requireContext())
        .setView(mDialogView)
    val mAlertDialog = mBuilder.show()

    mDialogView.findViewById<TextView>(R.id.dialog_frg__title).text = title
    mDialogView.findViewById<TextView>(R.id.dialog_frg__text).text = text

    mDialogView.findViewById<TextView>(R.id.dialog_frg__no_btn)
        .setOnClickListener {
            mAlertDialog.dismiss()
        }

    mDialogView.findViewById<TextView>(R.id.dialog_frg__yes_btn)
        .setOnClickListener {
            actionAfter()
            mAlertDialog.dismiss()
        }
    mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    mAlertDialog.setCancelable(false)
}