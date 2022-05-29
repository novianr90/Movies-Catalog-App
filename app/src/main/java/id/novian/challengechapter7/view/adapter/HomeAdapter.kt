package id.novian.challengechapter7.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.novian.challengechapter7.BuildConfig
import id.novian.challengechapter7.R
import id.novian.challengechapter7.databinding.GridItemBinding
import id.novian.challengechapter7.helper.toDate
import id.novian.challengechapter7.model.network.model.popular.Result

class HomeAdapter(private val onClickListener: (id: Int) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(item: List<Result>) = differ.submitList(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    inner class HomeViewHolder(private val binding: GridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.apply {

                cvItemData.setOnClickListener {
                    onClickListener.invoke(item.id)
                }

                val rating = (item.voteAverage * 10).toInt()

                tvMoviesTitle.text = item.title
                tvMoviesRating.text = rating.toString()
                pbMoviesRating.progress = rating
                tvMoviesReleaseData.text = item.releaseDate.toDate()

                val bgOptions = RequestOptions()
                    .placeholder(R.drawable.ic_image_error)

                val baseUrlImg = BuildConfig.BASE_URL_IMAGE
                val srcImg = "$baseUrlImg${item.posterPath}"

                Glide.with(itemView.context)
                    .load(srcImg)
                    .apply(bgOptions)
                    .override(1000, 1000)
                    .into(binding.ivMoviesImage)
            }
        }
    }
}