package com.runningapp.app.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.runningapp.app.common.Resource
import com.runningapp.app.data.UserPreferences
import com.runningapp.app.domain.use_case.GetAllSharedRunActivitiesUseCase
import com.runningapp.app.domain.use_case.GetAllUserChallengesUseCase
import com.runningapp.app.domain.use_case.GetAllUserRunActivitiesUseCase
import com.runningapp.app.ui.utils.RunListState
import com.runningapp.app.ui.utils.UserChallengesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAllUserRunActivitiesUseCase: GetAllUserRunActivitiesUseCase,
    userPreferences: UserPreferences
) : ViewModel() {

    private val _state = mutableStateOf(RunListState())
    val state: State<RunListState> = _state

    var userId: LiveData<Int?> = userPreferences.userId.asLiveData()

    fun getAllUserRunActivities(userId: Int) {
        getAllUserRunActivitiesUseCase(userId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = RunListState(runs = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = RunListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = RunListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}