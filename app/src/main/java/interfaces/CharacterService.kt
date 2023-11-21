package interfaces

import modelos.Character
import modelos.CharacterList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {
    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Character

    @GET("character")
    suspend fun getCharacterByName(@Query("name") name: String): CharacterList

    @GET("character")
    suspend fun getAllCharacters(): CharacterList

    @GET("character/{characterIds}")
    suspend fun getCharactersByIds(@Path("characterIds") characterIds: String): CharacterList
}