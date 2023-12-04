package com.mbs.workoutplan.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mbs.workoutplan.data.db.models.ExerciseDTO
import com.mbs.workoutplan.databinding.FragmentWorkoutDetailsBinding
import com.mbs.workoutplan.presentation.event.WorkoutDetailsEvent
import com.mbs.workoutplan.presentation.viewmodels.WorkoutDetailsViewModel
import com.mbs.workoutplan.presentation.views.adapters.ExerciseAdapter
import com.mbs.workoutplan.util.initLoading
import com.mbs.workoutplan.util.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

class WorkoutDetailsFragment : Fragment() {

    private var _binding: FragmentWorkoutDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WorkoutDetailsViewModel by viewModel()
    private val loading by lazy {
        initLoading(binding.root)
    }

    private val adapter by lazy {
        ExerciseAdapter(::onDeleteClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        observe()
    }

    override fun onResume() {
        super.onResume()
        binding.emptyListText.isVisible = false
        viewModel.getWorkoutInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setup() {
        binding.exercisesRecycler.adapter = adapter
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.fab.setOnClickListener {
            viewModel.currentUiState.workout?.identifier?.let {
                findNavController().navigate(
                    WorkoutDetailsFragmentDirections
                        .actionWorkoutDetailsFragmentToNewExerciseFragment(it)
                )
            }
        }
    }

    private fun observe() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            with(binding) {
                binding.emptyListText.isVisible = state.workout?.exercises.isNullOrEmpty()
                state.workout?.name?.let { name.text = it }
                state.workout?.exercises?.let {
                    adapter.submitList(it)
                }
                state.workout?.description?.let { description.text = it }
                state.workout?.date?.let {
                    createdAt.text = SimpleDateFormat(
                        "dd/MM/yyyy, HH:mm",
                        Locale.getDefault()
                    ).format(it.toDate())
                }
                state.event?.let { processEvents(it) }
            }
        }
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) loading.show()
            else loading.dismiss()
        }
    }

    private fun processEvents(event: WorkoutDetailsEvent) {
        when (event) {
            is WorkoutDetailsEvent.DeleteSuccess -> {
                toast(event.msg)
                viewModel.getWorkoutInfo()
            }

            is WorkoutDetailsEvent.Error -> {
                toast(event.msg)
            }
        }
        viewModel.clearEvents()
    }

    private fun onDeleteClick(exercise: ExerciseDTO) {
        viewModel.deleteExercise(exercise)
    }
}