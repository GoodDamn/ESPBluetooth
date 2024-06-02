package good.damn.espbluetooth.activities.bluetooth.connection

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import good.damn.espbluetooth.Application
import kotlin.math.log

@SuppressLint("MissingPermission")
class BluetoothConnection(
    private val mDevice: BluetoothDevice,
    private val mContext: Context
): Thread() {

    companion object {
        private const val TAG = "BluetoothConnection"
    }

    private var mSocket: BluetoothSocket? = null

    init {
        try {
            mSocket = mDevice.createRfcommSocketToServiceRecord(
                java.util.UUID.fromString(
                    Application.UUID
                )
            )
        } catch (e: Exception) {
            Application.toastMain(
                "Couldn't create socket: ${e.message}",
                mContext
            )
            Log.d(TAG, "init: EXCEPTION: ${e.message}")
        }
    }

    override fun run() {
        try {
            Log.d(TAG, "run: PREPARE_TO_CONNECT")
            mSocket?.connect()
            Log.d(TAG, "run: CONNECTED")
            Application.toastMain(
                "Connected",
                mContext
            )
        } catch (e: Exception) {
            Application.toastMain(
                "Couldn't connect: ${e.message}",
                mContext
            )
            Log.d(TAG, "run: EXCEPTION: ${e.message}")
        }
        super.run()
    }

    fun close() {
        try {
            mSocket?.close()
        } catch (e: Exception) {
            Application.toastMain(
                "Couldn't close socket: ${e.message}",
                mContext
            )
            Log.d(TAG, "close: EXCEPTION: ${e.message}")
        }
    }
}