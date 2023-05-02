package com.akmalmf.storyapp.ui.main.story_create


import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.akmalmf.storyapp.R
import com.akmalmf.storyapp.base.BaseFragment
import com.akmalmf.storyapp.databinding.FragmentStoryCreateBinding
import com.akmalmf.storyapp.domain.utils.convertToFile
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.akmalmf.storyapp.data.abstraction.Status
import com.akmalmf.storyapp.domain.utils.createCustomTempFile
import com.akmalmf.storyapp.domain.utils.getText
import com.akmalmf.storyapp.domain.utils.reduceFileImage
import com.akmalmf.storyapp.domain.utils.toInvisible
import com.akmalmf.storyapp.domain.utils.toVisible
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class StoryCreateFragment : BaseFragment<FragmentStoryCreateBinding>() {
    private val viewModel: StoryCreateViewModel by hiltNavGraphViewModels(R.id.story_nav)
    var photo: File? = null
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStoryCreateBinding
        get() = FragmentStoryCreateBinding::inflate

    override fun initView() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        bi.appBar.textToolbar.text = "Ayoo buat cerita!"

        bi.buttonGalery.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            val chooser = Intent.createChooser(intent, "Choose a Picture")
            launcherIntentGallery.launch(chooser)
        }
        bi.buttonCamera.setOnClickListener {
            startTakePhoto()
        }

        bi.buttonSubmit.setOnClickListener {
            if (bi.textInputDescription.getText().isEmpty()) {
                bi.textInputDescription.helperText = "Deskription harus diisi!"
            } else {
                bi.textInputDescription.helperText = null
            }
            if (photo?.exists() != true) {
                snackBarError("Image harus diisi!")
            }
            if (photo?.exists() == true && bi.textInputDescription.getText().isNotEmpty()) {
                val compresedPhoto = reduceFileImage(photo as File)
                val requestImageFile =
                    compresedPhoto.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part? = requestImageFile?.let { it1 ->
                    MultipartBody.Part.createFormData(
                        "photo",
                        compresedPhoto.name,
                        it1
                    )
                }
                if (imageMultipart != null) {
                    viewModel.createStory(bi.textInputDescription.getText(), imageMultipart)
                        .observe(this) {
                            when (it.status) {
                                Status.LOADING -> {
                                    bi.buttonSubmit.toInvisible()
                                    bi.progressBar.toVisible()

                                }

                                Status.SUCCESS -> {
                                    snackBarSuccess("Berhasil menambahkan cerita!")
                                    findNavController().navigate(
                                        StoryCreateFragmentDirections.actionStoryCreateFragmentToStoryListFragment(
                                            true
                                        )
                                    )
                                }

                                Status.ERROR -> {
                                    bi.buttonSubmit.toVisible()
                                    bi.progressBar.toInvisible()
                                    (it.data?.message
                                        ?: it.message)?.let { it1 -> snackBarError(it1) }
                                }
                            }
                        }
                }
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = convertToFile(requireContext(), selectedImg)
            photo = myFile
            bi.imagePreview.setImageURI(selectedImg)
        }
    }

    override fun onResume() {
        super.onResume()
        if (photo?.exists() == true) {
            val myBitmap = BitmapFactory.decodeFile(photo!!.absolutePath)
            bi.imagePreview.setImageBitmap(myBitmap)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                snackBarError("Membutuhkan akases kamera!")
                requireActivity().finish()
            }
        }
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            photo = File(currentPhotoPath)
            photo.let { file ->
                if (file != null) {
                    bi.imagePreview.setImageBitmap(BitmapFactory.decodeFile(file.path))
                }
            }

        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)

        createCustomTempFile(requireActivity().application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.akmalmf.storyapp.story",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}