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

        var i = 0
        var result = ""

        var attempts = 0
        var dataByte: Int
        var hex: String
        while (true) { // Line feed

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
            dataByte = inp.read()

            hex = Integer.toHexString(
                dataByte
            )

            if (i != 0) {
                result += "-"
            }

            result += hex
            i++
        }

        return result
    }

    fun sendMessage(
        hexMsg: String,
        out: OutputStream
    ) {
        val hexArray = hexMsg.split(
            "-".toRegex()
        )

        val bytes = ByteArray(
            hexArray.size
        )

        for (i in hexArray.indices) {
            bytes[i] = Integer.parseInt(
                hexArray[i],
                16
            ).toByte()
        }

        out.write(
            bytes
        )
    }

}