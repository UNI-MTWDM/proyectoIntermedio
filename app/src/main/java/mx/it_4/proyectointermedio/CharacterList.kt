package mx.it_4.proyectointermedio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import modelos.Character
import modelos.CharacterListAdapter
import modelos.Serialization

class CharacterList:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_list)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var data: List<Character>?

        if (intent.hasExtra("data")) {
            val serialized = intent.getByteArrayExtra("data")
            data = serialized?.let { Serialization.deserializeList(it) }

            if (data != null) {

                if (intent.hasExtra("total")) {
                    //Se limita a mostrar un total de personajes, elejido por el usuario
                    val total = intent.getIntExtra("total", 1)
                    data = selectRandomCharacters(data, total)
                }

                val adapter = CharacterListAdapter(data)
                recyclerView.adapter = adapter
            }
        }
    }

    fun selectRandomCharacters(characters: List<Character>, total: Int): List<Character> {
        // Shuffle the list of characters
        val shuffledList = characters.shuffled()

        // Take the first 3 characters
        return shuffledList.take(total)
    }
}