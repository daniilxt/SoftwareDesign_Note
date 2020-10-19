package com.university.softwaredesign_note.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.university.softwaredesign_note.R
import com.university.softwaredesign_note.bus.EventBus
import com.university.softwaredesign_note.models.Note
import kotlinx.android.synthetic.main.editor_fragment.*
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

        if (arguments != null) {
            note = requireArguments().getParcelable("note")
            editor_frg__text.setText(note?.noteText)
            editor_frg__title.setText(note?.title)
        }
        val toolbar: androidx.appcompat.widget.Toolbar =
            requireActivity().findViewById(R.id.editor_frg__toolbar)
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
                else -> {
                }
            }
            false
        }
    }
}

