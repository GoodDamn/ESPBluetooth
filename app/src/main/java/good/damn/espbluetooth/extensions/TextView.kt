package good.damn.espbluetooth.extensions

import android.widget.TextView

fun TextView.addText(
    msg: String
) {
    text = text.toString() + "$msg\n"
}