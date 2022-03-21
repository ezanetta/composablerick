package com.ezanetta.composablerick.presentation.navigation

object CharacterSheetDestination {
    private const val ROUTE: String = "characterSheet"
    const val CHARACTER_ARG: String = "character"
    const val ROUTE_WITH_ARG: String = "$ROUTE?$CHARACTER_ARG={$CHARACTER_ARG}"

    fun getRouteWithArgument(characterString: String): String {
        return "$ROUTE?$CHARACTER_ARG=$characterString"
    }
}
