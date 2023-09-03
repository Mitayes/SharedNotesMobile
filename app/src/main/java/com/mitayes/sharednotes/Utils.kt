package com.mitayes.sharednotes

import android.util.Log
import androidx.room.TypeConverter
import com.mitayes.sharednotes.presentation.MyApplication
import java.util.Date
import kotlin.math.roundToInt

fun doIf(predicate: Boolean, action: (Unit) -> Unit) {
    if (predicate) {
        action.invoke(Unit)
    }
}

fun logD(message: String){
    Log.d("CustomLog", message)
}

fun logE(message: String){
    Log.e("CustomLog", message)
}

fun Float.toDp() = this * MyApplication.appContext.resources.displayMetrics.density
fun Int.toDp() = this * MyApplication.appContext.resources.displayMetrics.density.roundToInt()

@TypeConverter
fun fromTimestamp(value: Long?): Date? {
    return value?.let { Date(it) }
}

@TypeConverter
fun dateToTimestamp(date: Date?): Long? {
    return date?.time
}
