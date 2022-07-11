package br.com.zup.rickandmortyemsimcity.domain.usecase

import android.app.Application
import br.com.zup.rickandmortyemsimcity.ERROR_VIEWSTATE_MSG
import br.com.zup.rickandmortyemsimcity.FAIL_LOAD_FAVORITE_LIST_MSG
import br.com.zup.rickandmortyemsimcity.FAIL_UPDATE_FAVORITE_LIST_MSG
import br.com.zup.rickandmortyemsimcity.data.datasource.local.CharacterDatabase
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult
import br.com.zup.rickandmortyemsimcity.domain.repository.CharacterRepository
import br.com.zup.rickandmortyemsimcity.ui.viewstate.ViewState

class CharacterUseCase(application: Application) {
    private val characterDao = CharacterDatabase.getDatabase(application).characterDao()
    private val characterRepository = CharacterRepository(characterDao)

    suspend fun getAllCharactersNetwork(): ViewState<List<CharacterResult>> {
        return try {
            val characters = characterRepository.getAllCharactersNetwork()
            characterRepository.insertAllCharactersDB(characters.characterResults)
            ViewState.Success(characters.characterResults)
            getAllCharacters()
        } catch (e: Exception) {
            getAllCharacters()
        }
    }

    suspend fun getAllCharacters(): ViewState<List<CharacterResult>> {
        return try {
            val characters = characterRepository.getAllCharacters()
            if (characters.isEmpty()) {
                ViewState.EmptyList(characters)
            } else {
                ViewState.Success(characters)
            }
        } catch (e: Exception) {
            ViewState.Error(Exception(ERROR_VIEWSTATE_MSG))
        }
    }

    suspend fun getAllFavoriteCharacters(): ViewState<List<CharacterResult>> {
        return try {
            val characters = characterRepository.getAllFavoriteCharacters()
            if (characters.isEmpty()) {
                ViewState.EmptyList(characters)
            } else {
                ViewState.Success(characters)
            }
        } catch (e: Exception) {
            ViewState.Error(Exception(FAIL_LOAD_FAVORITE_LIST_MSG))
        }
    }

    suspend fun updateFavoriteCharacters(character: CharacterResult): ViewState<CharacterResult> {
        return try {
            characterRepository.updateFavoriteCharacters(character)
            ViewState.Success(character)
        } catch (e: Exception) {
            ViewState.Error(Exception(FAIL_UPDATE_FAVORITE_LIST_MSG))
        }
    }


}