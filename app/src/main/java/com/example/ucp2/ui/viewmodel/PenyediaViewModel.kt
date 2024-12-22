package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.ui.KrsApp

object PenyediaViewModel {

    val Factory = viewModelFactory {
        initializer {
            InsertDosenViewModel(
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            HomeDosenViewModel(
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            InsertMataKuliahViewModel(
                krsApp().containerApp.repositoryMataKuliah,
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            HomeMataKuliahViewModel(
                krsApp().containerApp.repositoryMataKuliah
            )
        }

        initializer {
            DetailMataKuliahViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMataKuliah
            )
        }

        initializer {
            UpdateMataKuliahViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMataKuliah,
                krsApp().containerApp.repositoryDosen
            )
        }
    }
}

fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)