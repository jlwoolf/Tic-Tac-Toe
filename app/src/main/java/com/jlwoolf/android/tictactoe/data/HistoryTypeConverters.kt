package com.jlwoolf.android.tictactoe.data

import androidx.room.TypeConverter
import java.util.*

//list of type converters to handle java to Room/SQL? conversion
class HistoryTypeConverters {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromOutcome(outcome: Outcome?): String? {
        return when(outcome) {
            Outcome.WIN -> "WIN"
            Outcome.LOSS -> "LOSS"
            Outcome.DRAW -> "DRAW"
            else -> null
        }
    }

    @TypeConverter
    fun toOutcome(outcome: String?): Outcome? {
        return when(outcome) {
            "WIN" -> Outcome.WIN
            "LOSS" -> Outcome.LOSS
            "DRAW" -> Outcome.DRAW
            else -> null
        }
    }
}