package com.example.note.feature_note.domain.util

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromString (value:String): List<String>{
        return value.split("|")
    }

    @TypeConverter
    fun toMyString(value:List<String>): String{
        return value.joinToString("|")
    }
}