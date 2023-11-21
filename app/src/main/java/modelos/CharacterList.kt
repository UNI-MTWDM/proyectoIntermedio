package modelos

import java.io.Serializable

data class CharacterList (
    val info: Info,
    val results: List<Character> = emptyList()
): Serializable