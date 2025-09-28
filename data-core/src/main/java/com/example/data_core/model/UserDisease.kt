package com.example.data_core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "user_disease_history_junction", // Tabla de union
    primaryKeys = ["user_id_fk", "disease_id_fk"],
    indices = [
        Index(value = ["user_id_fk"]),
        Index(value = ["disease_id_fk"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"], // PK de User.kt
            childColumns = ["user_id_fk"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DiseaseInfo::class,
            parentColumns = ["disease_info_id"], // PK (columna) de DiseaseInfo.kt
            childColumns = ["disease_id_fk"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserDiseaseCrossRef(
    @ColumnInfo(name = "user_id_fk")
    val userId: Long,

    @ColumnInfo(name = "disease_id_fk")
    val diseaseInfoId: Long
)