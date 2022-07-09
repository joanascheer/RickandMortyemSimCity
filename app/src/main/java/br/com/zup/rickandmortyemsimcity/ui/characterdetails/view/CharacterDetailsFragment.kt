package br.com.zup.rickandmortyemsimcity.ui.characterdetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import br.com.zup.rickandmortyemsimcity.*
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult
import br.com.zup.rickandmortyemsimcity.databinding.FragmentCharacterDetailsBinding
import br.com.zup.rickandmortyemsimcity.ui.characterfavoritelist.CharacterFavoriteListAdapter
import br.com.zup.rickandmortyemsimcity.ui.characterfavoritelist.viewmodel.CharacterFavoriteListViewModel
import br.com.zup.rickandmortyemsimcity.ui.home.view.HomeActivity
import br.com.zup.rickandmortyemsimcity.ui.viewstate.ViewState
import com.squareup.picasso.Picasso

class CharacterDetailsFragment(

) : Fragment() {
    private lateinit var binding: FragmentCharacterDetailsBinding

    private val viewModel: CharacterFavoriteListViewModel by lazy {
        ViewModelProvider(this)[CharacterFavoriteListViewModel::class.java]
    }

    private val adapter: CharacterFavoriteListAdapter by lazy {
        CharacterFavoriteListAdapter(arrayListOf(), this::goToCharacterDetails, this::unfavoriteCharacter)
    }

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
        initObserver()
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
            favoriteCharacterUpdate(character)
            viewModel.unfavoriteCharacter(character)
            adapter.notifyDataSetChanged()

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

    private fun initObserver() {
        viewModel.characterFavoriteState.observe(this.viewLifecycleOwner) {
            when (it) {
                is ViewState.Success -> {
                    Toast.makeText(
                        context,
                        "O personagem ${it.data.name} foi favoritado com sucesso",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is ViewState.Error -> {
                    Toast.makeText(
                        context,
                        "${it.throwable.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is ViewState.EmptyList -> {
                    Toast.makeText(
                        context,
                        EMPTY_LIST_MSG,
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {}
            }
        }

    }

    private fun goToCharacterDetails(characterResult: CharacterResult) {
        val bundle = bundleOf(CHARACTER_KEY to characterResult)

        NavHostFragment.findNavController(this).navigate(
            R.id.action_characterFavoriteListFragment_to_characterDetailsFragment, bundle
        )
    }

    private fun favoriteCharacterUpdate(character: CharacterResult) {
        viewModel.updateCharacterFavorite(character)
        //cor estrela
    }

    private fun unfavoriteCharacter(character: CharacterResult) {
        viewModel.unfavoriteCharacter(character)
    }

    private fun customAppBar() {
        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}