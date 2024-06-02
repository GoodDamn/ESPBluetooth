package good.damn.espbluetooth.activities

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import good.damn.espbluetooth.Application
import good.damn.espbluetooth.adapters.BluetoothDevicesAdapter
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
            } else {
                Application.toast(
                    "Grant permission please :)",
                    this
                )
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
        val devices = mBluetoothService?.listDevices()

        if (devices == null || devices.isEmpty()) {
            Application.toast(
                "No bluetooth devices",
                activity
            )
            return
        }

        val recyclerView = RecyclerView(
            activity
        )

        setContentView(
            recyclerView
        )

        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )

        recyclerView.adapter = BluetoothDevicesAdapter(
            devices
        )

        recyclerView.setHasFixedSize(
            true
        )
    }
}