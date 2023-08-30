package com.mitayes.sharednotes.data.sqlite

import androidx.room.ColumnInfo
import com.mitayes.sharednotes.domain.RootNote

data class RootNoteTuple(
    val uuid: String,
    var name: String,
    var description: String,
    var shared: Int,
    @ColumnInfo(name = "is_owner") val isOwner: Int,
){
    fun toRootNote(): RootNote{
        return RootNote(
            uuid = uuid,
            name = name,
            description = description,
            shared = shared == 1
        )
    }
}
