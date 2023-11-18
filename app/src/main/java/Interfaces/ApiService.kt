package Interfaces

import Modelo.RickAndMortyApi
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService{
    @GET("{id}")
    suspend fun getRickAndMortyApi(@Path("id") id: String): RickAndMortyApi
}