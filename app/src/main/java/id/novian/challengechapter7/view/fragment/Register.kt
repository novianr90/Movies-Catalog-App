package id.novian.challengechapter7.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import id.novian.challengechapter7.databinding.FragmentRegisterBinding
import id.novian.challengechapter7.model.local.entity.Profile
import id.novian.challengechapter7.viewmodel.RegisterViewModel

@AndroidEntryPoint
class Register : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkChange()
        checkConfirmPasswordOnChanged()
        btnRegisterClicked()
    }

    private fun btnRegisterClicked() {
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (checkEtNullOrEmpty(email, username, password) && checkPasswordMatch()) {
                viewModel.checkEmailAndUsername(email, username)
            }

            observe(Profile(null, username, email, password))
        }
    }

    private fun observe(profile: Profile) {
        viewModel.dataSuccess.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.insertProfile(profile)
                findNavController().navigate(RegisterDirections.actionRegisterToLogin())
            } else {
                createToast("Profile Already Registered")
            }
        }
    }

    private fun checkEtNullOrEmpty(email: String, username: String, password: String): Boolean {
        return if (email.isEmpty() && username.isEmpty() && password.isEmpty()) {
            binding.containerEmail.error = "Input Email"
            binding.containerUsername.error = "Input Username"
            binding.containerPassword.error = "Input Password"
            false
        } else {
            binding.containerEmail.error = null
            binding.containerUsername.error = null
            binding.containerPassword.error = null
            true
        }
    }

    private fun checkPasswordMatch(): Boolean {
        return binding.etPassword.text.toString() == binding.etConfirmPassword.text.toString()
    }

    private fun viewOnChange(view: TextInputEditText, container: TextInputLayout, msg: String) {
        binding.apply {
            view.apply {
                doAfterTextChanged {
                    if (text.isNullOrEmpty()) {
                        container.error = msg
                    } else {
                        container.error = null
                    }
                }
            }
        }

        if (!checkPasswordMatch()) {
            binding.containerConfirmPassword.error = "Doesn't Match"
        } else {
            binding.containerConfirmPassword.error = null
        }
    }

    private fun checkChange() {
        viewOnChange(binding.etEmail, binding.containerEmail, "Input Email")
        viewOnChange(binding.etUsername, binding.containerUsername, "Input Email")
        viewOnChange(binding.etPassword, binding.containerPassword, "Input Email")
    }


    private fun checkConfirmPasswordOnChanged() {
        binding.etConfirmPassword.doAfterTextChanged {
            if (!checkPasswordMatch() || binding.etConfirmPassword.text.isNullOrEmpty()) {
                binding.containerConfirmPassword.error = "Doesn't Match"
            } else {
                binding.containerConfirmPassword.error = null
            }
        }
    }


    private fun createToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

}