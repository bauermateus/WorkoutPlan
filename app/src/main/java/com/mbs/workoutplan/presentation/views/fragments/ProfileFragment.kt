package com.mbs.workoutplan.presentation.views.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.mbs.workoutplan.databinding.FragmentProfileBinding
import com.mbs.workoutplan.presentation.event.ProfileEvent
import com.mbs.workoutplan.presentation.viewmodels.UserInfoViewModel
import com.mbs.workoutplan.presentation.views.activities.LoginActivity
import com.mbs.workoutplan.util.load
import com.mbs.workoutplan.util.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserInfoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        setup()
    }

    private fun observe() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.userData?.name.let { binding.username.text = it }
            state.userData?.image?.let { binding.profilePicture.load(it) }
            state.event?.let { processEvents(it) }
            FirebaseAuth.getInstance().currentUser?.uid?.let { Log.d("haha", it) }
        }
    }

    private fun processEvents(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.Error -> {
                toast(event.msg)
            }
        }
        viewModel.clearEvents()
    }

    private fun setup() {
        binding.editData.setOnClickListener {
            viewModel.currentUiState.userData?.let {
                findNavController().navigate(
                    ProfileFragmentDirections
                        .actionProfileFragmentToEditProfileFragment(it)
                )
            }
        }
        binding.exit.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}