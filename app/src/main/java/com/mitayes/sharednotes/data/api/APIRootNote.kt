package com.mitayes.sharednotes.data.api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.mitayes.sharednotes.domain.RootNote

data class APIRootNote(
    val uuid: String,
    val name: String,
    val description: String,
    val public: Int,
    @SerializedName("user_uuid")
    val userUUID: String
){
    private val gson by lazy { Gson() }
    constructor(note: RootNote, userUUID: String): this(
        note.uuid,
        note.name,
        note.description,
        if (note.shared) 1 else 0,
        userUUID
    )

    fun toJsonString(): String{
        return gson.toJson(this)
    }
}
