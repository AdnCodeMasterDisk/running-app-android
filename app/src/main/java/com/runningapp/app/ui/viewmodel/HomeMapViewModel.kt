package com.runningapp.app.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.runningapp.app.data.UserPreferences
import com.runningapp.app.domain.use_case.GetAllChallengesUseCase
import com.runningapp.app.domain.use_case.GetAllUserChallengesUseCase
import com.runningapp.app.domain.use_case.JoinChallengeUseCase
import com.runningapp.app.domain.use_case.LeaveChallengeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class HomeMapViewModel @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel() {

    private val _lastKnownLocation = MutableLiveData<LatLng?>(null)
    val lastKnownLocation: LiveData<LatLng?> get() = _lastKnownLocation

    init {
        requestForLastKnownLocation()
    }

    fun requestForLastKnownLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            val coordinates = getCurrentLocation()
            _lastKnownLocation.postValue(coordinates)
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getCurrentLocation() = suspendCoroutine<LatLng> { continuation ->

        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener {
                it?.let { location ->
                    continuation.resume(LatLng(location.latitude, location.longitude))
                } ?: continuation.resumeWithException(Throwable("Not found"))
            }
            .addOnFailureListener(continuation::resumeWithException)
            .addOnCanceledListener {
                continuation.resumeWithException(Throwable("Cancelled"))
            }
    }
}
