package mx.it_4.proyectointermedio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import modelos.RickAndMortyCharacter
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream

class CharacterInfo:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_info)

        val serialized = intent.getByteArrayExtra("datos")
        val datos = serialized?.let { deserializeArray(it) }
        if(datos != null) {
            val name = findViewById<TextView>(R.id.textViewName)
            val status = findViewById<TextView>(R.id.textViewStatus)
            val species = findViewById<TextView>(R.id.textViewSpecies)
            val origin = findViewById<TextView>(R.id.textViewOrigin)
            val location = findViewById<TextView>(R.id.textViewLocation)

            status.text = datos[0].status
            name.text = datos[0].name
            species.text = datos[0].species
            origin.text = datos[0].origin.name
            location.text = datos[0].location.name

            val img = findViewById<ImageView>(R.id.imageViewCharacter)
            Glide.with(this)
                .load(datos[0].image)
                .into(img)

            val btn = findViewById<Button>(R.id.buttonLocation)
            btn.setOnClickListener(){
                val url = datos[0].location.url

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)

                startActivity(intent)
            }
        }
    }
    fun deserializeArray(datos: ByteArray): Array<RickAndMortyCharacter> {
        val byteArrayInputStream = ByteArrayInputStream(datos)
        val objectInputStream = ObjectInputStream(byteArrayInputStream)
        val info = objectInputStream.readObject() as Array<RickAndMortyCharacter>
        objectInputStream.close()

        return info
    }
}