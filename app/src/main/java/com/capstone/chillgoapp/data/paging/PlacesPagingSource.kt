package com.capstone.chillgoapp.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.capstone.chillgoapp.data.repository.PlacesRepository
import com.capstone.chillgoapp.data.response.PlaceResponse
import com.capstone.chillgoapp.ui.common.TypePlace
import com.capstone.chillgoapp.ui.common.UiState
import retrofit2.HttpException
import java.io.IOException

class PlacesPagingSource(
    private val repository: PlacesRepository,
    private val filter: String,
) : PagingSource<Int, PlaceResponse>() {
    private lateinit var data: List<PlaceResponse>

    override fun getRefreshKey(state: PagingState<Int, PlaceResponse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlaceResponse> {
        return try {
            val currentPage = params.key ?: 1
            repository.getAllPlace(currentPage).collect {
                data = when (it) {
                    is UiState.Loading -> listOf()
                    is UiState.Error -> listOf()
                    UiState.Init -> listOf()
                    is UiState.Success -> {
                        Log.e("FILTER", filter)
                        it.data.data
                    }
                }
            }

            LoadResult.Page(
                data = data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (data.isNotEmpty()) currentPage + 1 else null,
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}