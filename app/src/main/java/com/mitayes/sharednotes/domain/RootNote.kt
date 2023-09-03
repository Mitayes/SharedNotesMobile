package com.mitayes.sharednotes.domain

import android.os.Parcelable
import com.mitayes.sharednotes.domain.sqlite.RootNoteTuple
import com.mitayes.sharednotes.fromTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class RootNote(
    val uuid: String = "",
    var name: String,
    var description: String = "",
    var shared: Boolean,
    var updateDate: Date?,
    var sync: Int,
) : Parcelable {
    constructor(rootNoteTuple: RootNoteTuple) : this(
        rootNoteTuple.uuid,
        rootNoteTuple.name,
        rootNoteTuple.description,
        rootNoteTuple.shared == 1,
        fromTimestamp(rootNoteTuple.updateDate),
        rootNoteTuple.sync,
    )
}