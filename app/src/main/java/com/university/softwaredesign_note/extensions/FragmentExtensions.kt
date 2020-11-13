package com.university.softwaredesign_note.extensions

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.university.softwaredesign_note.R

/**
 * Opens the bottom sheet in the container.
 *
 * @param fragment The fragment to fill in.
 * @param containerId Place to fill in.
 * @param isAnimated  Action with animation

 */
fun Fragment.showChildFragment(
    fragment: Fragment,
    containerId: Int,
    isAnimated: Boolean = false,
    animationRes: Int = R.anim.slide_from_bot
) {
    if (isAnimated) {
        childFragmentManager
            .beginTransaction()
            .setCustomAnimations(animationRes, android.R.anim.fade_out)
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    } else {
        childFragmentManager
            .beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }
}

/**
 * Hide the bottom sheet in the container.
 *
 * @param containerId Place of filled fragment
 * @param isAnimated  Action with animation
 */

fun Fragment.hideChildFragment(
    containerId: Int,
    isAnimated: Boolean = false,
    animateRes: Int = R.anim.slide_to_bot
) {
    if (isAnimated) {
        childFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_from_bot, animateRes)
            .replace(containerId, Fragment())
            .addToBackStack(null)

            .commit()
    } else {
        childFragmentManager
            .beginTransaction()
            .replace(containerId, Fragment())
            .addToBackStack(null)

            .commit()
    }
}

/**
 * Shows alert dialog.
 *
 * @param title Title of alert dialog
 * @param message Text on alert dialog
 * @param textBtn Text on complete button
 * @param action Action after on clicked button
 */
/*

fun Fragment.alertDialog(title: String, message: String, textBtn: String, action: () -> Unit) {

    val mDialog = AlertDialog.Builder(context, R.style.AlertDialogCustom)
    val dialog: AlertDialog = with(mDialog) {
        setTitle(title)
        setMessage(message)
        setCancelable(false)
        setPositiveButton(textBtn) { _: DialogInterface, _ ->
            action()
        }
        show()
    }
    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
        ContextCompat.getColor(
            requireContext(), R.color.camera_frg__dialog_ok
        )
    )
}*/
fun Fragment.animate(animationId: Int): Animation? = AnimationUtils.loadAnimation(
    this.requireContext(),
    animationId
)

fun Fragment.handleBackPressed(handler: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(
        viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handler()
            }
        })
}


fun Fragment.hideKeyBoard() {
    val view = requireActivity().currentFocus
    view?.let { v ->
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}