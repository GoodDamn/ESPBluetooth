package good.damn.espbluetooth.services

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import kotlin.random.Random

class PermissionService {

    companion object {
        fun isAcceptedBluetooth(
            context: Context
        ): Boolean {
            return ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    var requestCode = -1

    fun request(
        activity: Activity,
        permission: String
    ) {
        requestCode = Random
            .nextInt(50)
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(permission),
            requestCode
        )
    }
}