package com.university.softwaredesign_note.main

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.university.softwaredesign_note.R
import com.university.softwaredesign_note.bus.Event
import com.university.softwaredesign_note.bus.EventBus
import com.university.softwaredesign_note.extensions.animate
import com.university.softwaredesign_note.helper.CiceroneHelper
import com.university.softwaredesign_note.main.notes.FirstFragmentViewModel
import com.university.softwaredesign_note.models.DeleteableNote
import com.university.softwaredesign_note.models.Note
import com.university.softwaredesign_note.screens.Screens
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber


class HomeFragment : Fragment() {
    companion object {
        fun newInstance() =
                HomeFragment()

        private const val BOTTOM_CONTAINER_ID: Int = R.id.home_frg__bottom_container
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
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    fun disableSearchEditText() {
        editor_search__text.isEnabled = false
        editor_search__text.visibility = View.INVISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initToolbar()

        editor_search__text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.search(editor_search__text.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //при добавлении новой заявки пребрасывает на первый фрагмент, неомходи для поиска
        stateNavigation = R.id.firstFragment

        disableSearchEditText()

        Timber.i("ON VIEW CREATED")
        disposable = EventBus.get().subscribe { obj ->
            when (obj) {
                Event.HIDE_BUTTON -> {
                    try {
                        if (bottomNavigationView.visibility == View.VISIBLE) {
                            bottomNavigationView.visibility = View.GONE
                            bottomNavigationView.startAnimation(animate(R.anim.slide_to_bot))


                        }
                    } catch (e: Exception) {
                        Timber.i("Exception $e")
                    }
                }
                Event.SHOW_BUTTON -> {
                    try {
                        if (bottomNavigationView.visibility == View.GONE) {
                            bottomNavigationView.visibility = View.VISIBLE
                            bottomNavigationView.startAnimation(animate(R.anim.slide_from_bot))
                        }
                    } catch (e: Exception) {
                        Timber.i("Exception $e")
                    }
                }
                is Note -> {
                    viewModel.saveNote(obj)
                    Timber.i("&&& ${obj}")
                }

                is DeleteableNote -> {
                    viewModel.delete(
                            Note(
                                    obj.id,
                                    obj.noteText,
                                    obj.title,
                                    obj.liked,
                                    obj.archived,
                                    obj.private,
                                    obj.date,
                                    obj.dateEdit
                            )
                    )
                    Timber.i("DELETE NOTE: $obj")
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
                    Timber.i("GETTTT NOTES")
                    //allow don't run fun twice
                    if (stateNavigation != R.id.firstFragment) {
                        disableSearchEditText()
                        viewModel.list()
                        stateNavigation = R.id.firstFragment
                    }
                }
                R.id.secondFragment -> {
                        viewModel.search(editor_search__text.text.toString())
                        editor_search__text.isEnabled = true
                        editor_search__text.visibility = View.VISIBLE
                        stateNavigation = R.id.secondFragment

                }
                R.id.thirdFragment -> {
                    if (stateNavigation != R.id.thirdFragment) {
                        disableSearchEditText()
                        viewModel.filterByLike()
                        stateNavigation = R.id.thirdFragment
                    }
                }
                R.id.fourthFragment -> {
                    if (stateNavigation != R.id.fourthFragment) {
                        disableSearchEditText()
                        viewModel.filterByArchive()
                        stateNavigation = R.id.fourthFragment
                    }
                }
                else -> {
                    val note = Note.createNote()
                    CiceroneHelper.router().navigateTo(Screens.EditorScreen(note))
                    viewModel.add(note)
                    viewModel.list()
                    Toast.makeText(requireContext(), "fab", Toast.LENGTH_SHORT).show()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

        bottomNavigationView.setSelectedItemId(stateNavigation)
        bottomNavigationView.itemRippleColor = csl
    }


    private fun initToolbar() {
        val toolbar: androidx.appcompat.widget.Toolbar =
                requireActivity().findViewById(R.id.home_frg__toolbar)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.home_frg__toolbar_user -> {
/*                    val tmp = 1
                    if (tmp ==1) {
                        showChildFragment(ProfileModalBottomSheet(), BOTTOM_CONTAINER_ID, true)
                    }
                    else {
                        showChildFragment(ProfileModalBottomSheet(), BOTTOM_CONTAINER_ID, true)
                    }*/
                    val auth = FirebaseAuth.getInstance()
                    Toast.makeText(requireContext(), auth.currentUser?.email, Toast.LENGTH_LONG).show()
                }
            }
            false
        }
    }

    override fun onResume() {
        super.onResume()
        bottomNavigationView.setSelectedItemId(stateNavigation)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("ON  DESTROYED")
        if (disposable?.isDisposed == false) {
            disposable?.dispose()
        }
    }

    private fun initViewModel() {
        viewModel =
                ViewModelProvider(requireActivity()).get(FirstFragmentViewModel::class.java)
    }
}