package com.mitayes.sharednotes.domain

import android.os.Parcelable
import com.mitayes.sharednotes.data.sqlite.RootNotesDBEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RootNote(
    val uuid: String = "",
    var name: String,
    var description: String = "",
    var shared: Boolean
) : Parcelable {
    fun toRootNotesDbEntity(): RootNotesDBEntity = RootNotesDBEntity(
        uuid = uuid,
        name = name,
        description = description,
        shared = if (shared) { 1 } else { 0 }
    )
}