package id.novian.challengechapter7.view.fragment

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.novian.challengechapter7.R
import id.novian.challengechapter7.databinding.FragmentProfileBinding
import id.novian.challengechapter7.helper.Status
import id.novian.challengechapter7.helper.bitmapToString
import id.novian.challengechapter7.helper.stringToBitmap
import id.novian.challengechapter7.model.local.entity.ImageSource
import id.novian.challengechapter7.model.local.entity.Profile
import id.novian.challengechapter7.viewmodel.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class Profile : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var email: String
    private val cal = Calendar.getInstance()

    companion object {
        const val REQUEST_CODE_PERMISSION = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email = ProfileArgs.fromBundle(arguments as Bundle).emailProfile
        viewModel.getImageSrc(email)
        btnLogoutClicked()
        btnImageClicked()
        etBirthDateClicked()
        observe()
    }

    private fun btnLogoutClicked() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.action_profile_to_login)
        }
    }

    private fun setView(
        fullName: String, username: String,
        address: String, birthDate: String
    ) {
        binding.etFullName.setText(fullName)
        binding.etUsername.setText(username)
        binding.etAddress.setText(address)
        binding.etBirthDate.setText(birthDate)
    }

    private fun btnUpdateClicked(profile: Profile) {
        binding.btnUpdate.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val fullName = binding.etFullName.text.toString()
            val address = binding.etAddress.text.toString()
            val birthDate = binding.etBirthDate.text.toString()

            val objectProfile = Profile(
                profile.id, username,
                profile.email, profile.password,
                fullName, birthDate, address
            )

            viewModel.updateProfile(objectProfile)
        }
    }

    private fun observe() {
        viewModel.getDataProfile(email).observe(viewLifecycleOwner) {
            when (it.status) {

                Status.SUCCESS -> {
                    val data = it.data!!
                    setView(data.fullName, data.userName, data.address, data.birthDate)
                    btnUpdateClicked(data)
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.imgSrc.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                when (it.substring(0, 1)) {
                    "1" -> {
                        val uri = it.substringAfter("_")
                        val uriImage = Uri.parse(uri)
                        binding.ivProfile.setImageURI(uriImage)
                    }

                    "2" -> {
                        val dataBm = it.substringAfter("_")
                        binding.ivProfile.setImageBitmap(dataBm.stringToBitmap())
                    }
                }
            } else {
                binding.ivProfile.setImageResource(R.drawable.ic_profile)
            }
        }
    }

    //Image Src
    private fun btnImageClicked() {
        binding.btnAddImage.setOnClickListener {
            checkPermission()
        }
    }

    private fun checkPermission() {
        if (isGranted(
                requireActivity(),
                Manifest.permission.CAMERA, arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), REQUEST_CODE_PERMISSION
            )
        ) {
            chooseImageDialog()
        }
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }

    //Open Gallery
    private fun openGallery() {
        requireActivity().intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    //Get URI
    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            var uriString = result.toString()
            uriString = "1_$uriString"
            viewModel.insertImageSrc(ImageSource(null, email, uriString))
            binding.ivProfile.setImageURI(result)
        }

    // Open Camera
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }

    // Get Bitmap from Camera
    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val bitmap = result.data!!.extras?.get("data") as Bitmap

                var value = bitmap.bitmapToString()
                value = "2_$value"

                viewModel.insertImageSrc(ImageSource(null, email, value))

                binding.ivProfile.setImageBitmap(bitmap)
            }
        }


    //Calendar
    private fun createDateDialogDate(): DatePickerDialog {
        return DatePickerDialog(
            requireContext(),
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
    }

    private fun etBirthDateClicked() {
        binding.etBirthDate.setOnClickListener {
            createDateDialogDate().show()
        }
    }

    private val dateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "MM/dd/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            val stringDate = sdf.format(cal.time)
            binding.etBirthDate.setText(stringDate)
        }
}