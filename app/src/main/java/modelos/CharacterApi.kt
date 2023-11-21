package modelos

import interfaces.CharacterService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterApi {
    private val api: CharacterService by lazy {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharacterService::class.java)
    }
    suspend fun fetchCharacterById(id: Int): Character {
        return api.getCharacterById(id)
    }
    suspend fun fetchCharacterByName(name: String): CharacterList {
        return api.getCharacterByName(name)
    }
    suspend fun fetchAllCharacters(): CharacterList {
        return api.getAllCharacters()
    }

    suspend fun fetchCharactersByIds(ids: String): CharacterList {
        return api.getCharactersByIds(ids)
    }
}