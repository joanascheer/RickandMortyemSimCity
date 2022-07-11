package br.com.zup.rickandmortyemsimcity.ui.characterdetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.zup.rickandmortyemsimcity.CHARACTER_KEY
import br.com.zup.rickandmortyemsimcity.R
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult
import br.com.zup.rickandmortyemsimcity.databinding.FragmentCharacterDetailsBinding
import br.com.zup.rickandmortyemsimcity.ui.characterfavoritelist.viewmodel.CharacterFavoriteListViewModel
import br.com.zup.rickandmortyemsimcity.ui.home.view.HomeActivity
import com.squareup.picasso.Picasso

class CharacterDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCharacterDetailsBinding

    private val viewModel: CharacterFavoriteListViewModel by lazy {
        ViewModelProvider(this)[CharacterFavoriteListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        customAppBar()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val character = getCharacter()
        character?.let {
            getData(it)
            clickOnFavoriteBtn(it)
        }
        initObserver(character)
    }

    private fun clickOnFavoriteBtn(character: CharacterResult) {

        binding.ivFavorite.setOnClickListener {
            character.isFavorite = !character.isFavorite
            favoriteCharacterUpdate(character)
        }

    }

    private fun setCharacterFavoriteStatus(character: CharacterResult) {
        binding.ivFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                binding.root.context,
                if (character.isFavorite)
                    R.drawable.ic_yellow_star
                else
                    R.drawable.ic_white_star
            )
        )
    }

    private fun getData(character: CharacterResult) {

        character.apply {
            Picasso.get().load(image).into(binding.ivCharacterDetail)
            binding.tvCharacterNameFieldDetail.text = name
            binding.tvCharacterStatusFieldDetail.text = status
            binding.tvCharacterSpeciesFieldDetail.text = species
            binding.tvCharacterGenderFieldDetail.text = gender
            setCharacterFavoriteStatus(character)

            (activity as HomeActivity).supportActionBar?.title = name

        }
    }

    private fun getCharacter(): CharacterResult? {
        return arguments?.getParcelable(CHARACTER_KEY)
    }

    private fun initObserver(character: CharacterResult?) {

        viewModel.characterFavoriteState.observe(this) {
            character?.let {
                setCharacterFavoriteStatus(it)

                if (it.isFavorite) {
                    Toast.makeText(
                        context,
                        "O personagem ${it.name} foi favoritado com sucesso",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "O Personagem ${it.name} foi desfavoritado com sucesso.",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }
    }

    private fun favoriteCharacterUpdate(character: CharacterResult) {
        viewModel.updateCharacterFavorite(character)
    }

    private fun customAppBar() {
        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}