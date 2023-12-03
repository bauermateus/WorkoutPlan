package com.mbs.workoutplan.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mbs.workoutplan.data.db.models.Workout
import com.mbs.workoutplan.databinding.FragmentHomeBinding
import com.mbs.workoutplan.presentation.event.HomeEvent
import com.mbs.workoutplan.presentation.viewmodels.HomeViewModel
import com.mbs.workoutplan.presentation.views.adapters.WorkoutAdapter
import com.mbs.workoutplan.util.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()

    private val workoutsAdapter by lazy {
        WorkoutAdapter(::onWorkoutClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        onClick()
        setup()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.workouts?.let { workoutsAdapter.submitList(state.workouts) }
            state.event?.let { processEvents(it) }
        }
    }

    private fun onClick() {
        with(binding) {
            fab.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections
                        .actionHomeFragmentToCreateWorkoutFragment()
                )
            }
        }
    }

    private fun processEvents(event: HomeEvent) {
        when (event) {
            is HomeEvent.Error -> {
                toast(event.msg)
            }
        }
    }

    private fun onWorkoutClick(workout: Workout) {
        //
    }

    private fun setup() {
        binding.recycler.adapter = workoutsAdapter
    }

}