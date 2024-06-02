package good.damn.espbluetooth.activities

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import good.damn.espbluetooth.Application
import good.damn.espbluetooth.activities.bluetooth.connection.BluetoothConnection
import good.damn.espbluetooth.activities.bluetooth.protocol.MessageProtocol
import good.damn.espbluetooth.listeners.bluetooth.BluetoothConnectionListener
import good.damn.espbluetooth.services.BluetoothService
import java.io.InputStream

class DeviceActivity
: AppCompatActivity(),
BluetoothConnectionListener {

    companion object {
        private const val TAG = "DeviceActivity"
        const val KEY_MAC = "mac"
    }

    private var mDevice: BluetoothDevice? = null
    private var mTextViewMsg: TextView? = null
    private var mBtnHello: Button? = null

    private val mProtocol = MessageProtocol()

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

        mTextViewMsg = TextView(
            context
        )

        mBtnHello = Button(
            context
        )

        layout.orientation = LinearLayout
            .VERTICAL

        mTextViewMsg?.text = "Wait for response..."
        mBtnHello?.text = "Hello, ESP32"

        mBtnHello?.setOnClickListener(
            this::onClickBtnHello
        )

        layout.addView(
            mTextViewMsg,
            -1,
            -2
        )

        layout.addView(
            mBtnHello,
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
    override fun onInputBluetoothData(
        inp: InputStream
    ) {
        val msg = mProtocol.getMessage(
            inp
        )
        Application.ui {
            mTextViewMsg?.text = msg
        }
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

    private fun onClickBtnHello(
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

        val text = mBtnHello?.text.toString()

        connection.messageText = "$text\n"

        connection.delegate = this

        connection.start()
    }
}