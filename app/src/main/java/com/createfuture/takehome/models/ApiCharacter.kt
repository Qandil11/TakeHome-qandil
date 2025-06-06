package com.createfuture.takehome.models

data class ApiCharacter(
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val died: String,
    // Qandil: Changed type from List<Int> to List<String> to match API response format.
    // The API returns alias names as strings like ["Ned", "The Quiet Wolf"], not numeric values.
    val aliases: List<String>,
    val tvSeries: List<String>,
    val playedBy: List<String>,
)