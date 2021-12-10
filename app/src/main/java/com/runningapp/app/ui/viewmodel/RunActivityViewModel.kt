package com.runningapp.app.ui.viewmodel

import com.runningapp.app.data.remote.dto.RegisterRequestDTO
import com.runningapp.app.domain.use_case.RegisterUserUseCase
import com.runningapp.app.ui.utils.RegisterState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runningapp.app.common.Resource
import com.runningapp.app.data.remote.dto.SaveRunRequestDTO
import com.runningapp.app.domain.use_case.SaveImageUseCase
import com.runningapp.app.domain.use_case.SaveRunUseCase
import com.runningapp.app.ui.utils.SaveRunState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class RunActivityViewModel @Inject constructor(
    private val saveRunUseCase: SaveRunUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(SaveRunState())
    val state: State<SaveRunState> = _state

    fun saveRun(mapFile: MultipartBody.Part, requestBody: MultipartBody.Part) {
        saveRunUseCase(mapFile, requestBody).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = SaveRunState(responseBody = result.data)
                }
                is Resource.Error -> {
                    _state.value = SaveRunState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = SaveRunState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}