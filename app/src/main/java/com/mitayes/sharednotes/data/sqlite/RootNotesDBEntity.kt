package com.mitayes.sharednotes.data.sqlite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val ROOT_NOTES_TABLE_NAME = "root_notes"

@Entity(tableName = ROOT_NOTES_TABLE_NAME)
data class RootNotesDBEntity(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "shared") val shared: Int = 0,
    @ColumnInfo(name = "is_owner") val isOwner: Int = 1,
)
