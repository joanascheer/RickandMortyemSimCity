package br.com.zup.rickandmortyemsimcity.ui.characterlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import br.com.zup.rickandmortyemsimcity.CHARACTER_KEY
import br.com.zup.rickandmortyemsimcity.R
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult
import br.com.zup.rickandmortyemsimcity.databinding.FragmentCharacterListBinding
import br.com.zup.rickandmortyemsimcity.ui.characterlist.viewmodel.CharacterViewModel
import br.com.zup.rickandmortyemsimcity.ui.home.view.HomeActivity


class CharacterListFragment : Fragment() {
    private lateinit var binding: FragmentCharacterListBinding

    private val viewModel: CharacterViewModel by lazy {
        ViewModelProvider(this)[CharacterViewModel::class.java]
    }

//private val adapter: CharacterAdapter by lazy {
//CharacterAdapter(arrayListOf(), this::goToCharacterDetails)
//}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeActivity).supportActionBar?.title =
            getString(R.string.rick_and_morty_title)
    }

    private fun goToCharacterDetails() {
        val bundle = bundleOf(CHARACTER_KEY to CharacterResult())

        NavHostFragment.findNavController(this).navigate(
            R.id.action_characterListFragment_to_characterDetailsFragment
        )
    }

}