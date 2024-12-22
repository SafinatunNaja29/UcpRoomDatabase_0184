package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryDosen
import com.example.ucp2.repository.RepositoryMataKuliah
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InsertMataKuliahViewModel(private val repositoryMataKuliah: RepositoryMataKuliah, private val repositoryDosen: RepositoryDosen) : ViewModel() {
    var mataKuliahState by mutableStateOf(MataKuliahUiState())

    val dosenListState: StateFlow<List<Dosen>> = repositoryDosen.getAllDosen()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    var dosenListInsert by mutableStateOf<List<String>>(emptyList())
        private set

    fun fetchDosenList() {
        viewModelScope.launch {
            try {
                repositoryDosen.getAllDosen()
                    .collect { dosenList ->
                        dosenListInsert = dosenList.map { it.nama }
                    }
            } catch (e: Exception) {
                dosenListInsert = emptyList()
            }
        }
    }

    fun updateMataKuliahState(mataKuliahEvent: MataKuliahEvent){
        mataKuliahState = mataKuliahState.copy(
            mataKuliahEvent = mataKuliahEvent
        )
    }

    private fun validateMataKuliahFields(): Boolean{
        val event = mataKuliahState.mataKuliahEvent
        val errorState = MataKuliahErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode Mata Kuliah tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama Mata Kuliah tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "Jumlah SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester Mata Kuliah tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis Mata Kuliah tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )
        mataKuliahState = mataKuliahState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    fun saveDataMataKuliah(){
        val currentEvent = mataKuliahState.mataKuliahEvent
        if (validateMataKuliahFields()){
            viewModelScope.launch {
                try {
                    repositoryMataKuliah.insertMataKuliah(currentEvent.toMataKuliahEntity())
                    mataKuliahState = mataKuliahState.copy(
                        snackBarMessage = "Data Mata Kuliah Berhasil Disimpan",
                        mataKuliahEvent = MataKuliahEvent(),
                        isEntryValid = MataKuliahErrorState()
                    )
                } catch (e: Exception) {
                    mataKuliahState = mataKuliahState.copy(snackBarMessage = "Data Mata Kuliah Gagal Disimpan")
                }
            }
        } else{
            mataKuliahState = mataKuliahState.copy(snackBarMessage = "Input tidak valid. Periksan kembali data mata kuliah Anda.")
        }
    }
    fun  resetSnackBarMessage(){
        mataKuliahState = mataKuliahState.copy(snackBarMessage = null)
    }

}

data class MataKuliahUiState(
    val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    val isEntryValid: MataKuliahErrorState = MataKuliahErrorState(),
    val snackBarMessage: String? = null
)

data class MataKuliahErrorState(
    val kode: String? = null,
    val nama: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenis: String? = null,
    val dosenPengampu: String? = null
){
    fun isValid(): Boolean{
        return kode == null && nama == null && sks == null &&
                semester == null && jenis == null && dosenPengampu == null
    }
}

fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    nama = nama,
    sks = sks,
    semester = semester,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)

data class MataKuliahEvent(
    val kode: String = "",
    val nama: String = "",
    val sks: String = "",
    val semester: String = "",
    val jenis: String = "",
    val dosenPengampu: String = ""
)