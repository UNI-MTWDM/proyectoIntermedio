package mx.it_4.proyectointermedio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelos.CharacterApi
import modelos.Serialization

import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var api: CharacterApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        api = CharacterApi()

        val imageView = findViewById<ImageView>(R.id.imageView2)
        imageView.setImageResource(R.drawable.rick_morty)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val btnSearchName: Button = findViewById(R.id.btnSearchName)
        val txtName = findViewById<TextView>(R.id.txtCharacterName)
        btnSearchName.setOnClickListener()
        {
            progressBar.visibility = View.VISIBLE

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val characters = api.fetchCharacterByName(txtName.text.toString())

                    withContext(Dispatchers.Main) {

                        val intent = Intent(this@MainActivity, CharacterList::class.java)
                        intent.putExtra("data", Serialization.serializeList(characters.results))
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    Log.d("Search by name error: ", e.message.toString())
                } finally {
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }
        val txtTotalPersonajes: EditText = findViewById(R.id.txtTotalPersonajes)
        val btnSearchMultiple: Button = findViewById(R.id.btnSearchMany)
        btnSearchMultiple.setOnClickListener()
        {
            progressBar.visibility = View.VISIBLE

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    //Función para selección aleatoria de ids de personajes, basada en el total elejida por el usuario
                    var characterIds = generateRandomList(
                        size = txtTotalPersonajes.text.toString().toInt(),
                        minValue = 1,
                        maxValue = 826//Id max en la API
                    )
                    Log.d("Ids a buscar", characterIds.joinToString(","))

                    //No funciona pasandole arreglo de ids
                    //val characters = api.fetchCharactersByIds(characterIds.joinToString(","))

                    //Mientras solucionamos la búsqueda por arreglo de ids, mostraremos todo y realizamos selección aleatoria basada en el total que elija el usuario
                    //Validar que el usuario no elija más de 20, la API solo proporciona páginas con 20 personajes
                    val characters = api.fetchAllCharacters()

                    withContext(Dispatchers.Main) {

                        val intent = Intent(this@MainActivity, CharacterList::class.java)
                        intent.putExtra("total", txtTotalPersonajes.text.toString().toInt())
                        intent.putExtra("data", Serialization.serializeList(characters.results))
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    Log.d("Search by name error: ", e.message.toString())
                } finally {
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }
        val btnSearch: Button = findViewById(R.id.btnSearch)
        btnSearch.setOnClickListener()
        {
            progressBar.visibility = View.VISIBLE

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val characters = api.fetchAllCharacters()

                    withContext(Dispatchers.Main) {

                        val intent = Intent(this@MainActivity, CharacterList::class.java)
                        intent.putExtra("data", Serialization.serializeList(characters.results))
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    Log.d("Search by name error: ", e.message.toString())
                } finally {
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun generateRandomList(size: Int, minValue: Int, maxValue: Int): List<Int> {
        require(size > 0) { "Size must be greater than 0" }
        require(minValue <= maxValue) { "minValue must be less than or equal to maxValue" }

        val random = Random.Default
        return List(size) { random.nextInt(minValue, maxValue + 1) }
    }
}