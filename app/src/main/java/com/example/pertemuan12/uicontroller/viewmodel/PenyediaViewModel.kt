package com.example.pertemuan12.uicontroller.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pertemuan12.repositori.AplikasiSiswa

fun CreationExtras.aplikasiSiswa(): AplikasiSiswa = (
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiSiswa
)

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(aplikasiSiswa().container.repositoryDataSiswa)
        }
        initializer {
            EntryViewModel(aplikasiSiswa().container.repositoryDataSiswa)
        }
    }
}