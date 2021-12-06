package com.runningapp.app.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.*
import com.runningapp.app.common.Resource
import com.runningapp.app.data.UserPreferences
import com.runningapp.app.data.remote.dto.LoginRequestDTO
import com.runningapp.app.domain.use_case.LoginUserUseCase
import com.runningapp.app.ui.utils.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state
    var token: LiveData<String?> = userPreferences.authToken.asLiveData()

    fun loginUser(requestBody: LoginRequestDTO) {
        loginUserUseCase(requestBody).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = LoginState(user = result.data)
                    _state.value.user?.let { saveAuthToken(it.token) }
                }
                is Resource.Error -> {
                    _state.value = LoginState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = LoginState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveAuthToken(authToken: String){
        viewModelScope.launch {
            userPreferences.saveAuthToken(authToken)
            token = userPreferences.authToken.asLiveData()
        }
    }

    fun logout(){
        viewModelScope.launch {
            userPreferences.clear()
        }
    }

}