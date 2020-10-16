package senasoft2020.buhmed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.huawei.hms.support.hwid.result.AuthHuaweiId
import kotlinx.android.synthetic.main.activity_crear.*

data class ListaRate(var rate: String = "")

class CrearActivity : AppCompatActivity() {
    var titulo: String = ""
    var categoria: String = ""
    var descripcion: String = ""
    var autor: String = ""
    var cont: Int = 0
    val CORREO = "correo"
    private val db = FirebaseFirestore.getInstance()
    private val USUARIO = "usuario"

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

                    if (editTextNombre.text.isEmpty()) {
                        alerta("Titulo")
                        editTextNombre.requestFocus()
                    } else {
                        if (editTextDescripcion.text.isEmpty()) {
                            alerta("Descripcion")
                            editTextDescripcion.requestFocus()
                        }
                    }

                    if (editTextNombre.text.isNotEmpty() && editTextDescripcion.text.isNotEmpty()) {

                        titulo = editTextNombre.text.toString()
                        descripcion = editTextDescripcion.text.toString()
                        categoria = categorias[position]
                        val pref = PreferenceManager.getDefaultSharedPreferences(this@CrearActivity)


                        db.collection("publicaciones").document().set(
                            hashMapOf(
                                "Titulo" to titulo,
                                "Categoria" to categoria,
                                "Descripcion" to descripcion,
                                "Autor" to pref.getString("nombre", null),
                                "Rate" to 0
                            )
                        )
                        intentMain()

                    }
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

    private fun alerta(mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Un momento")
        builder.setMessage("Ingresar el campo $mensaje por favor")
        builder.setPositiveButton("Ok", null)
        val dialogo: AlertDialog = builder.create()
        dialogo.show()
    }

}