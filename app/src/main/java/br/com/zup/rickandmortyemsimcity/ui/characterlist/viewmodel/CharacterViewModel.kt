package br.com.zup.rickandmortyemsimcity.ui.characterlist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.zup.rickandmortyemsimcity.ERROR_VIEWSTATE_MSG
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult
import br.com.zup.rickandmortyemsimcity.domain.model.SingleLiveEvent
import br.com.zup.rickandmortyemsimcity.domain.usecase.CharacterUseCase
import br.com.zup.rickandmortyemsimcity.ui.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterViewModel(application: Application) : AndroidViewModel(application) {
    private val characterUseCase = CharacterUseCase(application)
    val characterListState = SingleLiveEvent<ViewState<List<CharacterResult>>>()

    fun getAllCharactersNetwork() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    characterUseCase.getAllCharactersNetwork()
                }
                characterListState.value = response
            } catch (e:Exception) {
                characterListState.value =
                    ViewState.Error(Throwable(ERROR_VIEWSTATE_MSG))
            }
        }
    }
}