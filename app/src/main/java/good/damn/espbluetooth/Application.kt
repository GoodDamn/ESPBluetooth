package good.damn.espbluetooth

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class Application
: Application() {
    companion object {
        var DENSITY = 1f
        var WIDTH = 1
        const val UUID = "00001101-0000-1000-8000-00805F9B34FB"

        val CHARSET_ASCII = Charsets.US_ASCII

        private val HANDLER = Handler(
            Looper.getMainLooper()
        )

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

        fun toastMain(
            msg: String,
            context: Context
        ) {
            ui {
                toast(msg,context)
            }
        }

        fun ui(
            run: Runnable
        ) {
            HANDLER.post(
                run
            )
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