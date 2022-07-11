package br.com.zup.rickandmortyemsimcity.ui.characterfavoritelist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.zup.rickandmortyemsimcity.FAIL_GET_FAVORITE_CHARACTERS
import br.com.zup.rickandmortyemsimcity.FAIL_UPDATE_FAVORITE_LIST_MSG
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult
import br.com.zup.rickandmortyemsimcity.domain.model.SingleLiveEvent
import br.com.zup.rickandmortyemsimcity.domain.usecase.CharacterUseCase
import br.com.zup.rickandmortyemsimcity.ui.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterFavoriteListViewModel(application: Application) : AndroidViewModel(application) {
    private val characterUseCase = CharacterUseCase(application)
    val characterFavoriteState = SingleLiveEvent<ViewState<CharacterResult>>()
    val characterFavoriteListState = SingleLiveEvent<ViewState<List<CharacterResult>>>()

    fun getAllFavoriteCharacters() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    characterUseCase.getAllFavoriteCharacters()
                }
                characterFavoriteListState.value = response
            } catch (e: Exception) {
                characterFavoriteState.value =
                    ViewState.Error(Throwable(FAIL_GET_FAVORITE_CHARACTERS))
            }
        }
    }

    fun updateCharacterFavorite(character: CharacterResult) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    characterUseCase.updateFavoriteCharacters(character)
                }
                characterFavoriteState.value = response
            } catch (ex: Exception) {
                characterFavoriteListState.value =
                    ViewState.Error(Throwable(FAIL_UPDATE_FAVORITE_LIST_MSG))
            }
        }
    }

}