package br.com.zup.rickandmortyemsimcity.data.datasource.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult

interface CharacterDAO {

    @Query("SELECT * FROM characters ORDER BY name ASC")
    fun getAllCharacters(): List<CharacterResult>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCharacters(charactersList: List<CharacterResult>)

}