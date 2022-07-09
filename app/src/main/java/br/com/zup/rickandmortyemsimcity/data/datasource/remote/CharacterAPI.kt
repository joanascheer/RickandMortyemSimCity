package br.com.zup.rickandmortyemsimcity.data.datasource.remote

import br.com.zup.rickandmortyemsimcity.data.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterAPI {
    @GET("character")
    suspend fun getAllCharactersNetwork(
        @Query("page")
        page: Int = 22
    ) : CharacterResponse
}