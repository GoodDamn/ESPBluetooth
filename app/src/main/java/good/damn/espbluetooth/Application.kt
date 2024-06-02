package good.damn.espbluetooth

import android.app.Application
import android.content.Context
import android.widget.Toast

class Application
: Application() {
    companion object {
        var DENSITY = 1f
        var WIDTH = 1
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

    override fun onCreate() {
        super.onCreate()

        val metrics = resources
            .displayMetrics

        DENSITY = metrics.density
        WIDTH = metrics.widthPixels
    }

}