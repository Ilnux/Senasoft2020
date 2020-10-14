package senasoft2020.buhmed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huawei.hms.common.internal.HuaweiApiManager
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.huawei.hms.support.hwid.result.AuthHuaweiId
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService

class LoginActivity : AppCompatActivity() {

    val CODIGO = 9999
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }


    //Funciones Huawei
    fun logHuawei() {
        val solicitud: HuaweiIdAuthParams =
            HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM).setAuthorizationCode()
                .setEmail().createParams()

        val servicio:HuaweiIdAuthService = HuaweiIdAuthManager.getService(this,solicitud)

        startActivityForResult(servicio.signInIntent,CODIGO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODIGO){
            val tarea = HuaweiIdAuthManager.parseAuthResultFromIntent(data)
            if(tarea.isSuccessful){
                val cuenta = tarea.result
                cambioAtividad(cuenta)
            }
        }
    }


    fun cambioAtividad(cuenta:AuthHuaweiId){
        val intent = Intent(this,ActivityComuna::class.java)
        intent.putExtra("cuenta", cuenta)
        startActivity(intent)
        finish()
    }
}



