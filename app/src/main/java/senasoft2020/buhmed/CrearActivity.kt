package senasoft2020.buhmed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.huawei.hms.support.hwid.result.AuthHuaweiId
import kotlinx.android.synthetic.main.activity_comuna.*
import kotlinx.android.synthetic.main.activity_crear.*
import senasoft2020.buhmed.data.Publicaciones

class CrearActivity : AppCompatActivity() {
    var titulo: String = ""
    var categoria: String = ""
    var descripcion: String = ""
    var cont: Int = 0

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear)
        llenarSpiner()
    }


    fun llenarSpiner() {
        val categorias = arrayListOf<String>(
            "Seguridad",
            "Ruido",
            "Movilidad",
            "Basuras"
        )
        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categorias)

        spinnerCategoria.adapter = arrayAdapter
        spinnerCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                buttonPublicar.setOnClickListener {
                    titulo = editTextNombre.text.toString()
                    descripcion = editTextDescripcion.text.toString()
                    categoria = categorias[position]

                    db.collection("publicaciones").document().set(
                        hashMapOf(
                            "Titulo" to titulo,
                            "Categoria" to categoria,
                            "Descripcion" to descripcion
                        )
                    )
//                    val listaublicaciones = mutableListOf<Publicaciones>()
//                    db.collection("publicaciones").get().addOnSuccessListener { resultado->
//                        for (documento in resultado){
//                            val publicaciones = documento.toObject(Publicaciones::class.java)
//                            listaublicaciones.add(publicaciones)
//                        }
//
//                        Log.d("Publicaciones","${listaublicaciones}")
//                    }
//                    val listaublicaciones = mutableListOf<Publicaciones>()
//                    db.collection("publicaciones").whereEqualTo("Categoria", "Movilidad").get().addOnSuccessListener {
//                        for (documentos in it){
//                            Log.d("doc","${documentos.data}")
//                        }
//                    }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}