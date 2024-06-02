package good.damn.espbluetooth.view_holders

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BluetoothDeviceViewHolder(
    layout: ViewGroup,
    private val mTextViewName: TextView,
    private val mTextViewMac: TextView
): RecyclerView.ViewHolder(
    layout
) {
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