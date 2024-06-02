package good.damn.espbluetooth.activities

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import good.damn.espbluetooth.Application
import good.damn.espbluetooth.activities.bluetooth.connection.BluetoothConnection
import good.damn.espbluetooth.listeners.bluetooth.BluetoothConnectionListener
import good.damn.espbluetooth.services.BluetoothService

class DeviceActivity
: AppCompatActivity(),
BluetoothConnectionListener {

    companion object {
        private const val TAG = "DeviceActivity"
        const val KEY_MAC = "mac"
    }

    private var mDevice: BluetoothDevice? = null
    private var mEditTextMsg: EditText? = null

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        val context = this

        val intent = intent

        val mac = intent.getStringExtra(
            KEY_MAC
        )

        if (mac == null) {
            Application.toast(
                "No MAC address",
                context
            )
            return
        }

        Application.toast(
            mac,
            context
        )

        mDevice = BluetoothService(
            context
        ).getDevice(
            mac
        )

        val layout = LinearLayout(
            context
        )

        mEditTextMsg = EditText(
            context
        )

        val btnSend = Button(
            context
        )

        layout.orientation = LinearLayout
            .VERTICAL

        mEditTextMsg?.hint = "Type some msg"
        btnSend.text = "Send"

        btnSend.setOnClickListener(
            this::onClickBtnSend
        )

        layout.addView(
            mEditTextMsg,
            -1,
            -2
        )

        layout.addView(
            btnSend,
            -1,
            -2
        )

        setContentView(
            layout
        )
    }

    @WorkerThread
    override fun onCreateBluetoothConnection() {
        Application.toastMain(
            "Connected to device",
            this
        )
    }

    @WorkerThread
    override fun onErrorBluetoothConnection(
        msg: String
    ) {
        Application.toastMain(
            msg,
            this
        )
    }

    private fun onClickBtnSend(
        v: View?
    ) {
        if (mDevice == null) {
            Application.toast(
                "No specified device",
                this
            )
            return
        }

        val connection = BluetoothConnection(
            mDevice!!,
            this
        )

        val text = mEditTextMsg?.text.toString()

        connection.messageText = "$text\n"

        connection.delegate = this

        connection.start()
    }
}