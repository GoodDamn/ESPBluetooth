package good.damn.espbluetooth.services

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import kotlin.math.log

class BluetoothService(
    context: Context
) {
    companion object {
        private const val TAG = "BluetoothService"
    }

    private val manager: BluetoothManager = context.getSystemService(
        Context.BLUETOOTH_SERVICE
    ) as BluetoothManager

    private val mDeviceAdapter = manager.adapter

    fun listDevices(
        activity: Activity
    ): Array<BluetoothDevice>? {
        return if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        ) mDeviceAdapter?.bondedDevices?.toTypedArray()
        else null
    }

}