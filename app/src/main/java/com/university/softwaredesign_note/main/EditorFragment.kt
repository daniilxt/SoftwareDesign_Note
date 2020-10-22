package com.university.softwaredesign_note.main

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.university.softwaredesign_note.R
import com.university.softwaredesign_note.bus.EventBus
import com.university.softwaredesign_note.models.DeleteableNote
import com.university.softwaredesign_note.models.Note
import kotlinx.android.synthetic.main.editor_fragment.*
import kotlinx.android.synthetic.main.editor_fragment.view.*
import timber.log.Timber


class EditorFragment : Fragment() {

    companion object {
        fun newInstance() = EditorFragment()
    }

    private lateinit var viewModel: EditorViewModel
    private var note: Note? = null
    private var textSize = 20f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.editor_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // get current fragment
        this.onResume()
        Toast.makeText(requireContext(), "CJANEGD", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: androidx.appcompat.widget.Toolbar =
            requireActivity().findViewById(R.id.editor_frg__toolbar)

        if (arguments != null) {
            note = requireArguments().getParcelable("note")
            editor_frg__text.setText(note?.noteText)
            editor_frg__title.setText(note?.title)
            if (note!!.liked) {
                //get like position in toolbar
                toolbar.menu.getItem(4)
                    .setIcon(R.drawable.bottom_nav__like_filled)
            }
        }
        toolbar.setNavigationOnClickListener {
            if (editor_frg__text.text.isNotEmpty()) {
                note?.noteText = editor_frg__text.text.toString()
                note?.title = editor_frg__title.text.toString()
                EventBus.send(note)
                Timber.i("&&& $note")
            }
            requireActivity().onBackPressed()
        }
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.editor_toolbar__font_up -> {
                    if (textSize <= 100) {
                        textSize += 6
                        editor_frg__text.textSize = textSize
                        Toast.makeText(
                            requireContext(),
                            "Size is ${textSize}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                R.id.editor_toolbar__font_down -> {
                    if (textSize >= 26) {
                        textSize -= 6
                        editor_frg__text.textSize = textSize
                        Toast.makeText(
                            requireContext(),
                            "Size is ${textSize}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                R.id.editor_toolbar__delete -> {

                    val tmp = note

                    //todo refactor
                    if (tmp != null) {
                        //inflate view
                        val mDialogView = LayoutInflater.from(requireContext())
                            .inflate(R.layout.custom_doalog, null)

                        //builder dialog
                        val mBuilder = AlertDialog.Builder(requireContext())
                            .setView(mDialogView)
                        val mAlertDialog = mBuilder.show()

                        mDialogView.findViewById<TextView>(R.id.dialog_frg__title).text =
                            getString(R.string.dialog_frg__sure)
                        mDialogView.findViewById<TextView>(R.id.dialog_frg__text).text =
                            getString(R.string.dialog_frg__attention)

                        mDialogView.findViewById<TextView>(R.id.dialog_frg__no_btn)
                            .setOnClickListener {
                                mAlertDialog.dismiss()
                            }

                        mDialogView.findViewById<TextView>(R.id.dialog_frg__yes_btn)
                            .setOnClickListener {
                                EventBus.send(
                                    DeleteableNote(
                                        tmp.id,
                                        tmp.noteText,
                                        tmp.title,
                                        tmp.liked,
                                        tmp.archived,
                                        tmp.private
                                    )
                                )
                                mAlertDialog.dismiss()
                                requireActivity().onBackPressed()
                            }
                        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        mAlertDialog.setCancelable(false)

                        //login button click of custom layout

                    }
                }

                R.id.editor_toolbar__like -> {
                    item.setIcon(R.drawable.bottom_nav__like_filled)
                    when (note?.liked) {
                        true -> {
                            item.setIcon(R.drawable.bottom_nav__like)
                        }
                    }
                    //change liked/unliked
                    note?.apply { liked = !liked }
                }
                R.id.editor_toolbar__share -> {

                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, note?.noteText)
                    val choseIntent =
                        Intent.createChooser(intent, note?.title)
                    startActivity(choseIntent)
                }
                else -> {
                }
            }
            false
        }
    }
}

