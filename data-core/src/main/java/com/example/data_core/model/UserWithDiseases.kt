package com.example.data_core.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithDiseases(
    @Embedded
    val user: User,

    @Relation(
        parentColumn = "id",
        entityColumn = "disease_info_id",
        associateBy = Junction(
            value = UserDiseaseCrossRef::class,
            parentColumn = "user_id_fk",
            entityColumn = "disease_id_fk"
        )
    )
    val diseases: List<DiseaseInfo>
)