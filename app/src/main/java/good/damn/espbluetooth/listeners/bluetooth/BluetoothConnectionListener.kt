package good.damn.espbluetooth.listeners.bluetooth

import androidx.annotation.WorkerThread
import java.io.InputStream

interface BluetoothConnectionListener {

    @WorkerThread
    fun onCreateBluetoothConnection()

    @WorkerThread
    fun onInputBluetoothData(
        inp: InputStream
    )

    @WorkerThread
    fun onErrorBluetoothConnection(
        msg: String
    )

}