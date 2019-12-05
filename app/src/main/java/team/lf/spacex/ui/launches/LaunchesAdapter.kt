package team.lf.spacex.ui.launches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import team.lf.spacex.databinding.ItemLaunchBinding
import team.lf.spacex.domain.Launch
import timber.log.Timber


class LaunchesAdapter(private val viewModel: LaunchesViewModel) :
    ListAdapter<Launch, LaunchesAdapter.LaunchesViewHolder>(LaunchesDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun submitLaunchListAsync(list: List<Launch>?) {
        adapterScope.launch {
            withContext(Dispatchers.Main) {
                submitList(list)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchesViewHolder {
        return LaunchesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LaunchesViewHolder, position: Int) {
        val item = getItem(position) as Launch
        holder.bind(item, viewModel)
    }

    class LaunchesViewHolder private constructor(private val binding: ItemLaunchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Launch,
            viewModel: LaunchesViewModel
        ) {
            binding.launch = item
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): LaunchesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLaunchBinding.inflate(layoutInflater, parent, false)
                return LaunchesViewHolder(binding)
            }
        }
    }
}


class LaunchesDiffCallback : DiffUtil.ItemCallback<Launch>() {
    override fun areItemsTheSame(oldItem: Launch, newItem: Launch): Boolean {
        return oldItem.flight_number == newItem.flight_number
    }

    override fun areContentsTheSame(oldItem: Launch, newItem: Launch): Boolean {
        return oldItem == newItem
    }
}