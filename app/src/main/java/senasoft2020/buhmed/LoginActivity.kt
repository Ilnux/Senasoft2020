package senasoft2020.buhmed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        textViewAppName.typeface = AppSenasoft2020.fontOpenSansRegular
        textViewWelcome.typeface = AppSenasoft2020.fontOpenSansRegular
        textViewMessage.typeface = AppSenasoft2020.fontOpenSansRegular
    }
}