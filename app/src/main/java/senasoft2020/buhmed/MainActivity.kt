package senasoft2020.buhmed

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Parcelable
import android.preference.PreferenceManager
import android.text.TextUtils
import android.view.Menu
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
//import com.huawei.hms.hmsscankit.ScanUtil
//import com.huawei.hms.ml.scan.HmsScan
//import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.huawei.hms.support.hwid.result.AuthHuaweiId
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val DEFINED_CODE = 222
        private val REQUEST_CODE_SCAN = 0X01
    }

    private val db = FirebaseFirestore.getInstance()
    private val USUARIO = "usuario"
    private val DOCUMENTOG = "perfilG"
    private val DOCUMENTOH = "perfilH"
    val PROVEEDOR = "proveedor"
    val CORREO = "correo"

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            startActivity(Intent(this, CrearActivity::class.java))
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_recientes, R.id.nav_votadas, R.id.nav_perfil, R.id.nav_publicaciones, R.id.nav_scan
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        // tesbtn.setOnClickListener({v -> newViewBtnClick()}) SCAN KIT
    }

    /* SCAN KIT

    private fun newViewBtnClick() {
        val list = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this, list, DEFINED_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) return
        else if (requestCode == DEFINED_CODE) {
            ScanUtil.startScan(this, REQUEST_CODE_SCAN, HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK || data == null) {return}
        else if (requestCode == REQUEST_CODE_SCAN) {
            when (val obj: Parcelable? = data.getParcelableExtra(ScanUtil.RESULT)) {
                is HmsScan -> {
                    if (!TextUtils.isEmpty(obj.getOriginalValue())) {
                        Toast.makeText(this, obj.getOriginalValue(), Toast.LENGTH_SHORT).show()
                    }
                    return
                }
            }
        }
    }

     */


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        if (pref.getString(PROVEEDOR, null) == "google") {
            val infoG: GoogleSignInAccount? =
                intent.extras?.getParcelable<GoogleSignInAccount>("cuenta")
            db.collection(USUARIO).document(pref.getString(CORREO,null)!!).get().addOnSuccessListener {
                txt_view_nombre.text = it.getString("Nombre" as String)
                txt_view_correo.text = it.getString("Correo" as String)
            }
        }else{
            val infoH = intent.extras?.getParcelable<AuthHuaweiId>("cuenta")
            db.collection(USUARIO).document(pref.getString(CORREO,null)!!).get().addOnSuccessListener {
                txt_view_nombre.text = it.getString("Nombre" as String)
                txt_view_correo.text = it.getString("Correo" as String)

            }
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



}