package br.com.zup.rickandmortyemsimcity.ui.characterdetails.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.zup.rickandmortyemsimcity.R
import br.com.zup.rickandmortyemsimcity.databinding.FragmentCharacterDetailsBinding
import br.com.zup.rickandmortyemsimcity.databinding.FragmentCharacterListBinding

class CharacterDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCharacterDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

}