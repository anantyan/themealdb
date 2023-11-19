package id.anantyan.foodapps.presentation.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import id.anantyan.foodapps.R
import id.anantyan.foodapps.databinding.ListItemMealsBinding
import id.anantyan.foodapps.domain.model.RecipeModel

class MealsLocalAdapter : ListAdapter<RecipeModel, MealsLocalAdapter.MealsItemViewHolder>(MealsItemComparator) {

    private var _onClick: ((position: Int, item: RecipeModel) -> Unit)? = null

    private object MealsItemComparator : DiffUtil.ItemCallback<RecipeModel>() {
        override fun areItemsTheSame(oldItem: RecipeModel, newItem: RecipeModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RecipeModel, newItem: RecipeModel): Boolean {
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

        fun bindItem(item: RecipeModel) {
            binding.txtTitle.text = item.title
            binding.imgHeadline.load(item.thumbnail) {
                crossfade(true)
                placeholder(R.drawable.img_loading)
                error(R.drawable.img_not_found)
                size(ViewSizeResolver(binding.imgHeadline))
            }
        }
    }

    fun onClick(listener: (position: Int, item: RecipeModel) -> Unit) {
        _onClick = listener
    }
}

