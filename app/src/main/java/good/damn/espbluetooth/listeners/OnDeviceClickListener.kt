package good.damn.espbluetooth.listeners

import android.bluetooth.BluetoothDevice

interface OnDeviceClickListener {

    fun onDevice(
        mac: String
    )
}