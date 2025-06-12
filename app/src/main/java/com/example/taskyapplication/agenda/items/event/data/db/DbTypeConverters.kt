package com.example.taskyapplication.agenda.items.event.data.db

import androidx.room.TypeConverter

class DbTypeConverters {

    @TypeConverter
    fun fromList(items: List<String>?): String? {
        return items?.joinToString(",")
    }

    @TypeConverter
    fun toList(value: String?): List<String>? {
        return value?.split(",")?.map { it.trim() }
    }
}
