package br.com.zup.rickandmortyemsimcity.data.model


import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("results")
    val characterResults: List<CharacterResult>
)