package br.com.zup.rickandmortyemsimcity.ui.characterdetails.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.zup.rickandmortyemsimcity.FAIL_UPDATE_CHARACTER_MSG
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult
import br.com.zup.rickandmortyemsimcity.domain.usecase.CharacterUseCase
import br.com.zup.rickandmortyemsimcity.ui.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val characterUseCase = CharacterUseCase(application)
    private val _characterFavoriteStatusState = MutableLiveData<ViewState<CharacterResult>>()
    val characterFavoriteStatusState: LiveData<ViewState<CharacterResult>> =
        _characterFavoriteStatusState

    fun updateFavoriteCharacter(character: CharacterResult) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    characterUseCase.updateFavoriteCharacters(character)
                }
                _characterFavoriteStatusState.value = response
            } catch (e: Exception) {
                ViewState.Error(Exception(FAIL_UPDATE_CHARACTER_MSG))
            }
        }
    }
}