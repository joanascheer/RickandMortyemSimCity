package br.com.zup.rickandmortyemsimcity.ui.characterdetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.zup.rickandmortyemsimcity.CHARACTER_KEY
import br.com.zup.rickandmortyemsimcity.JPEG
import br.com.zup.rickandmortyemsimcity.URL_BASE_IMG
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult
import br.com.zup.rickandmortyemsimcity.databinding.FragmentCharacterDetailsBinding
import br.com.zup.rickandmortyemsimcity.ui.home.view.HomeActivity
import com.squareup.picasso.Picasso

class CharacterDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCharacterDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getData() {
        val character = arguments?.getParcelable<CharacterResult>(CHARACTER_KEY)

        character?.let {
            Picasso.get().load(URL_BASE_IMG + it.id + JPEG)
            binding.tvCharacterNameFieldDetail.text = it.name
            binding.tvCharacterStatusFieldDetail.text = it.status
            binding.tvCharacterSpeciesFieldDetail.text = it.species
            binding.tvCharacterGenderFieldDetail.text = it.gender
            (activity as HomeActivity).supportActionBar?.title = it.name
        }
    }

}