package mobile.uz.dynamic_image_drawing

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Toast

fun String.removeSpace(): String = this.replace("\\s+".toRegex(), "")

fun String.showInLog() = Log.e("TTT : ", this)

fun String.showInLog(logName: String) = Log.e("TTT $logName: ", this)

fun String.toastShort(context: Context) = Toast.makeText(context, this, Toast.LENGTH_SHORT).show()

fun String.toastLong(context: Context) = Toast.makeText(context, this, Toast.LENGTH_LONG).show()

fun SeekBar.onStopTrackingTouch(onStopTrackingTouch: (Int?) -> Unit) {
    this.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {}
        override fun onStartTrackingTouch(p0: SeekBar?) {}
        override fun onStopTrackingTouch(p0: SeekBar?) {
            onStopTrackingTouch.invoke(p0?.progress)
        }
    })
}

fun Spinner.onItemSelected(onItemSelected: (Int) -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            onItemSelected.invoke(p2)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }
}