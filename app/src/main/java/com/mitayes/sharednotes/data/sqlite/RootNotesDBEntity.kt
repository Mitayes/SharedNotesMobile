package com.mitayes.sharednotes.data.sqlite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mitayes.sharednotes.dateToTimestamp
import com.mitayes.sharednotes.domain.RootNote
import java.util.Date

const val ROOT_NOTES_TABLE_NAME = "root_notes"

@Entity(tableName = ROOT_NOTES_TABLE_NAME)
data class RootNotesDBEntity(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "shared") val shared: Int = 0,
    @ColumnInfo(name = "is_owner") val isOwner: Int = 1,
    @ColumnInfo(
        name = "update_date",
        defaultValue = "CURRENT_TIMESTAMP"
    ) val updateDate: Long? = dateToTimestamp(Date())
) {
    constructor(rootNote: RootNote) : this(
        rootNote.uuid,
        rootNote.name,
        rootNote.description,
        if (rootNote.shared) { 1 } else { 0 },
        1,
        dateToTimestamp(rootNote.updateDate)
    )
}
