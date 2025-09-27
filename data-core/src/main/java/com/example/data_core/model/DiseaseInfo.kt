package com.example.data_core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "disease",
    indices = [Index(value = ["diseaseName"], unique = true)]
)
data class DiseaseInfo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    //Nombres corregidos y campos añadidos
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "symptoms") val symptoms: String,
    @ColumnInfo(name = "prevention") val prevention: String,
    @ColumnInfo(name = "treatment") val treatment: String
) {

    //Firebase actualizado con los nuevos nombres
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "name" to name,
        "description" to description,
        "symptoms" to symptoms,
        "prevention" to prevention,
        "treatment" to treatment
    )

    // Mapeo desde Firebase actualizado con los nuevos nombres
    companion object {
        fun fromMap(map: Map<String, Any?>): DiseaseInfo {

            val id = (map["id"] as? Number)?.toLong() ?: 0L

            return DiseaseInfo(
                id = id,
                name = map["name"] as? String ?: "",
                description = map["description"] as? String ?: "",
                symptoms = map["symptoms"] as? String ?: "",
                prevention = map["prevention"] as? String ?: "",
                treatment = map["treatment"] as? String ?: ""
            )
        }
    }
}