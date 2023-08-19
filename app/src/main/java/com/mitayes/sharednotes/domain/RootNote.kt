package com.mitayes.sharednotes.domain

data class RootNote(
    val uuid: String = "",
    var name: String,
    var description: String = "",
    var shared: Boolean
):java.io.Serializable

