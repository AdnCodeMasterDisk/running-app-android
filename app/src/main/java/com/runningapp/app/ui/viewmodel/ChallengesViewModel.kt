package com.runningapp.app.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.runningapp.app.common.Resource
import com.runningapp.app.data.UserPreferences
import com.runningapp.app.domain.use_case.GetAllChallengesUseCase
import com.runningapp.app.domain.use_case.GetAllSharedRunActivitiesUseCase
import com.runningapp.app.domain.use_case.GetAllUserChallengesUseCase
import com.runningapp.app.domain.use_case.JoinChallengeUseCase
import com.runningapp.app.ui.utils.ChallengeListState
import com.runningapp.app.ui.utils.JoinChallengeState
import com.runningapp.app.ui.utils.RunListState
import com.runningapp.app.ui.utils.UserChallengesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChallengesViewModel @Inject constructor(
    private val getAllChallengesUseCase: GetAllChallengesUseCase,
    private val getAllUserChallengesUseCase: GetAllUserChallengesUseCase,
    private val joinChallengeUseCase: JoinChallengeUseCase,
    userPreferences: UserPreferences
) : ViewModel() {

    private val _state = mutableStateOf(ChallengeListState())
    val state: State<ChallengeListState> = _state

    private val _userChallengesState = mutableStateOf(UserChallengesListState())
    val userChallengesState: State<UserChallengesListState> = _userChallengesState

    private val _joinChallengeState = mutableStateOf(JoinChallengeState())
    val joinChallengeState: State<JoinChallengeState> = _joinChallengeState

    var userId: LiveData<Int?> = userPreferences.userId.asLiveData()

    init {
        getAllChallenges()
    }

    private fun getAllChallenges() {
        getAllChallengesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ChallengeListState(challenges = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = ChallengeListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = ChallengeListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getAllUserChallenges(userId: Int) {
        getAllUserChallengesUseCase(userId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _userChallengesState.value = UserChallengesListState(userChallenges = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _userChallengesState.value = UserChallengesListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _userChallengesState.value = UserChallengesListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun joinChallenge(userId: Int, challengeId: Int) {
        joinChallengeUseCase(userId, challengeId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _joinChallengeState.value = JoinChallengeState(responseBody = result.data)
                }
                is Resource.Error -> {
                    _joinChallengeState.value = JoinChallengeState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _joinChallengeState.value = JoinChallengeState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}