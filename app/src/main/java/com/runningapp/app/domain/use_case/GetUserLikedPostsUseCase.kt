package com.runningapp.app.domain.use_case

import com.runningapp.app.common.Resource
import com.runningapp.app.data.remote.dto.toLikes
import com.runningapp.app.domain.model.Likes
import com.runningapp.app.domain.repository.RunRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetUserLikedPostsUseCase @Inject constructor(
    private val repository: RunRepository
) {

    operator fun invoke(userId: Int): Flow<Resource<List<Likes>>> = flow {
        try {
            emit(Resource.Loading<List<Likes>>())
            val likedActivities = repository.getUserLikedPosts(userId).map { it.toLikes() }
            emit(Resource.Success<List<Likes>>(likedActivities))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Likes>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Likes>>("Couldn't reach server. Check your internet connection."))
        }
    }
}