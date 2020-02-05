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
import org.ieselcaminas.aitor.pocketrol.R
import org.ieselcaminas.aitor.pocketrol.databinding.FragmentCharactersBinding

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var adapter: CharacterAdapter
    private val viewModel by lazy { ViewModelProviders.of(this).get(CharactersViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get a reference to the binding object and inflate the fragment views.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false)

        //RecyclerView
        binding.chrRecyclerView.layoutManager = LinearLayoutManager(activity)

        adapter = CharacterAdapter(CharacterListener {chrId ->
            viewModel.onCharacterClicked(chrId)
        })
        binding.chrRecyclerView.adapter = adapter

        observeData()
        observeClicker()

        return binding.root
    }

    fun observeData() {
        binding.shimmerViewContainer.startShimmer() //Shimmer is a charges animation

        viewModel.characters.observe(viewLifecycleOwner, Observer {
            binding.shimmerViewContainer.visibility = View.GONE
            binding.shimmerViewContainer.stopShimmer()

            it?.let {
                adapter.addAndSubmitList(it)
            }
        })
    }

    fun observeClicker() {
        viewModel.navigateToCharacterCard.observe(viewLifecycleOwner, Observer { chr ->
            chr?.let {
                this.findNavController().navigate(
                    CharactersFragmentDirections
                        .actionCharactersFragmentToCharacterCardFragment(chr))
                viewModel.onCharacterCardNavigated()
            }
        })
    }

}
