package good.damn.espbluetooth.activities.bluetooth.server

import android.content.Context
import android.util.Log
import good.damn.espbluetooth.services.BluetoothService

class BluetoothServer(
    private val mBluetoothService: BluetoothService
): Runnable {

    companion object {
        private const val TAG = "BluetoothServer"
    }

    private var mThread: Thread? = null

    override fun run() {
        val serverSocket = mBluetoothService
            .createServerSocket()
        Log.d(TAG, "run: SERVER CREATED. ACCEPTING CLIENTS")
        val clientSocket = serverSocket.accept()

        val device = clientSocket.remoteDevice

        Log.d(TAG, "run: CLIENT ACCEPTED: ${device.address}")

        clientSocket.close()
        serverSocket.close()
    }


    fun start() {
        mThread = Thread(this)
        mThread?.start()
    }

    fun stop() {
        mThread?.interrupt()
    }
}