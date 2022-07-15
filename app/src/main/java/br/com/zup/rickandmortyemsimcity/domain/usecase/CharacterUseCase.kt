package br.com.zup.rickandmortyemsimcity.domain.usecase

import android.app.Application
import br.com.zup.rickandmortyemsimcity.ERROR_VIEWSTATE_MSG
import br.com.zup.rickandmortyemsimcity.FAIL_LOAD_FAVORITE_LIST_MSG
import br.com.zup.rickandmortyemsimcity.FAIL_UPDATE_FAVORITE_LIST_MSG
import br.com.zup.rickandmortyemsimcity.data.datasource.local.CharacterDatabase
import br.com.zup.rickandmortyemsimcity.data.model.CharacterResult
import br.com.zup.rickandmortyemsimcity.domain.repository.CharacterRepository
import br.com.zup.rickandmortyemsimcity.ui.viewstate.ViewState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CharacterUseCase(application: Application) {
    private val characterDao = CharacterDatabase.getDatabase(application).characterDao()
    private val characterRepository = CharacterRepository(characterDao)
    private val auth: FirebaseAuth = Firebase.auth

    suspend fun getAllCharactersNetwork(): ViewState<List<CharacterResult>> {
        return try {
            val characters = characterRepository.getAllCharactersNetwork()
            characterRepository.insertAllCharactersDB(characters.characterResults)
            ViewState.Success(characters.characterResults)
            getAllCharacters()
        } catch (e: Exception) {
            getAllCharacters()
        }
    }

    private fun getAllCharacters(): ViewState<List<CharacterResult>> {
        return try {
            val characters = characterRepository.getAllCharacters()
            if (characters.isEmpty()) {
                ViewState.EmptyList(characters)
            } else {
                ViewState.Success(characters)
            }
        } catch (e: Exception) {
            ViewState.Error(Exception(ERROR_VIEWSTATE_MSG))
        }
    }

    fun getAllFavoriteCharacters(): ViewState<List<CharacterResult>> {
        return try {
            val characters = characterRepository.getAllFavoriteCharacters()
            if (characters.isEmpty()) {
                ViewState.EmptyList(characters)
            } else {
                ViewState.Success(characters)
            }
        } catch (e: Exception) {
            ViewState.Error(Exception(FAIL_LOAD_FAVORITE_LIST_MSG))
        }
    }

    fun updateFavoriteCharacters(character: CharacterResult): ViewState<CharacterResult> {
        return try {
            characterRepository.updateFavoriteCharacters(character)
            ViewState.Success(character)
        } catch (e: Exception) {
            ViewState.Error(Exception(FAIL_UPDATE_FAVORITE_LIST_MSG))
        }
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
        return auth.signInWithEmailAndPassword(email, password)}

    fun getUser() = auth.currentUser

    fun getNameUser(): String? = auth.currentUser?.displayName

    fun getEmailUser(): String? = auth.currentUser?.email


}