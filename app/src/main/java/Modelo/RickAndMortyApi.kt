package Modelo

import Interfaces.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RickAndMortyApiService{
    private val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/character/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
    suspend fun RickAndMortyApi(rymId: String): RickAndMortyApi{
        return api.getRickAndMortyApi(rymId)
    }
}

data class RickAndMortyApi (
    val Id: Int,
    val Name: String,
    val Species: String,
    val Gender: Int
)