package br.com.zup.rickandmortyemsimcity.ui.register.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.zup.rickandmortyemsimcity.CREATE_USER_ERROR_MESSAGE
import br.com.zup.rickandmortyemsimcity.EMAIL_ERROR_MESSAGE
import br.com.zup.rickandmortyemsimcity.NAME_ERROR_MESSAGE
import br.com.zup.rickandmortyemsimcity.PASSWORD_ERROR_MESSAGE
import br.com.zup.rickandmortyemsimcity.data.datasource.local.CharacterDatabase
import br.com.zup.rickandmortyemsimcity.domain.model.User
import br.com.zup.rickandmortyemsimcity.domain.repository.CharacterRepository
import br.com.zup.rickandmortyemsimcity.domain.usecase.CharacterUseCase

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val characterDao = CharacterDatabase.getDatabase(application).characterDao()
    private val characterRepository = CharacterRepository(characterDao)

    private var _registerState = MutableLiveData<User>()
    val registerState: LiveData<User> = _registerState

    private var _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    private fun registerUser(user: User) {
        try {
            characterRepository.registerUser(user.email, user.password).addOnSuccessListener {
                characterRepository.updateUserName(user.name)?.addOnSuccessListener {
                    _registerState.value = user
                }
            }.addOnFailureListener {
                _errorState.value = CREATE_USER_ERROR_MESSAGE
            }
        } catch (e: Exception) {
            _errorState.value = e.message
        }
    }

    fun validateData(user: User) {
        when {
            user.name.isEmpty() -> {
                _errorState.value = NAME_ERROR_MESSAGE
            }
            user.email.isEmpty() -> {
                _errorState.value = EMAIL_ERROR_MESSAGE
            }
            user.password.isEmpty() -> {
                _errorState.value = PASSWORD_ERROR_MESSAGE
            }
            else -> {
                registerUser(user)
            }
        }
    }

}
