package senasoft2020.buhmed

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



        buttonContinue.setOnClickListener {
            validarProveedor()
            llenarSpiner()
        }

    }


    fun validarProveedor() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        if (pref.getString("proveedor", "no hay nada") == "google") {
            datosGoogle()
        } else {
            if (pref.getString("proveedor", "no hay nada") == "huawei") {
                datoshuawei()
            } else {
                Toast.makeText(
                    this,
                    "${pref.getString("proveedor", "No hay datos")}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    fun datosGoogle() {
        val infoG: GoogleSignInAccount? =
            intent.extras?.getParcelable<GoogleSignInAccount>("cuenta")

        //db.collection("usuario").document("")


        Toast.makeText(this, "huawei ${infoG?.displayName}", Toast.LENGTH_SHORT).show()
    }


    fun datoshuawei() {
        val infoH = intent.extras?.getParcelable<AuthHuaweiId>("cuenta")

        Toast.makeText(this, "huawei ${infoH?.displayName}", Toast.LENGTH_SHORT).show()
    }

    fun llenarSpiner() {
        val comunas = arrayListOf<String>("1", "2", "3")
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
//                Toast.makeText(
//                    this@ActivityComuna,
//                    "Seleccionaste" + comunas[position],
//                    Toast.LENGTH_LONG
//                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}

