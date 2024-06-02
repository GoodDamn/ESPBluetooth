package good.damn.espbluetooth.services

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import good.damn.espbluetooth.Application
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

    @SuppressLint("MissingPermission")
    fun listDevices(): Array<BluetoothDevice>? {
        return mDeviceAdapter?.bondedDevices?.toTypedArray()
    }

    fun getDevice(
        mac: String
    ): BluetoothDevice? {

        if (!mDeviceAdapter.isEnabled || mac.isEmpty()) {
            return null
        }

        return mDeviceAdapter.getRemoteDevice(
            mac
        )
    }

}