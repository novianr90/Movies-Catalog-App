package id.novian.challengechapter7.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.novian.challengechapter7.R
import id.novian.challengechapter7.databinding.FragmentSplashBinding
import id.novian.challengechapter7.viewmodel.SplashViewModel
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            splashNextToPage()
        }
    }

    private suspend fun splashNextToPage() {
        delay(2000)
        viewModel.checkStatusLogin().observe(requireActivity()) {
            view?.post {
                if (it) {
                    findNavController().navigate(R.id.action_splashFragment_to_home2)
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_login)
                }
            }
        }
    }
}