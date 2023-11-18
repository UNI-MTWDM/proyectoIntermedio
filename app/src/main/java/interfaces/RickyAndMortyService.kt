package interfaces

import modelos.RickAndMortyCharacter
import retrofit2.http.GET
import retrofit2.http.Path


interface RickyAndMortyService{
    @GET("{id}")
    suspend fun getCharacterInfo(@Path("id") id: String): RickAndMortyCharacter
}