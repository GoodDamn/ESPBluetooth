package good.damn.espbluetooth.listeners.bluetooth

import android.bluetooth.BluetoothSocket
import androidx.annotation.WorkerThread

interface BluetoothServerListener {

    @WorkerThread
    fun onCreateBluetoothServer()

    @WorkerThread
    fun onAcceptBluetoothClient(
        socket: BluetoothSocket
    )
}