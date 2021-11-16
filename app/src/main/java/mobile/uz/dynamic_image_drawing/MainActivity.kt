package mobile.uz.dynamic_image_drawing

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_size -> {
                // iv_1 equal to iv_2
                showImageSize(iv_1)
            }
            R.id.bt_generate -> {
                iv_1.setSquareSize(20)
                iv_1.generateBitmap()
                iv_2.setSquareSize(60)
                iv_2.generateBitmap()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBasics()
        initListeners()
        initSpinnersItems()
    }

    private fun initBasics() {
        iv_1.setReplaceColor(Color.RED)
        iv_2.setReplaceColor(Color.YELLOW)
    }

    private fun initSpinnersItems() {
        val al = iv_1.getAlgorithms()
        sp_1.setItems(al)
        sp_2.setItems(al)
    }

    @SuppressLint("SetTextI18n")
    private fun initListeners() {
        bt_size.setOnClickListener(this)
        bt_generate.setOnClickListener(this)

        sp_1.setOnItemSelectedListener { view, position, id, item ->
        }
        sp_1.setOnItemSelectedListener { view, position, id, item ->
        }

        speed_seekbar.onProgressChanged { endPoint ->
            endPoint?.let { process ->
                tv_speed.text = "${Utils.formatCardBalance(process / 1000.0).toDouble()} s"
                iv_1.setAnimationDuration(process.toLong())
                iv_2.setAnimationDuration(process.toLong())
            }
        }
    }

    private fun showImageSize(imageView: ImageView) {
        val text = "Size: ${imageView.width} x ${imageView.height}"
        val builder = AlertDialog.Builder(this)
        builder.setMessage(text)
            .setCancelable(true)
            .setPositiveButton("OK") { dialog, _ -> dialog.cancel() }
        val alert: AlertDialog = builder.create()
        alert.show()
    }
}