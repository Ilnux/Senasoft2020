package senasoft2020.buhmed

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.huawei.hms.support.hwid.result.AuthHuaweiId
import kotlinx.android.synthetic.main.activity_comuna.*
import kotlinx.android.synthetic.main.activity_comuna.textViewAppName

class ActivityComuna : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private val USUARIO = "usuario"
    private val DOCUMENTOG = "perfilG"
    private val DOCUMENTOH = "perfilH"

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

    //guadar datos de los users de huawei
    fun datoshuawei() {
        val infoH = intent.extras?.getParcelable<AuthHuaweiId>("cuenta")

        db.collection(USUARIO).document(DOCUMENTOH).set(
            hashMapOf(
                "Nombre" to  infoH?.displayName+" "+infoH?.familyName,
                "Correo" to infoH?.email
            )
        )

        //Toast.makeText(this, "huawei ${infoH?.displayName}", Toast.LENGTH_SHORT).show()
    }

    fun llenarSpiner() {
        val comunas = arrayListOf<String>(
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16"
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
                    val pref = PreferenceManager.getDefaultSharedPreferences(this@ActivityComuna)

                    if (pref.getString("proveedor", "no hay nada") == "google") {
                        val infoG: GoogleSignInAccount? =
                            intent.extras?.getParcelable<GoogleSignInAccount>("cuenta")
                        db.collection(USUARIO).document().set(
                            hashMapOf(
                                "Nombre" to infoG?.displayName,
                                "Correo" to infoG?.email,
                                "Comuna" to comunas[position]
                            )
                        )
                    } else {
                        if (pref.getString("proveedor", "no hay nada") == "huawei") {
                            val infoH = intent.extras?.getParcelable<AuthHuaweiId>("cuenta")

                            db.collection(USUARIO).document(infoH!!.email).set(
                                hashMapOf(
                                    "Nombre" to infoH?.displayName + " " + infoH?.familyName,
                                    "Correo" to infoH?.email,
                                    "Comuna" to comunas[position]
                                )
                            )
                        } else {
                            Toast.makeText(
                                this@ActivityComuna,
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

