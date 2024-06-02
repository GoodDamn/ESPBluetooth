package good.damn.espbluetooth.services

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import kotlin.random.Random

class PermissionService {

    companion object {
        fun isAcceptedBluetooth(
            context: Context
        ): Boolean {

            val permission = if (
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                ) Manifest.permission.BLUETOOTH_CONNECT
            else Manifest.permission.BLUETOOTH

            return ActivityCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    var requestCode = -1

    fun request(
        activity: Activity,
        permission: String
    ) {
        request(
            activity,
            arrayOf(permission)
        )
    }
    fun request(
        activity: Activity,
        permissions: Array<String>
    ) {
        requestCode = Random
            .nextInt(50)
        ActivityCompat.requestPermissions(
            activity,
            permissions,
            requestCode
        )
    }
}