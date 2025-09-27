package com.example.data_core.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "suggestion",
    indices = [Index(value = ["title"], unique = true)]
)
data class Suggestion(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "url") val url: String?
) {
    // Firebase Mapping actualizado y robusto
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "title" to title,
        "description" to description,
        "url" to url
    )

    companion object {
        fun fromMap(map: Map<String, Any?>): Suggestion {
            val id = (map["id"] as? Number)?.toLong() ?: 0L

            return Suggestion(
                id = id,
                title = map["title"] as? String ?: "",
                description = map["description"] as? String ?: "",
                url = map["url"] as? String
            )
        }
    }
}