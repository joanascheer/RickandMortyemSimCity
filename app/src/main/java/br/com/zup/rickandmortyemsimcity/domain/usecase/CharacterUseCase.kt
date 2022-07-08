package br.com.zup.rickandmortyemsimcity.domain.usecase

import br.com.zup.rickandmortyemsimcity.ERROR_VIEWSTATE_MSG
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult
import br.com.zup.rickandmortyemsimcity.domain.repository.CharacterRepository
import br.com.zup.rickandmortyemsimcity.ui.viewstate.ViewState

class CharacterUseCase {
    private val characterRepository = CharacterRepository()

    suspend fun getAllCharactersNetwork(): ViewState<List<CharacterResult>> {
        return try {
            val characters = characterRepository.getAllCharactersNetwork()
            ViewState.Success(characters.characterResults)
        } catch (e: Exception) {
            ViewState.Error(Exception(ERROR_VIEWSTATE_MSG))
        }
    }

}