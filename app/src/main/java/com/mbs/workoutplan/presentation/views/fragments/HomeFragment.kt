package com.mbs.workoutplan.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mbs.workoutplan.data.db.models.WorkoutDTO
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
        WorkoutAdapter(::onWorkoutClick, ::onWorkoutDelete)
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

    override fun onResume() {
        super.onResume()
        binding.emptyListText.isVisible = false
        viewModel.getUserWorkouts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            binding.emptyListText.isVisible = state?.workouts.isNullOrEmpty()
            state.workouts?.let {
                workoutsAdapter.submitList(it)
            }
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
                binding.pb.isVisible = false
            }

            is HomeEvent.Success -> {
                binding.pb.isVisible = false
            }

            is HomeEvent.Deleted -> {
                toast("Treino removido.")
                viewModel.getUserWorkouts()
            }
        }
        viewModel.clearEvents()
    }

    private fun onWorkoutClick(workout: WorkoutDTO) {
        workout.identifier?.let {
            findNavController().navigate(
                HomeFragmentDirections
                    .actionHomeFragmentToWorkoutDetailsFragment(it)
            )
        }
    }

    private fun onWorkoutDelete(id: String) {
        viewModel.deleteWorkout(id)
    }

    private fun setup() {
        binding.recycler.adapter = workoutsAdapter
    }

}