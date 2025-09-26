package com.example.data_core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "disease")
data class DiseaseInfo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "diseaseName") val diseaseName: String,
    @ColumnInfo(name = "diseaseInfo") val diseaseInfo: String,
    @ColumnInfo(name = "symptoms") val symptoms: String,
    @ColumnInfo(name = "diseaseSpread") val diseaseSpread: String
){

    //Firebase Mapping
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "diseaseName" to diseaseName,
        "diseaseInfo" to diseaseInfo,
        "symptoms" to symptoms,
        "diseaseSpread" to diseaseSpread
    )
    //build config firebase
    companion object {
        fun fromMap(map: Map<String, Any?>): DiseaseInfo {
            val idValue: Long = when (val idFromDb = map["id"]) {
                is Number -> idFromDb.toLong()
                is String -> idFromDb.toLongOrNull() ?: 0L
                else -> 0L
            }

            return DiseaseInfo(
                id = idValue,
                diseaseName = map["diseaseName"] as? String ?: "Sin nombre",
                diseaseInfo = map["diseaseInfo"] as? String ?: "Sin información",
                symptoms = map["symptoms"] as? String ?: "Sin síntomas",
                diseaseSpread = map["diseaseSpread"] as? String ?: "Sin datos de propagación"
            )
        }
    }
}