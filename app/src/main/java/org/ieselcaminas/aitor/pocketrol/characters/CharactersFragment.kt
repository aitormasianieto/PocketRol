package org.ieselcaminas.aitor.pocketrol.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.ieselcaminas.aitor.pocketrol.R
import org.ieselcaminas.aitor.pocketrol.database.Character
import org.ieselcaminas.aitor.pocketrol.databinding.FragmentCharactersBinding

class CharactersFragment : Fragment() {

    private lateinit var adapter: CharacterAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentCharactersBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false)

        //RecyclerView
        adapter = CharacterAdapter(context!!)
        binding.chrRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.chrRecyclerView.adapter = adapter

        val dummyList = mutableListOf<Character>()
        dummyList.add(Character(1,
            "https://s3-eu-west-2.amazonaws.com/dungeon20/images/176/original-dcdb4d6f85745ea585917c22a357292a8182a482.png?1528971360",
            "Aitor"))

        adapter.setListData(dummyList) //Setting data to Adapter
        adapter.notifyDataSetChanged() //Notifies the adapter so it updates the dataList

        return binding.root
    }

}
