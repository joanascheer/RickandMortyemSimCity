package br.com.zup.rickandmortyemsimcity.ui.characterdetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import br.com.zup.rickandmortyemsimcity.*
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult
import br.com.zup.rickandmortyemsimcity.databinding.FragmentCharacterDetailsBinding
import br.com.zup.rickandmortyemsimcity.ui.home.view.HomeActivity
import com.squareup.picasso.Picasso

class CharacterDetailsFragment(

) : Fragment() {
    private lateinit var binding: FragmentCharacterDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        customAppBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()

    }

    private fun getData() {
        val character = arguments?.getParcelable<CharacterResult>(CHARACTER_KEY)

        if (character != null) {
            favoriteCharacter(character)
        }

        character?.let {
            Picasso.get().load(URL_BASE_IMG + it.id + JPEG).into(binding.ivCharacterDetail)
            binding.tvCharacterNameFieldDetail.text = it.name
            binding.tvCharacterStatusFieldDetail.text = it.status
            binding.tvCharacterSpeciesFieldDetail.text = it.species
            binding.tvCharacterGenderFieldDetail.text = it.gender
            (activity as HomeActivity).supportActionBar?.title = it.name

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
    }

    private fun favoriteCharacter(character: CharacterResult) {
        binding.ivFavorite.setOnClickListener {
            character.isFavorite = !character.isFavorite

            binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    if (character.isFavorite)
                        R.drawable.ic_yellow_star
                    else
                        R.drawable.ic_white_star
                )
            )

            //enviar objeto de volta pra lista inicial || lista de fav
            val bundle = bundleOf(UPDATED_CHARACTER to character)
//            NavHostFragment.findNavController(this)
//                .navigate(R.id.action_characterDetailsFragment_to_characterListFragment,bundle)
        }
    }

    private fun customAppBar() {
        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


}