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
import com.runningapp.app.domain.use_case.GetAllUserChallengesUseCase
import com.runningapp.app.domain.use_case.JoinChallengeUseCase
import com.runningapp.app.domain.use_case.LeaveChallengeUseCase
import com.runningapp.app.ui.utils.ChallengeListState
import com.runningapp.app.ui.utils.JoinLeaveChallengeState
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
    private val leaveChallengeUseCase: LeaveChallengeUseCase,
    userPreferences: UserPreferences
) : ViewModel() {

    private val _state = mutableStateOf(ChallengeListState())
    val state: State<ChallengeListState> = _state

    private val _userChallengesState = mutableStateOf(UserChallengesListState())
    val userChallengesState: State<UserChallengesListState> = _userChallengesState

    private var _joinChallengeState = mutableStateOf(JoinLeaveChallengeState())
    val joinChallengeState: State<JoinLeaveChallengeState> = _joinChallengeState

    private var _leaveChallengeState = mutableStateOf(JoinLeaveChallengeState())
    val leaveChallengeState: State<JoinLeaveChallengeState> = _leaveChallengeState

    var userId: LiveData<Int?> = userPreferences.userId.asLiveData()

    init {
        getAllChallenges()
    }

    fun getAllChallenges() {
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
                    _joinChallengeState.value = JoinLeaveChallengeState(responseBody = result.data)
                }
                is Resource.Error -> {
                    _joinChallengeState.value = JoinLeaveChallengeState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _joinChallengeState.value = JoinLeaveChallengeState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun leaveChallenge(userId: Int, challengeId: Int) {
        leaveChallengeUseCase(userId, challengeId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _leaveChallengeState.value = JoinLeaveChallengeState(responseBody = result.data)
                }
                is Resource.Error -> {
                    _leaveChallengeState.value = JoinLeaveChallengeState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _leaveChallengeState.value = JoinLeaveChallengeState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun clearState() {
        _joinChallengeState.value = JoinLeaveChallengeState(responseBody = null)
        _leaveChallengeState.value = JoinLeaveChallengeState(responseBody = null)
    }

}