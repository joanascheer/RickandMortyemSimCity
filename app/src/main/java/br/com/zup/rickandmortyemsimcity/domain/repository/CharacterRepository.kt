package br.com.zup.rickandmortyemsimcity.domain.repository

import br.com.zup.rickandmortyemsimcity.data.datasource.remote.RetrofitService
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResponse

class CharacterRepository {

    suspend fun getAllCharactersNetwork() : CharacterResponse {
        return RetrofitService.apiService.getAllCharactersNetwork()
    }
}