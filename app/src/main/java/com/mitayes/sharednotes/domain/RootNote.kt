package com.mitayes.sharednotes.domain

data class RootNote(
    val uuid: String = "",
    val name: String,
    val description: String = "",
    val public: Int
)

