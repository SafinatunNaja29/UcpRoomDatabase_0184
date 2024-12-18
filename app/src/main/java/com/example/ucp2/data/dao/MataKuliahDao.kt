package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

@Dao
interface MataKuliahDao {
    @Insert
    suspend fun insertMataKuliah(mataKuliah: MataKuliah)

    @Query("SELECT * FROM mata_kuliah ORDER BY nama ASC")
    suspend fun getAllMataKuliah(): Flow<List<MataKuliah>>

    @Query("SELECT * FROM mata_kuliah WHERE kode = :kode")
    suspend fun getMataKuliah(kode: String): MataKuliah

    @Update
    suspend fun updateMataKuliah(mataKuliah: MataKuliah)

    @Delete
    suspend fun deleteMataKuliah(mataKuliah: MataKuliah)

}