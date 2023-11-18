package id.anantyan.foodapps.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import id.anantyan.foodapps.data.remote.model.MealsItem
import id.anantyan.foodapps.databinding.ListItemCategoriesBinding

class CategoriesAdapter : ListAdapter<MealsItem, CategoriesAdapter.MealsItemViewHolder>(MealsItemComparator) {

    private var _onClick: ((position: Int, item: MealsItem) -> Unit)? = null

    private object MealsItemComparator : DiffUtil.ItemCallback<MealsItem>() {
        override fun areItemsTheSame(oldItem: MealsItem, newItem: MealsItem): Boolean {
            return oldItem.strCategory == newItem.strCategory
        }

        override fun areContentsTheSame(oldItem: MealsItem, newItem: MealsItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsItemViewHolder {
        return MealsItemViewHolder(
            ListItemCategoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MealsItemViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class MealsItemViewHolder(private val binding: ListItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: MealsItem) {
            binding.btnCategory.text = item.strCategory
        }
    }

    fun onClick(listener: (position: Int, item: MealsItem) -> Unit) {
        _onClick = listener
    }
}