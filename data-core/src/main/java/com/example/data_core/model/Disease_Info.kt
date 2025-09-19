package com.example.data_core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "disease")
data class Disease_Info(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "diseaseName") val diseaseName: String,
    @ColumnInfo(name = "diseaseInfo") val diseaseInfo: String,
    @ColumnInfo(name = "symptoms") val symptoms: String,
    @ColumnInfo(name = "diseaseSpread") val diseaseSpread: String
)