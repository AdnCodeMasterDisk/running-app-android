package com.runningapp.app.ui.viewmodel

import com.runningapp.app.data.remote.dto.RegisterRequest
import com.runningapp.app.domain.use_case.RegisterUserUseCase
import com.runningapp.app.ui.utils.RegisterState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runningapp.app.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    fun registerUser(requestBody: RegisterRequest) {
        registerUserUseCase(requestBody).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = RegisterState(responseBody = result.data)
                }
                is Resource.Error -> {
                    _state.value = RegisterState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = RegisterState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}