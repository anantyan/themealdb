package id.anantyan.foodapps.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import coil.load
import coil.size.ViewSizeResolver
import id.anantyan.foodapps.R
import id.anantyan.foodapps.data.remote.model.MealsItem
import id.anantyan.foodapps.databinding.ListItemMealsBinding

class MealsAdapter : ListAdapter<MealsItem, MealsAdapter.MealsItemViewHolder>(MealsItemComparator) {

    private var _onClick: ((position: Int, item: MealsItem) -> Unit)? = null

    private object MealsItemComparator : DiffUtil.ItemCallback<MealsItem>() {
        override fun areItemsTheSame(oldItem: MealsItem, newItem: MealsItem): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealsItem, newItem: MealsItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsItemViewHolder {
        return MealsItemViewHolder(
            ListItemMealsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MealsItemViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class MealsItemViewHolder(private val binding: ListItemMealsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: MealsItem) {
            binding.txtTitle.text = item.strMeal
            binding.imgHeadline.load(item.strMealThumb) {
                crossfade(true)
                placeholder(R.drawable.img_loading)
                error(R.drawable.img_not_found)
                size(ViewSizeResolver(binding.imgHeadline))
            }
        }
    }

    fun onClick(listener: (position: Int, item: MealsItem) -> Unit) {
        _onClick = listener
    }
}