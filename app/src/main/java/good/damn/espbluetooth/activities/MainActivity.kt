package good.damn.espbluetooth.activities

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import good.damn.espbluetooth.Application
import good.damn.espbluetooth.activities.bluetooth.server.BluetoothServer
import good.damn.espbluetooth.adapters.BluetoothDevicesAdapter
import good.damn.espbluetooth.listeners.OnDeviceClickListener
import good.damn.espbluetooth.services.BluetoothService
import good.damn.espbluetooth.services.PermissionService

class MainActivity
: AppCompatActivity(),
OnDeviceClickListener {

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
            arrayOf(
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH
            )
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

    override fun onDevice(
        mac: String
    ) {
        val intent = Intent(
            this,
            DeviceActivity::class.java
        )

        intent.putExtra(
            DeviceActivity.KEY_MAC,
            mac
        )

        startActivity(
            intent
        )
    }

    private fun onClickBtnCreateServer(
        v: View?
    ) {
        if (mBluetoothService == null) {
            Application.toast(
                "Invalid Bluetooth service",
                this
            )
            return
        }

        BluetoothServer(
            mBluetoothService!!
        ).start()
    }

    private fun startBluetoothManipulation() {
        val activity = this
        val devices = mBluetoothService?.listDevices()
        if (devices == null) {
            Application.toast(
                "No bluetooth devices",
                activity
            )
            return
        }

        val layout = LinearLayout(
            activity
        )

        val btnCreateServer = Button(
            activity
        )

        val recyclerView = RecyclerView(
            activity
        )

        layout.orientation = LinearLayout
            .VERTICAL

        btnCreateServer.text = "Create server"

        btnCreateServer.setOnClickListener(
            this::onClickBtnCreateServer
        )

        layout.addView(
            btnCreateServer,
            -1,
            -2
        )

        layout.addView(
            recyclerView,
            -1,
            -1
        )

        setContentView(
            layout
        )

        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )

        recyclerView.adapter = BluetoothDevicesAdapter(
            devices,
            this
        )

        recyclerView.setHasFixedSize(
            true
        )
    }
}