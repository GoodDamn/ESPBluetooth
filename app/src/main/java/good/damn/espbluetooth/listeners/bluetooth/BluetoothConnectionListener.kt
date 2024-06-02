package good.damn.espbluetooth.listeners.bluetooth

import androidx.annotation.WorkerThread

interface BluetoothConnectionListener {

    @WorkerThread
    fun onCreateBluetoothConnection()

    @WorkerThread
    fun onErrorBluetoothConnection(
        msg: String
    )

}