package com.example.hydrateme

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

class IntakeModel {
    private val entries: MutableList<IntakeEntry> = ArrayList()

    fun addEntry(entry: IntakeEntry) {
        entries.add(entry)
    }

    fun getTodaysIntake(): Int {
        val today = formatDateString(System.currentTimeMillis())
        return getDailyEntries()[today] ?: 0
    }

    fun isTodaysIntakeSufficient(): Boolean {
        return getTodaysIntake() >= 3000
    }

    fun getDailyEntries(): Map<String, Int> {
        val map: MutableMap<String, Int> = mutableMapOf<String, Int>()
        for(entry in entries) {
            val date = formatDateString(entry.timestamp)

            if(!map.containsKey(date)) {
                map[date] = entry.intake
            } else {
                map[date] = map[date]!!.plus(entry.intake)
            }
        }
        return map
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDateString(ts: Long): String {
        return SimpleDateFormat("dd MMM yyyy").format(ts)
    }
}