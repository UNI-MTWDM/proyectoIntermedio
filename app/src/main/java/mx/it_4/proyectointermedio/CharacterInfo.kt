package mx.it_4.proyectointermedio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelos.Character
import modelos.CharacterApi
import modelos.Serialization

class CharacterInfo:AppCompatActivity() {
    private lateinit var api: CharacterApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_info)
        api = CharacterApi()

        val serialized = intent.getByteArrayExtra("data")
        val character: Character? = serialized?.let { Serialization.deserializeCharacter(it) }

        //El objeto {character} ya contiene toda la info, por lo que no es necesario hacer uso del endpoint que busca por id
        //showDetail(character)

        //Búsqueda por id
        character?.let { searchCharacter(it.id) }
    }

    fun searchCharacter(id: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val character = api.fetchCharacterById(id)
                withContext(Dispatchers.Main) {
                    showDetail(character)
                }
            } catch (e: Exception) {
                Log.d("Search by id error: ", e.message.toString())
            } finally {
                val progressBar = findViewById<ProgressBar>(R.id.progressBar)
                progressBar.visibility = View.GONE
            }
        }
    }

    fun showDetail(data: Character?) {
        if (data != null) {
            val name = findViewById<TextView>(R.id.textViewName)
            val status = findViewById<TextView>(R.id.textViewStatus)
            val species = findViewById<TextView>(R.id.textViewSpecies)
            val origin = findViewById<TextView>(R.id.textViewOrigin)
            val location = findViewById<TextView>(R.id.textViewLocation)

            status.text = "Estatus: " + data.status
            name.text = "Nombre: " + data.name
            species.text = "Especie: " + data.species
            origin.text = "Origen: " + data.origin.name
            location.text = "Ubicación: " + data.location.name

            val img = findViewById<ImageView>(R.id.imageViewCharacter)
            Glide.with(this)
                .load(data.image)
                .into(img)

            val btn = findViewById<Button>(R.id.buttonLocation)
            btn.setOnClickListener() {
                val url = data.location.url

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)

                startActivity(intent)
            }
            val btnBack = findViewById<Button>(R.id.buttonHome)
            btnBack.setOnClickListener() {
                val intent = Intent(this, MainActivity::class.java)
                //this will remove CharacterList activity from the stack
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
        }
    }
}