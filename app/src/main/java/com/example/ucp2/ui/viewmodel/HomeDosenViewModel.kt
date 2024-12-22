package com.example.ucp2.ui.viewmodel

import com.example.ucp2.data.entity.Dosen

class HomeDosenViewModel {
}

data class HomeUiState(
    val listDosen: List<Dosen> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)