package com.example.ucp2.ui.navigation

interface AlamatNavigasi {
    val route: String

    object DestinasiHomeApp: AlamatNavigasi{
        override val route = "homeapp"
    }

    object DestinasiHomeDosen: AlamatNavigasi {
        override val route = "home"
    }

    object DestinasiHomeMataKuliah: AlamatNavigasi {
        override val route = "homematakuliah"
    }

    object DestinasiDetailMataKuliah: AlamatNavigasi {
        override val route = "detailmatakuliah"
        const val Kode = "kode"
        val routeWithArg = "$route/{$Kode}"
    }

    object DestinasiUpdateMataKuliah: AlamatNavigasi {
        override val route = "updatematakuliah"
        const val Kode = "kode"
        val routeWithArg = "$route/{$Kode}"
    }
}