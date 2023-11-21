package modelos

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mx.it_4.proyectointermedio.CharacterInfo
import mx.it_4.proyectointermedio.R

class CharacterListAdapter(private val characterList: List<Character>) :
    RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characterList[position]

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(character.image)
            .into(holder.imageViewCharacter)

        // Bind other data to views
        holder.textViewName.text = character.name
        holder.textViewStatus.text = character.status

        // Set click listener on the item
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CharacterInfo::class.java)
            intent.putExtra("data", Serialization.serializeCharacter(character))

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewCharacter: ImageView = itemView.findViewById(R.id.imageViewCharacter)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewStatus: TextView = itemView.findViewById(R.id.textViewStatus)
    }
}