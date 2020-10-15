package senasoft2020.buhmed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_ver_publicacion.*

class VerPublicacionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_publicacion)
        textViewAuthorName.text = intent.getStringExtra("postId")
        val db = FirebaseFirestore.getInstance()
        val postId = intent.getStringExtra("postId")


        db.collection("publicaciones").document(postId!!).get().addOnSuccessListener {
            textViewAuthorName.text = it.getString("Autor" as String)
            textViewPostTitle.text = it.getString("Titulo" as String)
            textViewPostCategory.text = it.getString("Categoria" as String)
            textViewPostDesc.text = it.getString("Descripcion" as String)

        }
        buttonRateNegative.setOnClickListener {
            db.collection("publicaciones").document(postId).get().addOnSuccessListener {
                db.collection("publicaciones").document(postId).set(
                    hashMapOf(
                        "Autor" to it.getString("Autor" as String),
                        "Titulo" to it.getString("Titulo" as String),
                        "Categoria" to it.getString("Categoria" as String),
                        "Descripcion" to it.getString("Descripcion" as String),
                        "Rate" to (it.get("Rate").toString().toInt())-1
                    )
                )
            }
        }
        buttonRatePositive.setOnClickListener {
            db.collection("publicaciones").document(postId).get().addOnSuccessListener {
                db.collection("publicaciones").document(postId).set(
                    hashMapOf(
                        "Autor" to it.getString("Autor" as String),
                        "Titulo" to it.getString("Titulo" as String),
                        "Categoria" to it.getString("Categoria" as String),
                        "Descripcion" to it.getString("Descripcion" as String),
                        "Rate" to (it.get("Rate").toString().toInt())+1
                    )
                )
            }
        }
        buttonShare.setOnClickListener {
            db.collection("publicaciones").document(postId!!).get().addOnSuccessListener {
                val intent = Intent(this, VerCodigoActivity::class.java)
                intent.putExtra("textoAConvertir", postId)
                startActivity(intent)
            }
        }
    }
}
