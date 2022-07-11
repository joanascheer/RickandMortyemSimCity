package br.com.zup.rickandmortyemsimcity.data.datasource.local

import androidx.room.*
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult

@Dao
interface CharacterDAO {

    @Query("SELECT * FROM characters ORDER BY name ASC")
    fun getAllCharacters(): List<CharacterResult>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllCharacters(charactersList: List<CharacterResult>)

    @Query("SELECT * FROM characters WHERE isFavorite = 1")
    fun getAllFavoriteCharacters(): List<CharacterResult>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateFavoriteCharacters(character: CharacterResult)

}