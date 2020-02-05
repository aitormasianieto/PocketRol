package org.ieselcaminas.aitor.pocketrol.characters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.ieselcaminas.aitor.pocketrol.database.Character
import org.ieselcaminas.aitor.pocketrol.databinding.ItemCharacterBinding

class CharacterAdapter(val clickListener: CharacterListener): ListAdapter<DataItem, RecyclerView.ViewHolder>(CharacterDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addAndSubmitList(list: List<Character>) {
        adapterScope.launch {
            lateinit var items: List<DataItem.CharacterItem>

            list.let {
                items = list.map {
                    DataItem.CharacterItem(it)
                }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    //Holder "Constructor"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.from(parent)
    }

    //Makes the bind between the dataArray and the viewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                val chrItem = getItem(position) as DataItem.CharacterItem
                holder.bindView(chrItem.character, clickListener)
            }
        }
    }

    class ItemViewHolder private constructor(val binding: ItemCharacterBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCharacterBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }

        //binds data into each field
        fun bindView(item: Character, clickListener: CharacterListener) {
            /*Glide.with(context).load(item.imageUrl).into((itemView.circleImageView))
            itemView.chrName_textView.text = item.name
            itemView.chrDesc_textView.text = item.race*/

            binding.character = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}

//Internal using to differentiate
class CharacterDiffCallback: DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

//RecyclerView Listener, opens to CharacterCardData
class CharacterListener(val clicker: (chrId: Long) -> Unit) {
    fun onClick(chr: Character) = clicker(chr.chrId)
}

//Using a class thats Store DataClass
sealed class DataItem {
    data class CharacterItem(val character: Character): DataItem() {
        override val id = character.chrId
    }

    abstract val id: Long
}