package id.novian.challengechapter7.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.novian.challengechapter7.databinding.FragmentLoginBinding
import id.novian.challengechapter7.viewmodel.LoginViewModel
import kotlin.system.exitProcess

@AndroidEntryPoint
class Login : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressed)
    }

    private val backPressed = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            exitProcess(0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkWithChange()
        btnLoginClicked()
        tvRegisterClicked()
    }

    private fun btnLoginClicked() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (checkDataNullOrEmpty(email, password)) {
                viewModel.checkEmailAndPassword(email, password)
            }
            observe()
        }
    }

    private fun observe() {
        viewModel.dataSuccess.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(LoginDirections.actionLoginToHome2())
            } else {
                createToast("Profile not Found")
            }
        }
    }

    private fun createToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun checkDataNullOrEmpty(email: String, password: String): Boolean {
        return if (email.isEmpty() && password.isEmpty()) {
            binding.containerEtName.error = "Input Email"
            binding.containerEtPassword.error = "Input Password"
            false
        } else {
            binding.containerEtName.error = null
            binding.containerEtPassword.error = null
            true
        }
    }

    private fun checkWithChange() {
        binding.apply {

            etEmail.apply {
                doAfterTextChanged {
                    if (text.isNullOrEmpty()) {
                        binding.containerEtName.error = "Input Email"
                    } else {
                        binding.containerEtName.error = null
                    }
                }
            }

            etPassword.apply {
                doAfterTextChanged {
                    if (text.isNullOrEmpty()) {
                        binding.containerEtPassword.error = "Input Password"
                    } else {
                        binding.containerEtPassword.error = null
                    }
                }
            }
        }
    }

    private fun tvRegisterClicked() {
        binding.tvRegisterAcc.setOnClickListener {
            findNavController().navigate(LoginDirections.actionLoginToRegister())
        }
    }
}