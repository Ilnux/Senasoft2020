package senasoft2020.buhmed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_crear.*

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
                    intentMain()
//                    val listaublicaciones = ArrayList<Publicaciones>()
//                    db.collection("publicaciones").whereEqualTo("Categoria", "Movilidad").get().addOnSuccessListener {
//                        for (documento in it){
//                            val publicaciones = documento.toObject(Publicaciones::class.java)
//                            listaublicaciones.add(publicaciones)
//
//                        }
//                        Log.d("doc","${listaublicaciones}")
//                        Log.d("doc","${listaublicaciones[0].Titulo}")
//                    }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }


    fun intentMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}