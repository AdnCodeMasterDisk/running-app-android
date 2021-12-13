package com.runningapp.app.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.runningapp.app.common.Resource
import com.runningapp.app.data.UserPreferences
import com.runningapp.app.domain.use_case.GetAllUserRunActivitiesUseCase
import com.runningapp.app.domain.use_case.GetUserMonthlyDistanceUseCase
import com.runningapp.app.ui.utils.MonthlyDistanceState
import com.runningapp.app.ui.utils.RunListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyGoalViewModel @Inject constructor(
    private val getUserMonthlyDistanceUseCase: GetUserMonthlyDistanceUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _state = mutableStateOf(MonthlyDistanceState())
    val state: State<MonthlyDistanceState> = _state
    var userId: LiveData<Int?> = userPreferences.userId.asLiveData()
    var monthlyGoalPrefs: LiveData<Int?> = userPreferences.monthlyGoal.asLiveData()

    fun getUserMonthlyDistance(userId: Int) {
        getUserMonthlyDistanceUseCase(userId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MonthlyDistanceState(monthlyDistance = result.data ?: 0L)
                }
                is Resource.Error -> {
                    _state.value = MonthlyDistanceState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = MonthlyDistanceState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveMonthlyGoal(monthlyGoal: Int){
        viewModelScope.launch {
            userPreferences.saveMonthlyGoal(monthlyGoal)
            monthlyGoalPrefs = userPreferences.monthlyGoal.asLiveData()
        }
    }

    fun getMonthlyGoal() {
        monthlyGoalPrefs = userPreferences.monthlyGoal.asLiveData()
    }
}