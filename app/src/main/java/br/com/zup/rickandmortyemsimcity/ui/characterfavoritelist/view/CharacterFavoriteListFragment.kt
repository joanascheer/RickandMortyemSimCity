package br.com.zup.rickandmortyemsimcity.ui.characterfavoritelist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.zup.rickandmortyemsimcity.R
import br.com.zup.rickandmortyemsimcity.databinding.FragmentCharacterFavoriteListBinding
import br.com.zup.rickandmortyemsimcity.ui.characterfavoritelist.viewmodel.CharacterFavoriteListViewModel
import br.com.zup.rickandmortyemsimcity.ui.home.view.HomeActivity

class CharacterFavoriteListFragment : Fragment() {
    private lateinit var binding: FragmentCharacterFavoriteListBinding
    private val viewModel: CharacterFavoriteListViewModel by lazy {
        ViewModelProvider(this)[CharacterFavoriteListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterFavoriteListBinding.inflate(inflater, container, false)
        customAppBar()
        return binding.root
    }

    private fun customAppBar() {
        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as HomeActivity).supportActionBar?.title = getString(R.string.favorite_txt)
    }

}