package org.ieselcaminas.aitor.pocketrol.characters

import android.annotation.SuppressLint
import android.util.Log
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

class CharacterAdapter(val clickListener: CharacterListener): ListAdapter<Character, RecyclerView.ViewHolder>(CharacterDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    //"Configure"(Initialice?) Data
    fun addAndSubmitList(list: List<Character>) {
        adapterScope.launch {
            lateinit var items: List<Character>

            //list.let {
                items = list.map {
                    it
                }
            //}
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
                val chr = getItem(position) as Character
                holder.bindView(chr, clickListener)
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
            binding.character = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}

//RecyclerView Listener, opens to CharacterCardData
class CharacterListener(val clicker: (character: Character) -> Unit) {
    fun onClick(chr: Character) = clicker(chr)
}

//Internal using to differentiate
class CharacterDiffCallback: DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.chrId == newItem.chrId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}



