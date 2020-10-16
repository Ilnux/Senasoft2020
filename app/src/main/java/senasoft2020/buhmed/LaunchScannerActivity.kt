package senasoft2020.buhmed

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions

class LaunchScannerActivity : AppCompatActivity() {
    companion object {
        private val DEFINED_CODE = 222
        private val REQUEST_CODE_SCAN = 0X01
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_scanner)
        launchQRScanner()
    }

    private fun launchQRScanner() {
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
            ScanUtil.startScan(this, REQUEST_CODE_SCAN, HmsScanAnalyzerOptions.Creator().setHmsScanTypes(
                HmsScan.ALL_SCAN_TYPE).create())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK || data == null) {return}
        else if (requestCode == REQUEST_CODE_SCAN) {
            when (val obj: Parcelable? = data.getParcelableExtra(ScanUtil.RESULT)) {
                is HmsScan -> {
                    if (!TextUtils.isEmpty(obj.getOriginalValue())) {
                        val intent = Intent(this, VerPublicacionActivity::class.java)
                        val result = obj.getOriginalValue()
                        intent.putExtra("postId", result)
                        startActivity(intent)
                        finish()
                    }
                    return
                }
            }
        }
    }
}