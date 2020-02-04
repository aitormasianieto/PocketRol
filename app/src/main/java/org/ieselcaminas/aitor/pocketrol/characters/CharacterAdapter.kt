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

class CharacterAdapter(private val context: Context, val clickListener: CharacterListener): RecyclerView.Adapter<CharacterAdapter.ItemViewHolder>() {

    //Data array
    private var dataList = mutableListOf<Character>()
    fun setListData(data: MutableList<Character>) {
        dataList = data
    }

    //Holder "Constructor"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_character, parent, false)
        return ItemViewHolder(view)
    }

    //Makes the bind between the dataArray and the viewHolder
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val chr = dataList[position]
        holder.bindView(chr)
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 0) {
            dataList.size
        }
        else {
            0
        }
    }

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        //binds data into each field
        fun bindView(chr: Character) {
            Glide.with(context).load(chr.imageUrl).into((itemView.circleImageView))
            itemView.chrName_textView.text = chr.name
            itemView.chrDesc_textView.text = chr.race
        }
    }
}

//RecyclerView Listener, opens to CharacterCardData
class CharacterListener(val clicker: (chrId: Long) -> Unit) {
    fun onClick(chr: Character) = clicker(chr.chrId)
}