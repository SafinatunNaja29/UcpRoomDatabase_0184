package com.example.ucp2.ui.view.matakuliah

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.HomeMataKuliahUiState
import com.example.ucp2.ui.viewmodel.HomeMataKuliahViewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeMataKuliahView(
    onBack: () -> Unit = { },
    viewModel: HomeMataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddMataKuliah: () -> Unit = { },
    onDetailClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Daftar Mata Kuliah",
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddMataKuliah,
                shape = MaterialTheme.shapes.medium,
                modifier = modifier.padding(16.dp),
                containerColor = Color(0xFF006400)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Mata Kuliah",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        val homeMataKuliahUiState by viewModel.homeMataKuliahUiState.collectAsState()

        BodyHomeMataKuliahView(
            homeMataKuliahUiState = homeMataKuliahUiState,
            onClick = {
                onDetailClick(it)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyHomeMataKuliahView(
    homeMataKuliahUiState: HomeMataKuliahUiState,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    when {
        homeMataKuliahUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        homeMataKuliahUiState.isError -> {
            LaunchedEffect(homeMataKuliahUiState.errorMessage) {
                homeMataKuliahUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }

        homeMataKuliahUiState.listMataKuliah.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data Matakuliah belum ditambahkan.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            ListMataKuliah(
                listMataKuliah = homeMataKuliahUiState.listMataKuliah,
                onClick = {
                    onClick(it)
                    println(it)
                },
                modifier = modifier
                    .background(Color(0xFFececec))
            )
        }
    }
}

@Composable
fun ListMataKuliah(
    listMataKuliah: List<MataKuliah>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = listMataKuliah,
            itemContent = { mataKuliah ->
                CardMataKuliah(
                    mataKuliah = mataKuliah,
                    onClick = { onClick(mataKuliah.kode) }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMataKuliah(
    mataKuliah: MataKuliah,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = mataKuliah.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Kode: ${mataKuliah.kode}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SKS: ${mataKuliah.sks}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
        }
    }
}