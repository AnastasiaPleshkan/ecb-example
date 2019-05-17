package eu.europa.ecb.ui.common

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import eu.europa.ecb.AppExecutors
import eu.europa.ecb.R
import eu.europa.ecb.databinding.CubeItemItemBinding
import eu.europa.ecb.vo.CubeItem

/**
 * A RecyclerView adapter for [CubeItem] class.
 */
class CubeItemListAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val itemClickCallback: ((CubeItem) -> Unit)?
) : DataBoundListAdapter<CubeItem, CubeItemItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<CubeItem>() {
            override fun areItemsTheSame(oldItem: CubeItem, newItem: CubeItem): Boolean {
                return oldItem.currency == newItem.currency
            }

            override fun areContentsTheSame(oldItem: CubeItem, newItem: CubeItem): Boolean {
                return return oldItem.currency == newItem.currency
                        && oldItem.rate == newItem.rate
            }
        }
) {

    private var selectedItem: CubeItem? = null

    override fun createBinding(parent: ViewGroup): CubeItemItemBinding {
        val binding = DataBindingUtil.inflate<CubeItemItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.cube_item_item,
                parent,
                false,
                dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.cube?.let {
                selectedItem = it
                notifyDataSetChanged()
                itemClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: CubeItemItemBinding, item: CubeItem) {
        binding.selected = (item == selectedItem)
        binding.cube = item
    }
}
