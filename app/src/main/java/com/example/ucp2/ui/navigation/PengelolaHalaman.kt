package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.homescreen.HomeApp
import com.example.ucp2.ui.view.dosen.DestinasiInsertDosen
import com.example.ucp2.ui.view.dosen.HomeDosenView
import com.example.ucp2.ui.view.dosen.InsertDosenView
import com.example.ucp2.ui.view.matakuliah.DestinasiInsertMataKuliah
import com.example.ucp2.ui.view.matakuliah.DetailMataKuliahView
import com.example.ucp2.ui.view.matakuliah.HomeMataKuliahView
import com.example.ucp2.ui.view.matakuliah.InsertMataKuliahView
import com.example.ucp2.ui.view.matakuliah.UpdateMataKuliahView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = AlamatNavigasi.DestinasiHomeApp.route
    ) {

        composable(
            route = AlamatNavigasi.DestinasiHomeApp.route
        ){
            HomeApp(
                onHalamanHomeDosen = {
                    navController.navigate(AlamatNavigasi.DestinasiHomeDosen.route)
                },
                onHalamanHomeMataKuliah = {
                    navController.navigate(AlamatNavigasi.DestinasiHomeMataKuliah.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = AlamatNavigasi.DestinasiHomeDosen.route
        ){
            HomeDosenView(
                onAddDosen = {
                    navController.navigate(DestinasiInsertDosen.route)
                },
                onBack = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertDosen.route
        ){
            InsertDosenView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            route = AlamatNavigasi.DestinasiHomeMataKuliah.route
        ){
            HomeMataKuliahView(
                onDetailClick = { kode ->
                    navController.navigate("${AlamatNavigasi.DestinasiDetailMataKuliah.route}/$kode")
                    println(
                        "PengelolaHalaman: kode = $kode"
                    )
                },
                onBack = {
                    navController.popBackStack()
                },
                onAddMataKuliah = {
                    navController.navigate(DestinasiInsertMataKuliah.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertMataKuliah.route
        ){
            InsertMataKuliahView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }

        composable(
            AlamatNavigasi.DestinasiDetailMataKuliah.routeWithArg,
            arguments = listOf(
                navArgument(AlamatNavigasi.DestinasiDetailMataKuliah.Kode) {
                    type = NavType.StringType
                }
            )
        ){
            val kode = it.arguments?.getString(AlamatNavigasi.DestinasiDetailMataKuliah.Kode)

            kode?.let { kode ->
                DetailMataKuliahView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${AlamatNavigasi.DestinasiUpdateMataKuliah.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            AlamatNavigasi.DestinasiUpdateMataKuliah.routeWithArg,
            arguments = listOf(
                navArgument(AlamatNavigasi.DestinasiUpdateMataKuliah.Kode) {
                    type = NavType.StringType
                }
            )
        ){

            UpdateMataKuliahView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }
    }
}