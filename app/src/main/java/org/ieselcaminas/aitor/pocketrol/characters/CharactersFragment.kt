package org.ieselcaminas.aitor.pocketrol.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import org.ieselcaminas.aitor.pocketrol.R
import org.ieselcaminas.aitor.pocketrol.databinding.FragmentCharactersBinding

class CharactersFragment : Fragment() {

    private lateinit var viewModel: CharactersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentCharactersBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false)

        val adapter = CharacterAdapter(CharacterListener { nightId ->
            //sleepTrackerViewModel.onSleepNightClicked(nightId)
        })
        binding.sleepList.adapter = adapter

        return binding.root
    }

}
