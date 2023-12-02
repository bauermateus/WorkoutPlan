package com.mbs.workoutplan.presentation.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.mbs.workoutplan.R
import com.mbs.workoutplan.databinding.FragmentLoginBinding
import com.mbs.workoutplan.presentation.event.LoginEvent
import com.mbs.workoutplan.presentation.uistate.isAllFieldsFilledCorrectly
import com.mbs.workoutplan.presentation.viewmodels.LoginViewModel
import com.mbs.workoutplan.presentation.views.activities.MainActivity
import com.mbs.workoutplan.util.initLoading
import com.mbs.workoutplan.util.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModel()
    private val loading by lazy {
        initLoading(binding.root)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateFields()
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.event?.let { processEvents(it) }
            with(binding) {
                if (state.isAllFieldsFilledCorrectly) {
                    loginButton.setBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.primary)
                    )
                    loginButton.setOnClickListener {
                        viewModel.signInWithEmailAndPassword(
                            email.text.toString().trim(),
                            password.text.toString().trim()
                        )
                    }
                } else {
                    loginButton.setBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.gray)
                    )
                    loginButton.setOnClickListener {
                        toast("Preencha todos os campos.")
                    }
                }
            }
        }
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) loading.show()
            else loading.dismiss()
        }
    }

    private fun processEvents(event: LoginEvent) {
        when (event) {
            is LoginEvent.Success -> {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }

            is LoginEvent.Error -> {
                toast(event.msg)
            }
        }
        viewModel.clearEvents()
    }

    private fun validateFields() {
        with(binding) {
            email.doAfterTextChanged {
                viewModel.validateEmail(it.toString().trim())
            }
            password.doAfterTextChanged {
                viewModel.validatePassword(it.toString().trim())
            }
        }
    }
}