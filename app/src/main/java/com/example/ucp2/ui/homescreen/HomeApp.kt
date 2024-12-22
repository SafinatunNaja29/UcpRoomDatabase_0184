package com.example.ucp2.ui.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2.R

@Composable
fun HomeApp(
    onHalamanHomeDosen: () -> Unit,
    onHalamanHomeMataKuliah: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFbbbbbb)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Image(
            painter = painterResource(
                id = R.drawable.logoumy
            ),
            contentDescription = "Logo UMY",
            modifier = Modifier
                .size(200.dp) //
                .padding(bottom = 24.dp)
        )

        Text(
            text = "UNIVERSITAS MUHAMMADIYAH YOGYAKARTA",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "Selamat Datang",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Button(
            onClick = { onHalamanHomeDosen() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            )
        ) {
            Text(
                text = "Dosen",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }


        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = { onHalamanHomeMataKuliah() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            )
        ) {
            Text(
                text = "Matakuliah",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}