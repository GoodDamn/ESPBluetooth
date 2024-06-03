package good.damn.espbluetooth.activities.bluetooth.server

import android.util.Log
import good.damn.espbluetooth.listeners.bluetooth.BluetoothServerListener
import good.damn.espbluetooth.services.BluetoothService

class BluetoothServer(
    private val mBluetoothService: BluetoothService
): Runnable {

    companion object {
        private const val TAG = "BluetoothServer"
    }

    var isRunning = false
    var delegate: BluetoothServerListener? = null

    private var mThread: Thread? = null

    override fun run() {
        val serverSocket = mBluetoothService
            .createServerSocket()
        delegate?.onCreateBluetoothServer()
        Log.d(TAG, "run: SERVER CREATED")
        isRunning = true

        while (isRunning) {
            Log.d(TAG, "run: Listening clients:")
            val clientSocket = serverSocket.accept()
            try {
                delegate?.onAcceptBluetoothClient(
                    clientSocket
                )
            } catch (e: Exception) { }

            delegate?.onDropBluetoothClient()
        }

        serverSocket.close()
    }


    fun start() {
        mThread = Thread(this)
        mThread?.start()
    }

    fun stop() {
        isRunning = false
    }
}