package com.mbs.workoutplan.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mbs.workoutplan.data.db.models.Workout
import com.mbs.workoutplan.databinding.FragmentCreateWorkoutBinding
import com.mbs.workoutplan.presentation.viewmodels.CreateWorkoutViewModel
import com.mbs.workoutplan.util.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateWorkoutFragment : Fragment() {
    private var _binding: FragmentCreateWorkoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CreateWorkoutViewModel by viewModel()

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
        viewModel.uiState.observe(viewLifecycleOwner) {

        }
    }

    private fun onClick() {
        with(binding) {
            confirmButton.setOnClickListener {
                if (name.text.isNullOrBlank()) {
                    toast("Nome é obrigatório.")
                    return@setOnClickListener
                }
                viewModel.createWorkout(
                    Workout(
                        null,
                        name.text.toString().toLong(),
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