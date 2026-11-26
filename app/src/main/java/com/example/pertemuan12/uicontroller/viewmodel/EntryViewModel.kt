package com.example.pertemuan12.uicontroller.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pertemuan12.modeldata.DataSiswa
import com.example.pertemuan12.modeldata.ResponsApi
import com.example.pertemuan12.repositori.RepositoryDataSiswa
import retrofit2.Response

class EntryViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel() {
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    /* Fungsi untuk memvalidasi input */
    private fun validasiInput(uiState: DetailsSiswa = uiStateSiswa.detailsSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    fun updateUiState(detailsSiswa: DetailsSiswa) {
        uiStateSiswa =
            UIStateSiswa(detailsSiswa = detailsSiswa, isEntryValid = validasiInput(detailsSiswa))
    }

    /* Fungsi untuk menyimpan data yang di-entry */
    suspend fun addSiswa(): Boolean {
        if (validasiInput()) {
            return try {
                val sip: Response<ResponsApi> = repositoryDataSiswa.postDataSiswa(uiStateSiswa.detailsSiswa.toDataSiswa())
                sip.isSuccessful && sip.body()?.message != null
            } catch (e: Exception) {
                false
            }
        }
        return false
    }
}

data class UIStateSiswa(
    val detailsSiswa: DetailsSiswa = DetailsSiswa(),
    val isEntryValid: Boolean = false
)

data class DetailsSiswa(
    val id: Int = 0,
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = "",
)

fun DetailsSiswa.toDataSiswa(): DataSiswa = DataSiswa(
    id = id,
    nama = nama,
    alamat = alamat,
    telpon = telpon
)

fun DataSiswa.toUIStateSiswa(isEntryValid: Boolean = false): UIStateSiswa =
    UIStateSiswa(
        detailsSiswa = this.toDetailsSiswa(),
        isEntryValid = isEntryValid
    )

fun DataSiswa.toDetailsSiswa(): DetailsSiswa = DetailsSiswa(
    id = id,
    nama = nama,
    alamat = alamat,
    telpon = telpon
)
