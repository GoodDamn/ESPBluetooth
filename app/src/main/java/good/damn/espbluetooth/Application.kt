package good.damn.espbluetooth

import android.app.Application
import android.content.Context
import android.widget.Toast

class Application
: Application() {
    companion object {
        fun toast(
            msg: String,
            context: Context
        ) {
            Toast.makeText(
                context,
                msg,
                Toast.LENGTH_LONG
            ).show()
        }
    }

}