package good.damn.espbluetooth.adapters

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import android.util.Log
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import good.damn.espbluetooth.listeners.OnDeviceClickListener
import good.damn.espbluetooth.services.PermissionService
import good.damn.espbluetooth.view_holders.BluetoothDeviceViewHolder

class BluetoothDevicesAdapter(
    private val mDevices: Array<BluetoothDevice>,
    private val mOnDeviceClickListener: OnDeviceClickListener
): RecyclerView.Adapter<BluetoothDeviceViewHolder>() {

    companion object {
        private const val TAG = "BluetoothDevicesAdapter"
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BluetoothDeviceViewHolder {
        return BluetoothDeviceViewHolder.create(
            parent.context
        )
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${mDevices.size}")
        return mDevices.size
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(
        holder: BluetoothDeviceViewHolder,
        position: Int
    ) {
        val device = mDevices[position]

        holder.setDeviceName(
            device.name
        )

        holder.setDeviceMac(
            device.address
        )

        holder.setOnDeviceClickListener(
            mOnDeviceClickListener
        )
    }
}