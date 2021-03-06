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
import com.university.softwaredesign_note.extensions.handleBackPressed
import com.university.softwaredesign_note.extensions.hideKeyBoard
import com.university.softwaredesign_note.extensions.showCustomDialog
import com.university.softwaredesign_note.firebase_db.FirebaseDB
import com.university.softwaredesign_note.helper.CiceroneHelper
import com.university.softwaredesign_note.models.DeleteableNote
import com.university.softwaredesign_note.models.Note
import com.university.softwaredesign_note.screens.Screens
import kotlinx.android.synthetic.main.editor_fragment.*
import kotlinx.android.synthetic.main.editor_fragment.view.*
import timber.log.Timber
import java.util.*


class EditorFragment : Fragment() {

    companion object {
        fun newInstance() = EditorFragment()
    }

    private lateinit var viewModel: EditorViewModel
    private var note: Note? = null
    private var textSize = 20f
    private var isDeletable = false

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

        handleBackPressed { saveNote() }
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
            saveNote()
        }
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.editor_toolbar__font_up -> {
                    if (textSize <= 100) {
                        textSize += 6
                        editor_frg__text.textSize = textSize
                    }
                }
                R.id.editor_toolbar__font_down -> {
                    if (textSize >= 26) {
                        textSize -= 6
                        editor_frg__text.textSize = textSize
                    }
                }
                R.id.editor_toolbar__delete -> {

                    val tmp = note

                    //todo refactor
                    if (tmp != null) {
                        showCustomDialog(
                                getString(R.string.dialog_frg__sure),
                                getString(R.string.dialog_frg__attention)
                        ) {
                            EventBus.send(
                                    DeleteableNote(
                                            tmp.id,
                                            tmp.noteText,
                                            tmp.title,
                                            tmp.liked,
                                            tmp.archived,
                                            tmp.private,
                                            tmp.date,
                                            tmp.dateEdit
                                    )
                            )
                            isDeletable = !isDeletable
                            requireActivity().onBackPressed()
                        }
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

    private fun saveNote() {
        hideKeyBoard()
        CiceroneHelper.router().backTo(Screens.HomeScreen())
        if (editor_frg__text.text.isNotEmpty()) {
            note?.noteText = editor_frg__text.text.toString()
            note?.title = createTitle()
            note?.dateEdit = Date().time
            if (isDeletable) {
                isDeletable = !isDeletable
                return  // leave from function
            }
            EventBus.send(note)
        }
    }

    //todo simplify
    private fun createTitle(): String {
        var itemTitle = editor_frg__title.text.toString()
        if (itemTitle.isEmpty()) {
            itemTitle = editor_frg__text.text.toString().substring(0, editor_frg__text.text.length)
            if (editor_frg__text.text.length >= 30) {
                itemTitle = editor_frg__text.text.toString().substring(0, 30)
            }
        }
        return itemTitle
    }
}

