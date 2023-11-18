package id.anantyan.foodapps.presentation.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import id.anantyan.foodapps.R
import id.anantyan.foodapps.data.remote.model.DataItem
import id.anantyan.foodapps.databinding.ListItemUsersBinding

class UsersAdapter : PagingDataAdapter<DataItem, UsersAdapter.DataItemViewHolder>(DataItemComparator) {

    private var _onClick: ((position: Int, item: DataItem) -> Unit)? = null

    private object DataItemComparator : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataItemViewHolder {
        return DataItemViewHolder(
            ListItemUsersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataItemViewHolder, position: Int) {
        holder.bindItem(getItem(position) ?: DataItem())
    }

    inner class DataItemViewHolder(private val binding: ListItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition) ?: DataItem())
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bindItem(item: DataItem) {
            binding.imgUser.load(item.avatar) {
                crossfade(true)
                placeholder(R.drawable.img_loading)
                error(R.drawable.img_not_found)
                size(ViewSizeResolver(binding.imgUser))
            }
            binding.txtFirstname.text = item.firstName
        }
    }

    fun onClick(listener: (position: Int, item: DataItem) -> Unit) {
        _onClick = listener
    }
}