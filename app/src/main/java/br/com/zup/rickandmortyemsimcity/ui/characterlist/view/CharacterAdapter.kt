package br.com.zup.rickandmortyemsimcity.ui.characterlist.view

import androidx.recyclerview.widget.RecyclerView
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult

class CharacterAdapter(
    private var characterList: MutableList<CharacterResult>,
    private val clickCharacter: (characterResult: CharacterResult) -> Unit,
) {

}