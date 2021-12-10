package com.runningapp.app.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runningapp.app.common.Resource
import com.runningapp.app.domain.use_case.GetAllRunsUseCase
import com.runningapp.app.ui.utils.RunListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RunListViewModel @Inject constructor(
    private val getAllRunsUseCase: GetAllRunsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(RunListState())
    val state: State<RunListState> = _state

    init {
        getAllRuns()
    }

    private fun getAllRuns() {
        getAllRunsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = RunListState(runs = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = RunListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = RunListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}