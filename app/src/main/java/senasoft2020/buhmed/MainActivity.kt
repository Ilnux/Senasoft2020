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
            startActivity(Intent(this, CrearActivity::class.java))
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_recientes, R.id.nav_votadas, R.id.nav_perfil, R.id.nav_publicaciones, R.id.nav_scan
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

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