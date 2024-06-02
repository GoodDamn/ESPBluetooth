package good.damn.espbluetooth.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import good.damn.espbluetooth.Application

class DeviceActivity
: AppCompatActivity() {

    companion object {
        private const val TAG = "DeviceActivity"
        const val KEY_MAC = "mac"
    }

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
    }

}