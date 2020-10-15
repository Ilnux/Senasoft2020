package senasoft2020.buhmed

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Color.RED
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.hmsscankit.WriterException
import com.huawei.hms.ml.scan.HmsBuildBitmapOption
import com.huawei.hms.ml.scan.HmsScan
import senasoft2020.buhmed.ui.votadas.VotadasFragment
import java.lang.Exception

data class Post(var Titulo: String = "", var Categoria:String = "",var Descripcion:String = "")

class PostAdapter(var list: ArrayList<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    class ViewHolder(view: View, context: Context): RecyclerView.ViewHolder(view) {
        fun bindItems(data: Post) {
            val title: TextView = itemView.findViewById(R.id.textViewPostTitle)
            val desc: TextView = itemView.findViewById(R.id.textViewPostDesc)
            val rate: TextView = itemView.findViewById(R.id.textViewPostRate)
            val picture: ImageView = itemView.findViewById(R.id.imageViewPostPicture)
            val card: CardView = itemView.findViewById(R.id.cardView)
            val qr: ImageView = itemView.findViewById(R.id.imageViewPostQR)
            picture.clipToOutline = true

            title.text = data.Titulo
            desc.text = data.Categoria
            rate.text = data.Descripcion.toString()

//            if (data.rate > 0) {
//                rate.setTextColor(ContextCompat.getColor(itemView?.context, R.color.ratePositive))
//            } else if (data.rate < 0) {
//                rate.setTextColor(ContextCompat.getColor(itemView?.context, R.color.rateNegative))
//            } else {
//                rate.setTextColor(ContextCompat.getColor(itemView?.context, R.color.textBlack))
//            }

            card.setOnClickListener {
                val intent = Intent(itemView.context, VerPublicacionActivity::class.java)
                itemView.context.startActivity(intent)
            }

            qr.setOnClickListener{
                    val intent = Intent(itemView.context, VerCodigoActivity::class.java)
                    intent.putExtra("postID", "Oe bien o no")
                    startActivity(itemView.context, intent, Bundle())
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