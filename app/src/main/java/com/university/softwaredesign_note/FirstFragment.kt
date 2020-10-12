package com.university.softwaredesign_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.university.softwaredesign_note.adapters.MainRecyclerAdapter
import com.university.softwaredesign_note.adapters.OnItemClickListener
import com.university.softwaredesign_note.bus.Event
import com.university.softwaredesign_note.bus.EventBus
import kotlinx.android.synthetic.main.first_fragment.*

class FirstFragment : Fragment() {
    private lateinit var itemAdapter: MainRecyclerAdapter

    companion object {
        fun newInstance() = FirstFragment()
    }

    private lateinit var viewModel: FirstFragmentViewModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(FirstFragmentViewModel::class.java)
        viewModel.getNotes().observe(viewLifecycleOwner, Observer {
            itemAdapter.bind(it)
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.first_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        first_frg__recycler.layoutManager = LinearLayoutManager(requireContext())
        itemAdapter = MainRecyclerAdapter(object : OnItemClickListener {
            override fun onItemClicked(position: Int, item: Any) {
                Toast.makeText(requireContext(), "Item is $position", Toast.LENGTH_SHORT).show()
            }
        })
        first_frg__recycler.adapter = itemAdapter
        first_frg__recycler.setOnScrollChangeListener { _, _, _, _, oldScrollY ->
            if (oldScrollY < 0) EventBus.send(Event.HIDE_BUTTON) else EventBus.send(Event.SHOW_BUTTON)
        }
        first_frg__add_btn.setOnClickListener {
            viewModel.add()
        }
    }
}