package br.com.zup.rickandmortyemsimcity.domain.repository

import br.com.zup.rickandmortyemsimcity.data.datasource.local.CharacterDAO
import br.com.zup.rickandmortyemsimcity.data.datasource.remote.RetrofitService
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResponse
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult

class CharacterRepository(private val characterDao: CharacterDAO) {

    fun getAllCharacters(): List<CharacterResult> = characterDao.getAllCharacters()

    fun insertAllCharactersDB(charactersList: List<CharacterResult>) {
        characterDao.insertAllCharacters(charactersList)
    }

    suspend fun getAllCharactersNetwork() : CharacterResponse {
        return RetrofitService.apiService.getAllCharactersNetwork()
    }

    suspend fun getAllFavoriteCharacters(): List<CharacterResult> = characterDao.getAllFavoriteCharacters()

    suspend fun updateFavoriteCharacters(character: CharacterResult) {
        characterDao.updateFavoriteCharacters(character)
    }
}