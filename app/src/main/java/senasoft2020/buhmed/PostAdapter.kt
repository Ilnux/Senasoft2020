package senasoft2020.buhmed

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import senasoft2020.buhmed.ui.votadas.VotadasFragment

data class Post(val title: String, val desc: String, val rate: Int /*val idAuthor: String*/)

class PostAdapter(var list: ArrayList<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    class ViewHolder(view: View, context: Context): RecyclerView.ViewHolder(view) {
        fun bindItems(data: Post) {
            val title: TextView = itemView.findViewById(R.id.textViewAuthorName)
            val desc: TextView = itemView.findViewById(R.id.textViewPostDesc)
            val rate: TextView = itemView.findViewById(R.id.textViewPostRate)
            val picture: ImageView = itemView.findViewById(R.id.imageViewPostPicture)
            val card: CardView = itemView.findViewById(R.id.cardView)
            picture.clipToOutline = true

            title.text = data.title
            desc.text = data.desc
            rate.text = data.rate.toString()

            if (data.rate > 0) {
                rate.setTextColor(ContextCompat.getColor(itemView?.context, R.color.ratePositive))
            } else if (data.rate < 0) {
                rate.setTextColor(ContextCompat.getColor(itemView?.context, R.color.rateNegative))
            } else {
                rate.setTextColor(ContextCompat.getColor(itemView?.context, R.color.textBlack))
            }

            card.setOnClickListener {
                val intent = Intent(itemView.context, VerPublicacionActivity::class.java)
                itemView.context.startActivity(intent)
            }

            /*
            val pic = obtener foto de la cuenta

            Glide.with(itemView.context).load(picture).into(picture) cargar foto de la cuenta
            */
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(parent?.context).inflate(R.layout.post_item, parent, false)
        return ViewHolder(v, v.context)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }
}