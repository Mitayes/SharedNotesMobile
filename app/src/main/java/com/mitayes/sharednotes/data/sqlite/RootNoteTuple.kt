package com.mitayes.sharednotes.data.sqlite

import androidx.room.ColumnInfo

data class RootNoteTuple(
    val uuid: String,
    var name: String,
    var description: String,
    var shared: Int,
    @ColumnInfo(name = "is_owner")
    val isOwner: Int,
    @ColumnInfo(name = "update_date", defaultValue = "CURRENT_TIMESTAMP")
    val updateDate: Long
)
