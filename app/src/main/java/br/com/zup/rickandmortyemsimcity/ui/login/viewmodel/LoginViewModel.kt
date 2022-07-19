package br.com.zup.rickandmortyemsimcity.ui.login.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.rickandmortyemsimcity.EMAIL_ERROR_MESSAGE
import br.com.zup.rickandmortyemsimcity.LOGIN_ERROR_MESSAGE
import br.com.zup.rickandmortyemsimcity.NAME_ERROR_MESSAGE
import br.com.zup.rickandmortyemsimcity.PASSWORD_ERROR_MESSAGE
import br.com.zup.rickandmortyemsimcity.data.datasource.local.CharacterDAO
import br.com.zup.rickandmortyemsimcity.data.datasource.local.CharacterDatabase
import br.com.zup.rickandmortyemsimcity.domain.model.User
import br.com.zup.rickandmortyemsimcity.domain.repository.CharacterRepository
import br.com.zup.rickandmortyemsimcity.domain.usecase.CharacterUseCase

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val characterDao = CharacterDatabase.getDatabase(application).characterDao()
    private val characterRepository = CharacterRepository(characterDao)

    private var _loginState = MutableLiveData<User>()
    val loginState: LiveData<User> = _loginState
    private var _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    fun getUser() = characterRepository.getCurrentUser()

    private fun login(user: User) {
        try {
            characterRepository.loginUser(user.email, user.password).addOnSuccessListener {
                _loginState.value = user
            }.addOnFailureListener {
                _errorState.value = LOGIN_ERROR_MESSAGE
            }

        } catch (e: Exception) {
            _errorState.value = e.message
        }
    }

    fun validateData(user: User) {
        when {
            user.email.isEmpty() -> {
                _errorState.value = EMAIL_ERROR_MESSAGE
            }
            user.password.isEmpty() -> {
                _errorState.value = PASSWORD_ERROR_MESSAGE
            }
            else -> {
                login(user)
            }
        }
    }


}