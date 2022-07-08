package br.com.zup.rickandmortyemsimcity.data.model


import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val characterResults: List<CharacterResult>
)