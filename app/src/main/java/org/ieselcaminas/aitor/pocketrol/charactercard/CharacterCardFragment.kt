package org.ieselcaminas.aitor.pocketrol.charactercard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.ieselcaminas.aitor.pocketrol.R
import org.ieselcaminas.aitor.pocketrol.databinding.FragmentCharacterCardBinding


class CharacterCardFragment : Fragment() {

    private val character by lazy { CharacterCardFragmentArgs.fromBundle(arguments!!).chr }
    private val viewModelFactory by lazy { CharacterCardViewModelFactory(character) }
    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory).get(CharacterCardViewModel::class.java) }
    //This could be done easier, but I was trying things

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentCharacterCardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_card, container, false)

        binding.character = viewModel.character

        binding.editButton.setOnClickListener {
            findNavController().navigate(CharacterCardFragmentDirections.actionCharacterCardFragmentToCharacterCreationFragment(character, this::class.java.toString()))
        }

        return binding.root
    }
}
