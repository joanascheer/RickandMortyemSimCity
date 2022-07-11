package br.com.zup.rickandmortyemsimcity.ui.characterfavoritelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.zup.rickandmortyemsimcity.JPEG
import br.com.zup.rickandmortyemsimcity.URL_BASE_IMG
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult
import br.com.zup.rickandmortyemsimcity.databinding.CharacterItemBinding
import com.squareup.picasso.Picasso

class CharacterFavoriteListAdapter(
    private var characterList: MutableList<CharacterResult> = mutableListOf(),
    private val clickCharacter: (characterResult: CharacterResult) -> Unit
) : RecyclerView.Adapter<CharacterFavoriteListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characterList[position]
        holder.showCharacterInfo(character)
        holder.binding.cvItem.setOnClickListener {
            clickCharacter(character)
        }
    }

    override fun getItemCount() = characterList.size

    fun updateFavoriteList(newList: MutableList<CharacterResult>) {
        characterList = newList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun showCharacterInfo(characterResult: CharacterResult) {
            Picasso.get().load(characterResult.image)
                .into(binding.ivCharacter)
            binding.tvCharacterName.text = characterResult.name
        }

    }
}