package com.university.softwaredesign_note.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.university.softwaredesign_note.R


class EditorFragment : Fragment() {

    companion object {
        fun newInstance() = EditorFragment()
    }

    private lateinit var viewModel: EditorViewModel

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
        toolbar.inflateMenu(R.menu.toolbar_menu)
        toolbar.setNavigationIcon(R.drawable.toolbar_nav__back)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.firstFragment1 -> {
                    Toast.makeText(requireContext(), "tap", Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
            false
        }

    }
}

