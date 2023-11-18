package mx.it_4.proyectointermedio

import Modelo.RickAndMortyApiService
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

class MainActivity : AppCompatActivity() {

    private lateinit var ApiService: RickAndMortyApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ApiService = RickAndMortyApiService()

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val txtId: EditText = findViewById(R.id.IdPersonaje)
        val btnSearch: Button = findViewById(R.id.SearchButton)

        btnSearch.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            var id = txtId.text.toString()
            getCharacter(id)

        }
    }

    private fun getCharacter(id: String) {
        GlobalScope.launch (Dispatchers.IO){
            try {
                val character = ApiService.RickAndMortyApi(id)
                Log.i("RyM Id", character.Id.toString())
                Log.d("RyM Id", character.Id.toString())
                Log.i("RyM Name", character.Name)
                Log.d("RyM Name", character.Name)
                withContext(Dispatchers.Main){
                    // TODO Actualizar GUI
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