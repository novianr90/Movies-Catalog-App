package id.novian.challengechapter7.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.novian.challengechapter7.databinding.FragmentHomeBinding
import id.novian.challengechapter7.helper.Status
import id.novian.challengechapter7.view.adapter.HomeAdapter
import id.novian.challengechapter7.viewmodel.HomeViewModel

@AndroidEntryPoint
class Home : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        getEmail()
        observe()
    }

    private fun initRecyclerView() {
        binding.rvMovies.apply {
            homeAdapter = HomeAdapter(::nextToDetails)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = homeAdapter
        }
    }

    private fun nextToDetails(id: Int) {
        val action = HomeDirections.actionHome2ToDetails(movieId = id)
        findNavController().navigate(action)
    }

    private fun ivProfileClicked(email: String) {
        binding.ivProfile.setOnClickListener {
            findNavController().navigate(HomeDirections.actionHome2ToProfile(email))
        }
    }

    private fun getEmail() {
        viewModel.getEmail().observe(viewLifecycleOwner) {
            viewModel.getUsername(it)
            ivProfileClicked(it)
        }
    }

    private fun observe() {
        viewModel.usernameDb.observe(viewLifecycleOwner) { username ->
            val result = "Welcome, ${username}!"
            binding.tvGreeting.text = result
        }

        viewModel.getAllPopularMovies().observe(viewLifecycleOwner) { res ->
            when (res.status) {
                Status.LOADING -> {
                    binding.pbHome.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {
                    res.data?.let { homeAdapter.submitList(it.results) }
                    binding.pbHome.visibility = View.GONE
                }

                Status.ERROR -> {
                    binding.pbHome.visibility = View.GONE
                    Toast.makeText(requireContext(), res.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}