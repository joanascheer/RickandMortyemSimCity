package br.com.zup.rickandmortyemsimcity.ui.characterfavoritelist.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import br.com.zup.rickandmortyemsimcity.CHARACTER_KEY
import br.com.zup.rickandmortyemsimcity.EMPTY_LIST_MSG
import br.com.zup.rickandmortyemsimcity.R
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult
import br.com.zup.rickandmortyemsimcity.databinding.FragmentCharacterFavoriteListBinding
import br.com.zup.rickandmortyemsimcity.ui.characterfavoritelist.CharacterFavoriteListAdapter
import br.com.zup.rickandmortyemsimcity.ui.characterfavoritelist.viewmodel.CharacterFavoriteListViewModel
import br.com.zup.rickandmortyemsimcity.ui.home.view.HomeActivity
import br.com.zup.rickandmortyemsimcity.ui.viewstate.ViewState

class CharacterFavoriteListFragment : Fragment() {
    private lateinit var binding: FragmentCharacterFavoriteListBinding

    private val viewModel: CharacterFavoriteListViewModel by lazy {
        ViewModelProvider(this)[CharacterFavoriteListViewModel::class.java]
    }

    private val adapter: CharacterFavoriteListAdapter by lazy {
        CharacterFavoriteListAdapter(arrayListOf(), this::goToCharacterDetail, this::unfavoriteCharacter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterFavoriteListBinding.inflate(inflater, container, false)
        customAppBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllFavoriteCharacters()
    }

    private fun customAppBar() {
        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as HomeActivity).supportActionBar?.title = getString(R.string.favorite_txt)
    }

    private fun initObserver() {
        viewModel.characterFavoriteListState.observe(this.viewLifecycleOwner) {
            when (it) {
                is ViewState.Success -> {
                    Log.i("LISTA", "${it.data}")
                    adapter.updateFavoriteList(it.data.toMutableList())
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

    private fun showRecyclerView() {
        initObserver()
        binding.rvCharacterFavoriteList.adapter = adapter
        binding.rvCharacterFavoriteList.layoutManager = GridLayoutManager(context, 2)
    }

    private fun goToCharacterDetail(character: CharacterResult) {
        val bundle = bundleOf(CHARACTER_KEY to character)

        NavHostFragment.findNavController(this).navigate(
            R.id.action_characterFavoriteListFragment_to_characterDetailsFragment, bundle
        )
    }

    private fun unfavoriteCharacter(character: CharacterResult) {
        viewModel.unfavoriteCharacter(character)
    }

}