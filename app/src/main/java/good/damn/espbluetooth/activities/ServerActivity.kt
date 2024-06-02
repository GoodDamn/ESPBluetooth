package good.damn.espbluetooth.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class ServerActivity
: AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        val context = this

        val layout = LinearLayout(
            context
        )

        setContentView(
            layout
        )
    }

}