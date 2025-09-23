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
    companion object{
        fun fromMap(map: Map<String, Any?>): DiseaseInfo = DiseaseInfo(
            id = map["id"] as Long,
            diseaseName = map["diseaseName"] as String,
            diseaseInfo = map["diseaseInfo"] as String,
            symptoms = map["symptoms"] as String,
            diseaseSpread = map["diseaseSpread"] as String
        )
    }


}