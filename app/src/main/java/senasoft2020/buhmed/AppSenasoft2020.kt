package senasoft2020.buhmed

import android.app.Application
import android.graphics.Typeface

class AppSenasoft2020: Application() {
    companion object {
        lateinit var fontOpenSansRegular: Typeface
        lateinit var fontOpenSansBold: Typeface
        lateinit var fontOpenSansExtraBold: Typeface
    }

    override fun onCreate() {
        super.onCreate()
        fontOpenSansRegular = Typeface.createFromAsset(assets, "fonts/Open-Sans-Regular.ttf")
        fontOpenSansBold = Typeface.createFromAsset(assets, "fonts/Open-Sans-Bold.ttf")
        fontOpenSansExtraBold = Typeface.createFromAsset(assets, "fonts/Open-Sans-Extra-Bold.ttf")
    }
}