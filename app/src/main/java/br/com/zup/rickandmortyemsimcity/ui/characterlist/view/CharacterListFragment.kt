package br.com.zup.rickandmortyemsimcity.ui.characterlist.view

import android.os.Bundle
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
import br.com.zup.rickandmortyemsimcity.databinding.FragmentCharacterListBinding
import br.com.zup.rickandmortyemsimcity.ui.characterlist.viewmodel.CharacterViewModel
import br.com.zup.rickandmortyemsimcity.ui.home.view.HomeActivity
import br.com.zup.rickandmortyemsimcity.ui.viewstate.ViewState


class CharacterListFragment : Fragment() {
    private lateinit var binding: FragmentCharacterListBinding

    private val viewModel: CharacterViewModel by lazy {
        ViewModelProvider(this)[CharacterViewModel::class.java]
    }

    private val adapter: CharacterAdapter by lazy {
        CharacterAdapter(arrayListOf(), this::goToCharacterDetails)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        customAppBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeActivity).supportActionBar?.title =
            getString(R.string.rick_and_morty_title)
        initObserver()
        showRecyclerView()
        clickFloatActionBtn()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllCharactersNetwork()
    }

    private fun clickFloatActionBtn() {
        binding.floatingActionButton.setOnClickListener {
            goToCharacterFavoriteList()
        }
    }

    private fun goToCharacterFavoriteList() {
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_characterListFragment_to_characterFavoriteListFragment)
    }

    private fun initObserver() {
        viewModel.characterListState.observe(this.viewLifecycleOwner) {

            when (it) {
                is ViewState.Success -> {
                    adapter.updateCharacterList(it.data.toMutableList())
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
        binding.rvCharacterList.adapter = adapter
        binding.rvCharacterList.layoutManager = GridLayoutManager(context, 2)
    }


    private fun goToCharacterDetails(characterResult: CharacterResult) {
        val bundle = bundleOf(CHARACTER_KEY to characterResult)

        NavHostFragment.findNavController(this).navigate(
            R.id.action_characterListFragment_to_characterDetailsFragment, bundle
        )
    }

    private fun customAppBar() {
        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }


}