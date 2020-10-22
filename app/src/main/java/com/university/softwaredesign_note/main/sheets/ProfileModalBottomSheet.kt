package com.university.softwaredesign_note.main.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.university.softwaredesign_note.R
import com.university.softwaredesign_note.extensions.handleBackPressed
import kotlinx.android.synthetic.main.profile_frg__modal_sheet.*
import timber.log.Timber

class ProfileModalBottomSheet : Fragment() {

    private lateinit var sheetBehavior: BottomSheetBehavior<View>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.profile_frg__modal_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSheetBehavior()
        handleBackPressed {
            if (sheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED || sheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileModalBottomSheet()
    }

    private fun setSheetBehavior() {
        sheetBehavior = BottomSheetBehavior.from(help_frg__scroll)
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Timber.i("SLIDE ${sheetBehavior.state}")
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                Timber.i("CNG STATE ${sheetBehavior.state}")
                when (newState) {
                    else -> {
                    }
                }
            }
        })
    }
}
