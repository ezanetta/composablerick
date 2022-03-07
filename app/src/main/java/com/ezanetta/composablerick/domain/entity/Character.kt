package com.ezanetta.composablerick.domain.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Character(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "status") val status: Status,
    @Json(name = "species") val species: String,
    @Json(name = "type") val type: String,
    @Json(name = "gender") val gender: Gender,
    @Json(name = "location") val location: Location,
    @Json(name = "image") val image: String,
    @Json(name = "episode") val episode: List<String>
)
