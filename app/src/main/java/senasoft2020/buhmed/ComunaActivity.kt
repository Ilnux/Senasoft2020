package senasoft2020.buhmed

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.firestore.FirebaseFirestore
import com.huawei.hms.support.hwid.result.AuthHuaweiId
import kotlinx.android.synthetic.main.activity_comuna.*
import kotlinx.android.synthetic.main.activity_comuna.textViewAppName

class ComunaActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private val USUARIO = "usuario"
    private val DOCUMENTOG = "perfilG"
    private val DOCUMENTOH = "perfilH"
    val CORREO = "correo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comuna)
        val fontOpenSansRegular = Typeface.createFromAsset(assets, "fonts/Open-Sans-Regular.ttf")
        val fontOpenSansBold = Typeface.createFromAsset(assets, "fonts/Open-Sans-Bold.ttf")
        val fontOpenSansExtraBold =
            Typeface.createFromAsset(assets, "fonts/Open-Sans-Extra-Bold.ttf")
        textViewAppName.typeface = fontOpenSansBold
        textViewPregunta.typeface = fontOpenSansRegular
        buttonContinue.typeface = fontOpenSansExtraBold

        llenarSpiner()

    }


    fun llenarSpiner() {
        val comunas = arrayListOf<String>(
            "1 - Popular",
            "2 - Santa Cruz",
            "3 - Manrique",
            "4 - Aranjuez",
            "5 - Castilla",
            "6 - Doce de Octubre",
            "7 - Robledo",
            "8 - Villa Hermosa",
            "9 - Buenos Aires",
            "10 - La Candelaria",
            "11 - Laureles, Estadio",
            "12 - La Ámerica",
            "13 - San Javier",
            "14 - Poblado",
            "15 - Guayabal",
            "16 - Belén"
        )
        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, comunas)

        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                buttonContinue.setOnClickListener {
                    intentBotonComuna()
                    val pref = PreferenceManager.getDefaultSharedPreferences(this@ComunaActivity)

                    if (pref.getString("proveedor", "no hay nada") == "google") {
                        val infoG: GoogleSignInAccount? =
                            intent.extras?.getParcelable<GoogleSignInAccount>("cuenta")
                        val guardar = pref.edit()
                        guardar.putString(CORREO, infoG?.email)
                        guardar.putString("nombre", infoG?.displayName)
                        guardar.apply()

                        db.collection(USUARIO).document(infoG!!.email!!).set(
                            hashMapOf(
                                "Nombre" to infoG.displayName,
                                "Correo" to infoG.email,
                                "Comuna" to comunas[position]
                            )
                        )
                    } else {
                        if (pref.getString("proveedor", "no hay nada") == "huawei") {
                            val infoH = intent.extras?.getParcelable<AuthHuaweiId>("cuenta")
                            val guardar = pref.edit()
                            guardar.putString(CORREO, infoH?.email)
                            guardar.putString("nombre", infoH?.displayName+" "+infoH?.familyName)
                            guardar.apply()

                            db.collection(USUARIO).document(infoH!!.email).set(
                                hashMapOf(
                                    "Nombre" to infoH?.displayName + " " + infoH?.familyName,
                                    "Correo" to infoH?.email,
                                    "Comuna" to comunas[position]
                                )
                            )
                        } else {
                            Toast.makeText(
                                this@ComunaActivity,
                                "${pref.getString("proveedor", "No hay datos")}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun intentBotonComuna() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

