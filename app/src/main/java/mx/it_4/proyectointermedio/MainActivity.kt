package mx.it_4.proyectointermedio

import android.content.Intent
import modelos.RickAndMortyApiService
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelos.RickAndMortyCharacter
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var service: RickAndMortyApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        service = RickAndMortyApiService()

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val txtId: EditText = findViewById(R.id.IdPersonaje)
        val btnSearch: Button = findViewById(R.id.SearchButton)

        btnSearch.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            val id = txtId.text.toString()

            GlobalScope.launch (Dispatchers.IO){
                try {
                    val character = service.fetchCharacterInfo(id)
                    Log.i("RyM Id", character.image)

                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.INVISIBLE

                        val intent = Intent(this@MainActivity, CharacterInfo::class.java)
                        val datos = arrayOf(character)
                        intent.putExtra("datos", serializeArray(datos))
                        startActivity(intent)
                    }
                }
                catch (e: Exception){
                    // Manejar el error
                    Log.d("Seccion", "Sección Error")
                    Log.d("Error: ", e.message.toString())
                }
            }
        }
    }

    fun serializeArray(datos: Array<RickAndMortyCharacter>): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(datos)
        objectOutputStream.close()

        return byteArrayOutputStream.toByteArray()
    }
}