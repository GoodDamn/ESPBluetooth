package good.damn.espbluetooth.activities

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import good.damn.espbluetooth.Application
import good.damn.espbluetooth.activities.bluetooth.protocol.MessageProtocol
import good.damn.espbluetooth.activities.bluetooth.server.BluetoothServer
import good.damn.espbluetooth.adapters.BluetoothDevicesAdapter
import good.damn.espbluetooth.extensions.addText
import good.damn.espbluetooth.listeners.bluetooth.BluetoothServerListener
import good.damn.espbluetooth.listeners.OnDeviceClickListener
import good.damn.espbluetooth.services.BluetoothService
import good.damn.espbluetooth.services.PermissionService
import org.w3c.dom.Text

class ListDevicesActivity
: AppCompatActivity(),
OnDeviceClickListener {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val macTarget = "C0:49:EF:01:2E:7A"
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

    private fun startBluetoothManipulation() {
        if (mBluetoothService == null) {
            return
        }

        val activity = this

        if (!mBluetoothService!!.isAvailable) {
            Application.toast(
                "Please enable Bluetooth",
                activity
            )
            val layout = LinearLayout(
                activity
            )
            val textView = TextView(
                activity
            )
            val btnReload = Button(
                activity
            )
            layout.orientation = LinearLayout
                .VERTICAL
            textView.gravity = Gravity
                .CENTER_HORIZONTAL
            textView.text = "Please enable bluetooth"
            btnReload.text = "Reload bluetooth list"
            btnReload.setOnClickListener {
                startBluetoothManipulation()
            }
            layout.addView(
                textView,
                -1,
                -2
            )
            layout.addView(
                btnReload,
                -1,-2
            )
            setContentView(
                layout
            )
            return
        }

        val devices = mBluetoothService!!.listDevices()

        if (devices.isNullOrEmpty()) {
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

        var j = 0
        for (i in devices.indices) {
            if (devices[i].address.equals(macTarget)) {
                j = i
                break
            }
        }

        recyclerView.adapter = BluetoothDevicesAdapter(
            arrayOf(
                devices[j]
            ),
            this
        )

        recyclerView.setHasFixedSize(
            true
        )
    }
}