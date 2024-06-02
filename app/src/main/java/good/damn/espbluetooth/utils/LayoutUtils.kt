package good.damn.espbluetooth.utils

import android.widget.LinearLayout
import good.damn.espbluetooth.Application

class LayoutUtils {
    companion object {
        fun linearLayout(
            leftMargin: Int = 0,
            topMargin: Int = 0,
            width: Int,
            height: Int
        ): LinearLayout.LayoutParams {
            val params = LinearLayout.LayoutParams(
                width,
                height
            )

            params.leftMargin = (leftMargin * Application.DENSITY)
                .toInt()

            params.topMargin = (topMargin * Application.DENSITY)
                .toInt()

            return params
        }
    }
}