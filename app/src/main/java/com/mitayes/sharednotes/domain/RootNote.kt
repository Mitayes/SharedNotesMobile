package com.mitayes.sharednotes.domain

data class RootNote(
    val uuid: String = "",
    val name: String,
    val description: String = "",
    val shared: Boolean
):java.io.Serializable

