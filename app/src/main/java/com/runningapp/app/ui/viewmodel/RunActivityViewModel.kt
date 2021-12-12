package com.runningapp.app.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.runningapp.app.common.Resource
import com.runningapp.app.data.UserPreferences
import com.runningapp.app.domain.use_case.*
import com.runningapp.app.ui.utils.LikesState
import com.runningapp.app.ui.utils.SaveRunState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class RunActivityViewModel @Inject constructor(
    private val saveRunUseCase: SaveRunUseCase,
    private val shareRunActivitiesUseCase: ShareRunActivityUseCase,
    private val updateLikeUseCase: UpdateLikeUseCase,
    private val getUserLikedPostsUseCase: GetUserLikedPostsUseCase,
    userPreferences: UserPreferences
) : ViewModel() {

    private val _state = mutableStateOf(SaveRunState())
    val state: State<SaveRunState> = _state

    var userId: LiveData<Int?> = userPreferences.userId.asLiveData()

    private val _likesState = mutableStateOf(LikesState())
    val likesState: State<LikesState> = _likesState

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

    fun shareRun(runId: Int) {
        shareRunActivitiesUseCase(runId).onEach { result ->
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

    fun updateLike(userId: Int, runId: Int) {
        updateLikeUseCase(userId, runId).onEach { result ->
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

    fun getUserLikedPosts(userId: Int) {
        getUserLikedPostsUseCase(userId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _likesState.value = LikesState(likedActivities = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _likesState.value = LikesState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _likesState.value = LikesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}