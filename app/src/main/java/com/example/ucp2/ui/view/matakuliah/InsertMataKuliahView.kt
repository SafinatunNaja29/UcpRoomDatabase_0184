package com.example.ucp2.ui.view.matakuliah

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.navigation.AlamatNavigasi
import com.example.ucp2.ui.viewmodel.InsertMataKuliahViewModel
import com.example.ucp2.ui.viewmodel.MataKuliahErrorState
import com.example.ucp2.ui.viewmodel.MataKuliahEvent
import com.example.ucp2.ui.viewmodel.MataKuliahUiState
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertMataKuliah: AlamatNavigasi {
    override val route: String = "insertmatakuliah"
}

@Composable
fun InsertMataKuliahView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertMataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.mataKuliahState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val dosenList by viewModel.dosenListState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchDosenList()
    }

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Mata Kuliah"
            )
            InsertBodyMataKuliah(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateMataKuliahState(updateEvent)
                },
                dosenList = dosenList,
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveDataMataKuliah()
                    }
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyMataKuliah(
    modifier: Modifier = Modifier,
    dosenList: List<Dosen>,
    onValueChange: (MataKuliahEvent) -> Unit,
    uiState: MataKuliahUiState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        FormMataKuliah(
            mataKuliahEvent = uiState.mataKuliahEvent,
            onValueChange = onValueChange,
            dosenList = dosenList,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFF006400),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Simpan",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}


@Composable
fun FormMataKuliah(
    mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    dosenList: List<Dosen>,
    onValueChange: (MataKuliahEvent) -> Unit = {},
    errorState: MataKuliahErrorState = MataKuliahErrorState(),
    modifier: Modifier = Modifier
) {
    val semesterOptions = listOf("Ganjil", "Genap")
    val jenisOptions = listOf("Wajib", "Peminatan")
    var selectedDosen by remember { mutableStateOf(mataKuliahEvent.dosenPengampu) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {

        OutlinedTextField(
            value = mataKuliahEvent.nama,
            onValueChange = { onValueChange(mataKuliahEvent.copy(nama = it)) },
            label = { Text("Nama Mata Kuliah") },
            placeholder = { Text("Masukkan Nama Mata Kuliah") },
            isError = errorState.nama != null,
            modifier = Modifier.fillMaxWidth()
        )
        errorState.nama?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }


        OutlinedTextField(
            value = mataKuliahEvent.kode,
            onValueChange = { onValueChange(mataKuliahEvent.copy(kode = it)) },
            label = { Text("Kode Mata Kuliah") },
            placeholder = { Text("Masukkan Kode Mata Kuliah") },
            isError = errorState.kode != null,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        errorState.kode?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }


        Text("Semester", style = MaterialTheme.typography.bodyMedium)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            semesterOptions.forEach { option ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = mataKuliahEvent.semester == option,
                        onClick = { onValueChange(mataKuliahEvent.copy(semester = option)) }
                    )
                    Text(text = option)
                }
            }
        }


        OutlinedTextField(
            value = mataKuliahEvent.sks,
            onValueChange = { onValueChange(mataKuliahEvent.copy(sks = it)) },
            label = { Text("SKS") },
            placeholder = { Text("Masukkan Jumlah SKS") },
            isError = errorState.sks != null,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        errorState.sks?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }


        Text("Jenis Mata Kuliah", style = MaterialTheme.typography.bodyMedium)
        jenisOptions.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = mataKuliahEvent.jenis == option,
                    onClick = { onValueChange(mataKuliahEvent.copy(jenis = option)) }
                )
                Text(text = option)
            }
        }


        Text("Dosen Pengampu", style = MaterialTheme.typography.bodyMedium)
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedDosen,
                onValueChange = {},
                label = { Text("Pilih Dosen Pengampu") },
                placeholder = { Text("Klik untuk memilih dosen") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { expanded = true }
                    )
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dosenList.forEach { dosen ->
                    DropdownMenuItem(
                        text = { Text(dosen.nama) },
                        onClick = {
                            selectedDosen = dosen.nama
                            onValueChange(mataKuliahEvent.copy(dosenPengampu = dosen.nama))
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}