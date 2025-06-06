package com.createfuture.takehome.models

data class ApiCharacter(
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val died: String,
    val aliases: List<Int>,
    val tvSeries: List<String>,
    val playedBy: List<String>,
)