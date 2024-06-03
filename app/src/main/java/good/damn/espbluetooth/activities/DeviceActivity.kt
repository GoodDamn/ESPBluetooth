package good.damn.espbluetooth.activities

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import good.damn.espbluetooth.Application
import good.damn.espbluetooth.activities.bluetooth.connection.BluetoothConnection
import good.damn.espbluetooth.activities.bluetooth.protocol.MessageProtocol
import good.damn.espbluetooth.extensions.addText
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

    private val mProtocol = MessageProtocol()
    private var mConnection: BluetoothConnection? = null

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

        mConnection = BluetoothConnection(
            mDevice!!,
            this
        )

        mConnection!!.delegate = this
        mConnection!!.start()

        val scrollView = ScrollView(
            context
        )

        val layout = LinearLayout(
            context
        )

        mTextViewMsg = TextView(
            context
        )

        layout.orientation = LinearLayout
            .VERTICAL

        mTextViewMsg?.movementMethod = ScrollingMovementMethod()
        mTextViewMsg?.text = "Wait for response..."

        layout.addView(
            mTextViewMsg,
            -1,
            (200 * Application.DENSITY).toInt()
        )

        createButtonMessage(
            "Hello, ESP32!",
            layout
        )

        createButtonMessage(
            "This is some message!",
            layout
        )

        createButtonMessage(
            "Lorem ipsum asdkjklufdjn",
            layout
        )

        createButtonMessage(
            "Some message",
            layout
        )

        createButtonMessage(
            "Diploma",
            layout
        )

        createButtonMessage(
            "June",
            layout
        )

        createButtonMessage(
            "Telegram",
            layout
        )

        createButtonMessage(
            "Github",
            layout
        )

        scrollView.addView(
            layout,
            -1,
            -2
        )

        setContentView(
            scrollView
        )
    }

    override fun onBackPressed() {
        mConnection?.close()
        super.onBackPressed()
    }

    @WorkerThread
    override fun onCreateBluetoothConnection() {
        Application.ui {
            mTextViewMsg?.addText(
                "Connected"
            )
        }
    }

    @WorkerThread
    override fun onInputBluetoothData(
        inp: InputStream
    ) {
        val msg = mProtocol.getMessage(
            inp
        )
        Application.ui {
            mTextViewMsg?.addText(
                msg
            )
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

    private fun createButtonMessage(
        text: String,
        layout: LinearLayout
    ) {
        val btnMsg = Button(
            this
        )
        btnMsg.text = text

        btnMsg.setOnClickListener(
            this::onClickBtnMsg
        )

        layout.addView(
            btnMsg,
            -1,
            -2
        )
    }

    private fun onClickBtnMsg(
        v: View
    ) {
        if (mDevice == null) {
            Application.toast(
                "No specified device",
                this
            )
            return
        }

        mTextViewMsg?.addText(
            "Waiting for response..."
        )

        val text = (v as? Button)?.text.toString()
        mConnection?.messageText = "$text\n"
    }
}