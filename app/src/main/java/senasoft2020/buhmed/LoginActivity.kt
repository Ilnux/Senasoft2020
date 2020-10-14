package senasoft2020.buhmed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.huawei.hms.common.internal.HuaweiApiManager
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.huawei.hms.support.hwid.result.AuthHuaweiId
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService

class LoginActivity : AppCompatActivity() {

    val CODIGOH = 9999
    val CODIGOG = 7777
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val fontOpenSansRegular = Typeface.createFromAsset(assets, "fonts/Open-Sans-Regular.ttf")
        val fontOpenSansBold = Typeface.createFromAsset(assets, "fonts/Open-Sans-Bold.ttf")
        val fontOpenSansExtraBold = Typeface.createFromAsset(assets, "fonts/Open-Sans-Extra-Bold.ttf")
        textViewAppName.typeface = fontOpenSansBold
        textViewWelcome.typeface = fontOpenSansRegular
        textViewMessage.typeface = fontOpenSansRegular
        buttonHuawei.setOnClickListener {
            logHuawei()
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
                .setEmail().createParams()

        val servicio: HuaweiIdAuthService = HuaweiIdAuthManager.getService(this, solicitud)

        startActivityForResult(servicio.signInIntent, CODIGOH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODIGOG) {
            val tarea = GoogleSignIn.getSignedInAccountFromIntent(data)
            val cuenta = tarea.getResult(ApiException::class.java)

            if (cuenta != null) {
                val credencial = GoogleAuthProvider.getCredential(cuenta.idToken, null)

                FirebaseAuth.getInstance().signInWithCredential(credencial).addOnCompleteListener {
                    if (it.isSuccessful){
                         Toast.makeText(this,"correo: ${cuenta.email}  ${cuenta.displayName}",Toast.LENGTH_LONG).show()
                    }
                    Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()

                }
            }
        }


        //HUAWEI
        if (requestCode == CODIGOH) {
            val tarea = HuaweiIdAuthManager.parseAuthResultFromIntent(data)
            if (tarea.isSuccessful) {
                val cuenta = tarea.result
                cambioAtividad(cuenta)
            }
        }
    }


    fun cambioAtividad(cuenta: AuthHuaweiId) {
        val intent = Intent(this, ActivityComuna::class.java)
        intent.putExtra("cuenta", cuenta)
        startActivity(intent)
        finish()
    }
}



