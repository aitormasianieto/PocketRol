package org.ieselcaminas.aitor.pocketrol.charactercreation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import org.ieselcaminas.aitor.pocketrol.R
import org.ieselcaminas.aitor.pocketrol.databinding.FragmentCharacterCreationBinding
import org.ieselcaminas.aitor.pocketrol.databinding.FragmentCharactersBinding

/**
 * A simple [Fragment] subclass.
 */
class CharacterCreationFragment : Fragment() {

    private lateinit var binding:FragmentCharacterCreationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_creation, container, false)

        return binding.root
    }


}
