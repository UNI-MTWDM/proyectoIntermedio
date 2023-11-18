package modelos

import interfaces.RickyAndMortyService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable

class RickAndMortyApiService{
    private val api: RickyAndMortyService by lazy {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/character/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RickyAndMortyService::class.java)
    }
    suspend fun fetchCharacterInfo(rymId: String): RickAndMortyCharacter{
        return api.getCharacterInfo(rymId)
    }
}

data class RickAndMortyCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String> = emptyList(),
    val url: String,
    val created: String
): Serializable

data class Origin(
    val name: String,
    val url: String
): Serializable

data class Location(
    val name: String,
    val url: String
): Serializable