package good.damn.espbluetooth.activities.bluetooth.protocol

import good.damn.espbluetooth.Application
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream

class MessageProtocol {

    fun getMessage(
        inp: InputStream
    ): String {
        val baos = ByteArrayOutputStream(
            100
        )

        var n = 0
        while (n != 0x0A) { // Line feed
            n = inp.read()
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