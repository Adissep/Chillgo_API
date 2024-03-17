package com.capstone.chillgoapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.capstone.chillgoapp.data.repository.PlacesRepository
import com.capstone.chillgoapp.data.response.UmkmResponse
import com.capstone.chillgoapp.ui.common.UiState
import retrofit2.HttpException
import java.io.IOException

class UmkmPagingSource(
    private val repository: PlacesRepository,
    private val filter: String,
) : PagingSource<Int, UmkmResponse>() {

    private lateinit var data: List<UmkmResponse>

    override fun getRefreshKey(state: PagingState<Int, UmkmResponse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UmkmResponse> {
        return try {
            val currentPage = params.key ?: 1

            repository.getAllUmkm(currentPage).collect {
                data = when (it) {
                    is UiState.Loading -> listOf()
                    is UiState.Error -> listOf()
                    UiState.Init -> listOf()
                    is UiState.Success -> {
                        it.data.data.filter { d -> d.city.contains(filter) }
                    }
                }
            }

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = if (data.isNotEmpty()) currentPage + 1 else null,
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}