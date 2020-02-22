package org.ieselcaminas.aitor.pocketrol.charactercreation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import org.ieselcaminas.aitor.pocketrol.R
import org.ieselcaminas.aitor.pocketrol.charactercard.CharacterCardFragment
import org.ieselcaminas.aitor.pocketrol.database.Character
import org.ieselcaminas.aitor.pocketrol.database.RaceOrClas
import org.ieselcaminas.aitor.pocketrol.databinding.FragmentCharacterCreationBinding

/**
 * A simple [Fragment] subclass.
 */
class CharacterCreationFragment : Fragment() {

    private lateinit var binding: FragmentCharacterCreationBinding
    private val viewModel by lazy { ViewModelProvider(this).get(CharacterCreationViewModel::class.java) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_creation, container, false)

        //Saves spinners data
        val races: MutableList<RaceOrClas> = mutableListOf()
        val clases: MutableList<RaceOrClas> = mutableListOf()

    //CLICKERS
        //SPINNER clickers
        binding.raceSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected( parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                for (r in races) {
                    if (r.name == parent!!.getItemAtPosition(position))
                        binding.race = r
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) { /*Nothing happens*/ }
        }
        binding.clasSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected( parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                for (c in clases) {
                    if (c.name == parent!!.getItemAtPosition(position))
                        binding.clas = c
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) { /*Nothing happens*/ }
        }
        //BUTTON clickerS
        binding.imgCheckButton.setOnClickListener {
            if (URLUtil.isValidUrl(binding.imageUrlEditText.text.toString())) {
                binding.imageUrl = binding.imageUrlEditText.text.toString()
                Toast.makeText(context!!, "Image updated.", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(context!!, "Not valid URL. Try again! <3", Toast.LENGTH_LONG).show()
            }
        }

        binding.saveButton.setOnClickListener {
            viewModel.repo.setCharacter(
                Character(
                    viewModel.chrID,
                    binding.imageUrl ?: "",
                    binding.nameEditText.text.toString(),
                    binding.race!!.name,
                    binding.clas!!.name
                )
            )
            findNavController().navigate( CharacterCreationFragmentDirections.actionCharacterCreationFragmentToCharactersFragment())
        }

        //Puts data into the spinners
        viewModel.race.observe(viewLifecycleOwner, Observer {
            val names: MutableList<String> = mutableListOf()
            names.add(0, "---Choose Any---")
            for (i in it) {
                names.add(i.name)
                    races.add(i)
            }
                binding.raceSpinner.adapter =
                    ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_item, names)
        })
        viewModel.clas.observe(viewLifecycleOwner, Observer {
            val names: MutableList<String> = mutableListOf()
            names.add(0, "---Choose Any---")
            for (i in it) {
                names.add(i.name)
                clases.add(i)
            }
            binding.clasSpinner.adapter =
                ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_item, names)
        })

        viewModel.fetchRaceOrClasData("races")
        viewModel.fetchRaceOrClasData("clases")

        //Checking where does it come from
        if (CharacterCreationFragmentArgs.fromBundle(arguments!!).fragment == CharacterCardFragment::class.java.toString()) {
            var chr = CharacterCreationFragmentArgs.fromBundle(arguments!!).chr!!

            viewModel.chrID = chr.chrId

            for (r in races) {
                if (r.name == chr.race) {
                    binding.race = r
                    break
                }
            }
            for (c in clases) {
                if (c.name == chr.clas) {
                    binding.race = c
                    break
                }
            }
            binding.imageUrl = chr.imageUrl
            binding.nameEditText.setText(chr.name)



            for (r in races) {
                if (r.name == chr.race) {
                    binding.raceSpinner.setSelection(races.indexOf(r)+1)
                    break
                }
            }
            for (c in clases) {
                if (c.name == chr.clas) {
                    binding.clasSpinner.setSelection(clases.indexOf(c) + 1)
                    break
                }
            }
        }

        return binding.root
    }


}
