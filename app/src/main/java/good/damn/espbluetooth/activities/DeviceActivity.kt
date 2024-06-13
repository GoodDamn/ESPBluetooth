package good.damn.espbluetooth.activities

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
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
    private lateinit var mTextViewMsg: TextView
    private lateinit var mEditTextMsg: EditText

    private val mProtocol = MessageProtocol()
    private var mConnection: BluetoothConnection? = null

    private lateinit var mLayout: LinearLayout

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        val context = this
        val scrollView = ScrollView(
            context
        )
        mLayout = LinearLayout(
            context
        )
        mTextViewMsg = TextView(
            context
        )
        mEditTextMsg = EditText(
            context
        )
        val btnSend = Button(
            context
        )


        mLayout.orientation = LinearLayout
            .VERTICAL
        mTextViewMsg.movementMethod = ScrollingMovementMethod()

        mTextViewMsg.text = "Connecting to device...\n"
        mEditTextMsg.hint = "00-00-00-00-00-00-00-00-00-00"
        btnSend.text = "Send"



        mLayout.addView(
            mTextViewMsg,
            -1,
            (200 * Application.DENSITY).toInt()
        )
        mLayout.addView(
            mEditTextMsg,
            -1,
            -2
        )
        mLayout.addView(
            btnSend,
            -1,
            -2
        )

        scrollView.addView(
            mLayout,
            -1,
            -2
        )

        setContentView(
            scrollView
        )

        btnSend.setOnClickListener(
            this::onClickBtnSend
        )
    }

    override fun onStart() {
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
        super.onStart()
    }

    override fun onBackPressed() {
        mConnection?.close()
        super.onBackPressed()
    }

    @WorkerThread
    override fun onCreateBluetoothConnection() {
        Application.ui {

            for (i in 1..2) {
                mLayout.getChildAt(i)
                    .visibility = View.VISIBLE
            }

            mTextViewMsg.addText(
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
            mTextViewMsg.addText(
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


    private fun onClickBtnSend(
        view: View
    ) {
        if (mDevice == null) {
            Application.toast(
                "No specified device",
                this
            )
            return
        }

        mConnection?.messageText = "${mEditTextMsg.text}"
        mTextViewMsg.addText(
            "Waiting for response..."
        )
    }

}