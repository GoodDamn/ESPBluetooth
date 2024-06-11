package good.damn.espbluetooth.activities.bluetooth.protocol

import android.util.Log
import good.damn.espbluetooth.Application
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream

class MessageProtocol {

    companion object {
        private const val TAG = "MessageProtocol"
    }

    fun getMessage(
        inp: InputStream
    ): String {
        val baos = ByteArrayOutputStream(
            100
        )

        var attempts = 0
        var n = 0
        while (n != 0x0A) { // Line feed

            if (inp.available() == -1) {
                break
            }

            if (inp.available() == 0) {
                if (attempts >= 100000) {
                    break
                }
                attempts++
                continue
            }
            attempts = 0
            n = inp.read()
            if (n == 0) {
                continue
            }

            if (n == -1) {
                break
            }

            baos.write(n)
        }

        val msgBytes = baos.toByteArray()
        baos.close()

        return String(
            msgBytes,
            Application.CHARSET_ASCII
        )
    }

    fun sendMessage(
        msg: String,
        out: OutputStream
    ) {
        out.write(
            msg.toByteArray(
                Application.CHARSET_ASCII
            )
        )
    }

}