package good.damn.espbluetooth.activities

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import good.damn.espbluetooth.Application
import good.damn.espbluetooth.connection.BluetoothConnection
import good.damn.espbluetooth.services.BluetoothService

class DeviceActivity
: AppCompatActivity() {

    companion object {
        private const val TAG = "DeviceActivity"
        const val KEY_MAC = "mac"
    }

    private var mDevice: BluetoothDevice? = null

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

        val editTextMsg = EditText(
            context
        )

        val btnSend = Button(
            context
        )

        layout.orientation = LinearLayout
            .VERTICAL

        editTextMsg.hint = "Type some msg"
        btnSend.text = "Send"

        btnSend.setOnClickListener(
            this::onClickBtnSend
        )

        layout.addView(
            editTextMsg,
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

        BluetoothConnection(
            mDevice!!,
            this
        ).start()
    }
}