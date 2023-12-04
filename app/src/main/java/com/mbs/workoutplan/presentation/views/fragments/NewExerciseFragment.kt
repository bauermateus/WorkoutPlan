package com.mbs.workoutplan.presentation.views.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mbs.workoutplan.R
import com.mbs.workoutplan.databinding.FragmentNewExerciseBinding
import com.mbs.workoutplan.presentation.event.NewExerciseEvent
import com.mbs.workoutplan.presentation.viewmodels.NewExerciseViewModel
import com.mbs.workoutplan.util.initLoading
import com.mbs.workoutplan.util.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewExerciseFragment : Fragment() {
    private var _binding: FragmentNewExerciseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewExerciseViewModel by viewModel()
    private var selectedImgUri: Uri? = null
    private val args: NewExerciseFragmentArgs by navArgs()
    private val loading by lazy {
        initLoading(binding.root)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        onClick()
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
            if (isLoading) {
                loading.show()
                if (selectedImgUri != null) toast("Fazendo upload da foto, isso pode demorar um pouco...")
            } else loading.dismiss()
        }
    }

    private fun onClick() {
        with(binding) {
            confirmButton.setOnClickListener {
                if (binding.name.text.isNullOrBlank()) {
                    toast("Nome deve ser preenchido.")
                    return@setOnClickListener
                }
                viewModel.createNewExercise(
                    args.workoutId,
                    name.text.toString().trim(),
                    obs.text.toString().trim(),
                    selectedImgUri
                )
            }
            editPhotoButton.setOnClickListener {
                openImagePicker()
            }
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun processEvents(event: NewExerciseEvent) {
        when (event) {
            is NewExerciseEvent.Error -> {
                toast(event.msg)
            }

            is NewExerciseEvent.CreatedSuccess -> {
                findNavController().popBackStack()
                toast("Exercício adicionado.")
            }
        }
        viewModel.clearEvents()
    }

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                startCamera()
            } else {
                Toast.makeText(requireContext(), "Permissão negada.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private fun checkCameraPermission() {
        if (
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            AlertDialog.Builder(requireActivity(), R.style.AlertStyle)
                .setTitle("Permissão de camera")
                .setMessage("Necessária para tirar fotos.")
                .setPositiveButton("OK") { _, _ ->
                    requestCameraPermission.launch(Manifest.permission.CAMERA)
                }
                .setNegativeButton("Cancelar", null)
                .create()
                .show()
        }
    }

    private fun startCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            null
        }
        photoFile?.also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.mbs.workoutplan.fileprovider",
                it
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            activityResultContract.launch(takePictureIntent)
        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            selectedImgUri = this.toUri()
        }
    }

    private val activityResultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    selectedImgUri = uri
                }
                binding.img.setImageURI(selectedImgUri)
            }
        }

    private fun openImagePicker() {
        val options = arrayOf<CharSequence>("Camera", "Galeria", "Cancelar")
        AlertDialog.Builder(requireActivity(), R.style.AlertStyle)
            .setTitle("Selecionar foto")
            .setItems(options) { dialog, item ->
                when {
                    options[item] == "Camera" -> {
                        checkCameraPermission()
                    }

                    options[item] == "Galeria" -> {
                        val pickPhoto =
                            Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            )
                        activityResultContract.launch(pickPhoto)
                    }

                    options[item] == "Cancelar" -> {
                        dialog.dismiss()
                    }
                }
            }
            .show()
    }
}