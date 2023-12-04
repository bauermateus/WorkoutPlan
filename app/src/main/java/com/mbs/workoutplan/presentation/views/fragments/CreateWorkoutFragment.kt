package com.mbs.workoutplan.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mbs.workoutplan.data.db.models.WorkoutDTO
import com.mbs.workoutplan.databinding.FragmentCreateWorkoutBinding
import com.mbs.workoutplan.presentation.event.CreateWorkoutEvent
import com.mbs.workoutplan.presentation.viewmodels.CreateWorkoutViewModel
import com.mbs.workoutplan.util.initLoading
import com.mbs.workoutplan.util.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateWorkoutFragment : Fragment() {
    private var _binding: FragmentCreateWorkoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CreateWorkoutViewModel by viewModel()
    private val loading by lazy {
        initLoading(binding.root)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateWorkoutBinding.inflate(inflater, container, false)
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
            state.event?.let { processEvents(it) }
        }
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) loading.show()
            else loading.dismiss()
        }
    }

    private fun processEvents(event: CreateWorkoutEvent) {
        when (event) {
            is CreateWorkoutEvent.Success -> {
                findNavController().navigate(
                    CreateWorkoutFragmentDirections
                        .actionCreateWorkoutFragmentToWorkoutDetailsFragment(event.id)
                )
            }

            is CreateWorkoutEvent.Error -> {
                toast(event.msg)
            }
        }
        viewModel.clearEvents()
    }

    private fun onClick() {
        with(binding) {
            confirmButton.setOnClickListener {
                if (name.text.isNullOrBlank()) {
                    toast("Nome é obrigatório.")
                    return@setOnClickListener
                }
                viewModel.createWorkout(
                    WorkoutDTO(
                        null,
                        name.text.toString(),
                        description.text.toString(),
                        exercises = null
                    )
                )
            }
        }
    }

    private fun setup() {
        with(binding) {
            toolbar.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}