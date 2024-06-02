package good.damn.espbluetooth.activities

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import good.damn.espbluetooth.services.BluetoothService
import good.damn.espbluetooth.services.PermissionService

class MainActivity
: AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }
    
    private val mPermissionService = PermissionService()
    private var mBluetoothService: BluetoothService? = null
    
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        val activity = this

        mBluetoothService = BluetoothService(
            activity
        )
        
        if (PermissionService.isAcceptedBluetooth(
            activity
        )) {
            startBluetoothManipulation()
            return
        }
        
        mPermissionService.request(
            activity,
            Manifest.permission.BLUETOOTH_CONNECT
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == mPermissionService.requestCode) {
            Log.d(TAG, "onRequestPermissionsResult: GRANT_RESULT: ${grantResults.contentToString()}")

            // 0 - granted
            // -1 - not granted
            if (grantResults[0] == 0) {
                startBluetoothManipulation()
            }
        }
        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
    }

    private fun startBluetoothManipulation() {
        val activity = this
        val devices = mBluetoothService?.listDevices(
            activity
        )

        Log.d(TAG, "onCreate: BLUE_DEVICES: $devices")
    }
}