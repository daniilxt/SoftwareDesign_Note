package com.university.softwaredesign_note.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.university.softwaredesign_note.FourthViewModel
import com.university.softwaredesign_note.R

class FourthFragment : Fragment() {

    companion object {
        fun newInstance() =
            FourthFragment()
    }

    private lateinit var viewModel: FourthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fourth_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FourthViewModel::class.java)
        // TODO: Use the ViewModel
    }

}