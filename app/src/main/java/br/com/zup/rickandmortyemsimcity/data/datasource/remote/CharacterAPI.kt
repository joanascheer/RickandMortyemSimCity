package br.com.zup.rickandmortyemsimcity.data.datasource.remote

import br.com.zup.rickandmortyemsimcity.data.model.CharacterResponse
import retrofit2.http.GET

interface CharacterAPI {
    @GET("character")
    suspend fun getAllCharactersNetwork(): CharacterResponse
}