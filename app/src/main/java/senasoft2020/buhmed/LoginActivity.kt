package senasoft2020.buhmed

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.huawei.hms.support.hwid.result.AuthHuaweiId
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    val PROVEEDOR = "proveedor"
    val CODIGOH = 9999
    val CODIGOG = 7777
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val fontOpenSansRegular = Typeface.createFromAsset(assets, "fonts/Open-Sans-Regular.ttf")
        val fontOpenSansBold = Typeface.createFromAsset(assets, "fonts/Open-Sans-Bold.ttf")
        val fontOpenSansExtraBold =
            Typeface.createFromAsset(assets, "fonts/Open-Sans-Extra-Bold.ttf")
        textViewAppName.typeface = fontOpenSansBold
        textViewWelcome.typeface = fontOpenSansRegular
        textViewMessage.typeface = fontOpenSansRegular
        buttonHuawei.setOnClickListener {
            logHuawei()
        }
        buttonGoogle.setOnClickListener {
            logGoogle()
        }

    }


    //Funciones Google

    fun logGoogle() {
        val confGoogle = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val clienteGoogle = GoogleSignIn.getClient(this, confGoogle)
        startActivityForResult(clienteGoogle.signInIntent, CODIGOG)
    }


    //Funciones Huawei
    fun logHuawei() {
        val solicitud: HuaweiIdAuthParams =
            HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM).setAuthorizationCode()
                .setEmail()
                .createParams()

        val servicio: HuaweiIdAuthService = HuaweiIdAuthManager.getService(this, solicitud)

        startActivityForResult(servicio.signInIntent, CODIGOH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //HUAWEI
        if (requestCode == CODIGOH) {

            val tarea = HuaweiIdAuthManager.parseAuthResultFromIntent(data)

            if (tarea.isSuccessful) {
                val cuenta = tarea.result
                val pref = PreferenceManager.getDefaultSharedPreferences(this)
                val guardar = pref.edit()
                guardar.putString(PROVEEDOR, "huawei")
                guardar.apply()

                cambioAtividadH(cuenta)
            }else
                Toast.makeText(this, "No se conecto con huawei", Toast.LENGTH_SHORT).show()



        }

        //GOOGLE
        if (requestCode == CODIGOG) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful) {
                Toast.makeText(this, "bien", Toast.LENGTH_SHORT).show()
                val cuenta: GoogleSignInAccount? = task.getResult(ApiException::class.java)

                val pref = PreferenceManager.getDefaultSharedPreferences(this)
                val guardar = pref.edit()
                guardar.putString(PROVEEDOR, "google")
                guardar.apply()

                cambioAtividadG(cuenta)
            } else
                Toast.makeText(this, "No se conecto con google", Toast.LENGTH_SHORT).show()

        }
    }


    fun cambioAtividadG(cuenta: GoogleSignInAccount?) {
        val intent = Intent(this, ActivityComuna::class.java)
        intent.putExtra("cuenta", cuenta)
        startActivity(intent)
        finish()
    }

    fun cambioAtividadH(cuenta: AuthHuaweiId) {
        val intent = Intent(this@LoginActivity, ActivityComuna::class.java)
        intent.putExtra("cuenta", cuenta)
        startActivity(intent)
        finish()
    }
}



