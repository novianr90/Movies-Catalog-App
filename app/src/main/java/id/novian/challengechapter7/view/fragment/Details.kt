package id.novian.challengechapter7.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import id.novian.challengechapter7.BuildConfig
import id.novian.challengechapter7.R
import id.novian.challengechapter7.databinding.FragmentDetailsBinding
import id.novian.challengechapter7.helper.Status
import id.novian.challengechapter7.helper.toDateDetail
import id.novian.challengechapter7.model.network.model.details.DetailsResponse
import id.novian.challengechapter7.viewmodel.DetailsViewModel

@AndroidEntryPoint
class Details : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = DetailsArgs.fromBundle(arguments as Bundle).movieId
        observe(id)
    }

    private fun observe(id: Int) {
        viewModel.getDetails(id).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    binding.pbDetail.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {

                    binding.pbDetail.visibility = View.GONE

                    val data = resource.data!!
                    setView(
                        title = data.title, date = data.releaseDate,
                        runtime = getRuntime(data), genre = getGenre(data),
                        tagline = data.tagline, overview = data.overview,
                        poster = data.posterPath, backdrop = data.backdropPath
                    )
                }

                Status.ERROR -> {
                    binding.pbDetail.visibility = View.GONE
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }

    }

    private fun getGenre(detailsResponse: DetailsResponse): String {
        var genre = ""

        val genreResult = detailsResponse.genres

        if (genreResult.size == 1) {
            genre += genreResult[0].name
        } else {
            for (i in genreResult.indices) {
                genre += if (i == genreResult.lastIndex) {
                    genreResult[i].name
                } else {
                    genreResult[i].name + ", "
                }
            }
        }
        return genre
    }

    private fun getRuntime(detailsResponse: DetailsResponse): String {
        val runtime = detailsResponse.runtime

        val hour = (runtime / 60).toString()
        val minutes = (runtime % 60).toString()
        return "${hour}h ${minutes}m"
    }

    private fun setView(
        title: String, date: String,
        runtime: String, genre: String,
        tagline: String, overview: String,
        poster: String, backdrop: String
    ) {

        binding.tvMoviesTitle.text = title
        binding.tvMoviesReleaseDate.text = date.toDateDetail()
        binding.tvRuntime.text = runtime
        binding.tvGenre.text = genre
        binding.tvTagline.text = tagline
        binding.tvMoviesOverview.text = overview

        val baseUrlImg = BuildConfig.BASE_URL_IMAGE
        val srcPoster = "$baseUrlImg${poster}"
        val srcBackdrop = "$baseUrlImg${backdrop}"

        val bgOptions = RequestOptions()
            .placeholder(R.drawable.ic_image_error)

        Glide.with(requireContext())
            .load(srcBackdrop)
            .apply(bgOptions)
            .into(binding.ivMoviesBackdrop)

        Glide.with(requireContext())
            .load(srcPoster)
            .apply(bgOptions)
            .override(500, 500)
            .into(binding.ivMoviesImage)
    }

}