package org.ieselcaminas.aitor.pocketrol.characters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_character.view.*
import org.ieselcaminas.aitor.pocketrol.R
import org.ieselcaminas.aitor.pocketrol.database.Character

class CharacterAdapter(private val context: Context): RecyclerView.Adapter<CharacterAdapter.ItemViewHolder>() {

    private var dataList = mutableListOf<Character>()
    fun setListData(data: MutableList<Character>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_character, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (dataList.size < 0) {
            return dataList.size
        }
        else {

        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val chr = dataList[position]
        holder.bindView(chr)
    }

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindView(chr: Character) {
            Glide.with(context).load(chr.imageUrl).into((itemView.circleImageView))
            itemView.chrName_textView.text = chr.name
            itemView.chrDesc_textView.text = chr.characterId.toString()
        }
    }

}