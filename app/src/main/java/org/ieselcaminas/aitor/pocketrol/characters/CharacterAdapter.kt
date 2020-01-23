package org.ieselcaminas.aitor.pocketrol.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ieselcaminas.aitor.pocketrol.database.Character
import org.ieselcaminas.aitor.pocketrol.databinding.ListItemCharacterBinding

class CharacterAdapter(val clickListener: CharacterListener) : ListAdapter<Character, ItemViewHolder>(CharacterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }
}

class ItemViewHolder private constructor(val binding: ListItemCharacterBinding): RecyclerView.ViewHolder(binding.root) {

    companion object { //hace lo que haya dentro static
        fun from(parent: ViewGroup): ItemViewHolder { //las funciones static pueden ser utilizadas sin instanciar un objeto de la clase
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemCharacterBinding.inflate(layoutInflater, parent, false)

            return ItemViewHolder(binding)
        }
    }

    fun bind(item: Character, clickListener: CharacterListener) {
        binding.chr = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }
}

//Lo utiliza el ListAdapter para trabajar ;; compara la lista interna(que tine el propio adapter) con la que tu le pasas en el Fragment mediante '.submitList()' y decide  QUE MODIFICAR
class CharacterDiffCallback: DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.characterId == newItem.characterId
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }

}

class CharacterListener(val clicker: (characterId: Long) -> Unit) {
    fun onClick(chr: Character) = clicker(chr.characterId)
}