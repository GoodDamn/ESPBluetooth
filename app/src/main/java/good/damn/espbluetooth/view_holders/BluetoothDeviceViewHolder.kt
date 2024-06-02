package good.damn.espbluetooth.view_holders

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import good.damn.espbluetooth.Application
import good.damn.espbluetooth.listeners.OnDeviceClickListener
import good.damn.espbluetooth.utils.LayoutUtils

class BluetoothDeviceViewHolder(
    layout: ViewGroup,
    private val mTextViewName: TextView,
    private val mTextViewMac: TextView
): RecyclerView.ViewHolder(
    layout
) {
    private var mOnDeviceClickListener: OnDeviceClickListener? = null

    fun setOnDeviceClickListener(
        l: OnDeviceClickListener
    ) {
        mOnDeviceClickListener = l
    }

    init {
        layout.setOnClickListener {
            mOnDeviceClickListener?.onDevice(
                mTextViewMac.text.toString()
            )
        }
    }

    fun setDeviceName(
        name: String
    ) {
        mTextViewName.text = name
    }

    fun setDeviceMac(
        mac: String
    ) {
        mTextViewMac.text = mac
    }

    companion object {
        fun create(
            context: Context
        ): BluetoothDeviceViewHolder {

            val layout = LinearLayout(
                context
            )

            val textViewName = TextView(
                context
            )

            val textViewMac = TextView(
                context
            )

            layout.orientation = LinearLayout
                .VERTICAL

            textViewName.text = "{deviceName}"
            textViewMac.text = "{deviceMac}"

            textViewName.textSize = 25f
            textViewMac.textSize = 16f

            layout.setPadding(
                (Application.WIDTH * 0.1f).toInt(),
                (15 * Application.DENSITY).toInt(),
                0,
                0
            )

            layout.addView(
                textViewName,
                -2,
                -2
            )

            layout.addView(
                textViewMac,
                -2,
                -2
            )

            return BluetoothDeviceViewHolder(
                layout,
                textViewName,
                textViewMac
            )
        }
    }
}