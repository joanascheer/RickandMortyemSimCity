package br.com.zup.rickandmortyemsimcity.ui.characterdetails.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.zup.rickandmortyemsimcity.FAIL_UPDATE_CHARACTER_MSG
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult
import br.com.zup.rickandmortyemsimcity.domain.model.SingleLiveEvent
import br.com.zup.rickandmortyemsimcity.domain.usecase.CharacterUseCase
import br.com.zup.rickandmortyemsimcity.ui.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailsViewModel(application: Application): AndroidViewModel(application) {
    private val characterUseCase = CharacterUseCase(application)
    private val characterFavoriteState = SingleLiveEvent<ViewState<CharacterResult>>()

    fun updateFavoriteCharacters(characterResult: CharacterResult) {
        viewModelScope.launch {
            try {
                val character = withContext(Dispatchers.IO) {
                    characterUseCase.updateFavoriteCharacters(characterResult)
                }
                characterFavoriteState.value = character
            } catch (e:Exception) {
                characterFavoriteState.value = ViewState.Error(Throwable(FAIL_UPDATE_CHARACTER_MSG))
            }
        }
    }

    fun updateFavoriteIcon() {

    }

}