package com.example.data_core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "user_history",
    primaryKeys = ["uid", "disease_id_fk"],
    indices = [
        Index(value = ["uid"]),
        Index(value = ["disease_id_fk"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = DiseaseInfo::class,
            parentColumns = ["disease_info_id"],
            childColumns = ["disease_id_fk"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserHistoryEntry(
    @ColumnInfo(name = "uid") val uid: String,
    @ColumnInfo(name = "disease_id_fk") val diseaseInfoId: Long
)