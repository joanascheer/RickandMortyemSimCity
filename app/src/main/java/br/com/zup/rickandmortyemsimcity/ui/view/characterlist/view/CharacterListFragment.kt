package br.com.zup.rickandmortyemsimcity.ui.view.characterlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.zup.rickandmortyemsimcity.R
import br.com.zup.rickandmortyemsimcity.databinding.FragmentCharacterListBinding
import br.com.zup.rickandmortyemsimcity.ui.view.home.HomeActivity


class CharacterListFragment : Fragment() {
    private lateinit var binding: FragmentCharacterListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeActivity).supportActionBar?.title = getString(R.string.rick_and_morty_title)
    }

}