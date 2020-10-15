package senasoft2020.buhmed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_ver_publicacion.*

class VerPublicacionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_publicacion)
        textViewAuthorName.text = intent.getStringExtra("nombreAutor")
        textViewPostTitle.text = intent.getStringExtra("titulo")
        textViewPostDesc.text = intent.getStringExtra("descripcion")
        textViewPostCategory.text = intent.getStringExtra("categoria")

buttonShare.setOnClickListener {
    val db = FirebaseFirestore.getInstance()
    db.collection("publicaciones").get()
}

    }
}