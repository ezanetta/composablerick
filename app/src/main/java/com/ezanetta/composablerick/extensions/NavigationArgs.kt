package com.ezanetta.composablerick.extensions

import com.ezanetta.composablerick.domain.entity.Character
import com.squareup.moshi.Moshi

fun fromJsonStringToCharacter(characterString: String): Character? {
    return Moshi.Builder().build().run {
        adapter(Character::class.java)
            .lenient()
            .fromJson(characterString)
    }
}

fun toJsonStringFromCharacter(character: Character): String {
    return Moshi.Builder().build().run {
        adapter(Character::class.java)
            .lenient()
            .toJson(character)
    }
}