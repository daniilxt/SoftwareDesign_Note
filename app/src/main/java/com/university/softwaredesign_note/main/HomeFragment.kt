package com.university.softwaredesign_note.main

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.university.softwaredesign_note.R
import com.university.softwaredesign_note.bus.Event
import com.university.softwaredesign_note.bus.EventBus
import com.university.softwaredesign_note.models.Note
import com.university.softwaredesign_note.ui.notes.FirstFragmentViewModel
import com.university.softwaredesign_note.ui.notes.NotesFragment
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.lang.Exception

class HomeFragment : Fragment() {
    companion object {
        fun newInstance() =
            HomeFragment()
    }

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var viewModel: FirstFragmentViewModel
    private var stateNavigation: Int = R.id.firstFragment

    private var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("ON CREATE VIEW")
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        Timber.i("ON VIEW CREATED")
        disposable = EventBus.get().subscribe { obj ->
            when (obj) {
                Event.HIDE_BUTTON -> {
                    try {
                        val slideUp: Animation =
                            AnimationUtils.loadAnimation(this.requireContext(), R.anim.slide_to_bot)

                        if (bottomNavigationView.visibility == View.VISIBLE) {
                            bottomNavigationView.visibility = View.GONE
                            bottomNavigationView.startAnimation(slideUp)
                        }
                    } catch (e: Exception) {
                        Timber.i("Exception $e")
                    }
                }
                Event.SHOW_BUTTON -> {
                    try {

                        val slideUp: Animation =
                            AnimationUtils.loadAnimation(
                                this.requireContext(),
                                R.anim.slide_from_bot
                            )

                        if (bottomNavigationView.visibility == View.GONE) {
                            bottomNavigationView.visibility = View.VISIBLE
                            bottomNavigationView.startAnimation(slideUp)
                        }
                    } catch (e: Exception) {
                        Timber.i("Exception $e")
                    }
                }
                is Note -> {
                    viewModel.saveNote(obj)
                    Timber.i("&&& ${obj}")
                }
            }
        }
        val colors = intArrayOf(
            ContextCompat.getColor(requireContext(), android.R.color.holo_green_dark),
            ContextCompat.getColor(requireContext(), android.R.color.holo_blue_dark)
        )
        val csl = ColorStateList(arrayOf(IntArray(0)), intArrayOf(colors[0]))

        bottomNavigationView = requireActivity().findViewById(R.id.nav_view)
        val navController = requireActivity().findNavController(R.id.main_activity__container)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnNavigationItemSelectedListener {

            when (it.itemId) {

                R.id.firstFragment -> {
                    //allow don't run fun twice
                    if (stateNavigation != R.id.firstFragment) {
                        viewModel.list()
                        stateNavigation = R.id.firstFragment
                    }
                }
                R.id.secondFragment -> {

                }
                R.id.thirdFragment -> {
                    if (stateNavigation != R.id.secondFragment) {
                        viewModel.filterByLike()
                        stateNavigation = R.id.secondFragment
                    }
                }
                R.id.fourthFragment -> {
                    if (stateNavigation != R.id.fourthFragment) {
                        viewModel.filterByArchive()
                        stateNavigation = R.id.fourthFragment
                    }
                }
                else -> {
                    viewModel.add()
                    Toast.makeText(requireContext(), "fab", Toast.LENGTH_SHORT).show()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        bottomNavigationView.itemRippleColor = csl
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("ON  DESTROYED")
        if (disposable?.isDisposed == false) {
            disposable?.dispose()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(FirstFragmentViewModel::class.java)
    }
}