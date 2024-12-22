package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMataKuliah
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeMataKuliahViewModel(
    private val repositoryMataKuliah: RepositoryMataKuliah
) : ViewModel(){
    val homeMataKuliahUiState: StateFlow<HomeMataKuliahUiState> = repositoryMataKuliah.getAllMataKuliah()
        .filterNotNull()
        .map {
            HomeMataKuliahUiState(
                listMataKuliah = it.toList(),
                isLoading = false
            )
        }
        .onStart {
            emit(HomeMataKuliahUiState(isLoading = true))
            delay(2000)
        }
        .catch {
            emit(
                HomeMataKuliahUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeMataKuliahUiState(
                isLoading = true
            )
        )

}

data class HomeMataKuliahUiState(
    val listMataKuliah: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)