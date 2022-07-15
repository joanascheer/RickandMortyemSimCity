package br.com.zup.rickandmortyemsimcity.domain.repository

import br.com.zup.rickandmortyemsimcity.data.datasource.local.CharacterDAO
import br.com.zup.rickandmortyemsimcity.data.datasource.remote.RetrofitService
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResponse
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CharacterRepository(private val characterDao: CharacterDAO) {
    private val auth: FirebaseAuth = Firebase.auth

    fun getAllCharacters(): List<CharacterResult> = characterDao.getAllCharacters()

    fun insertAllCharactersDB(charactersList: List<CharacterResult>) {
        characterDao.insertAllCharacters(charactersList)
    }

    suspend fun getAllCharactersNetwork() : CharacterResponse {
        return RetrofitService.apiService.getAllCharactersNetwork()
    }

    fun getAllFavoriteCharacters(): List<CharacterResult> = characterDao.getAllFavoriteCharacters()

    fun updateFavoriteCharacters(character: CharacterResult) {
        characterDao.updateFavoriteCharacters(character)
    }

    fun registerUser (email: String, password: String) : Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun updateUserProfile (name: String ): Task<Void>? {
        val profile = UserProfileChangeRequest.Builder().setDisplayName(name).build()
        return auth.currentUser?.updateProfile(profile)
    }

    fun logoutOut() {
        auth.signOut()
    }

    fun loginUser (email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun getUser() = auth.currentUser



}