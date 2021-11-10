package mobile.uz.dynamic_image_drawing

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {

        private val algorithms = arrayListOf("Recursion")

    }

    private var bitmap1: Bitmap? = null
    private var bitmap2: Bitmap? = null
    private var animationTime = 200L

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_size -> {
                showImageSize(iv_1)
            }
            R.id.bt_generate -> {
                bitmap1 = bitmapMatrix(iv_1)
                iv_1.setImageBitmap(bitmap1)
                bitmap2 = bitmapMatrix(iv_2)
                iv_2.setImageBitmap(bitmap2)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListeners()
        initSpinnersItems()
    }

    private fun initSpinnersItems() {
        sp_1.setItems(algorithms)
        sp_2.setItems(algorithms)
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
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
                animationTime = process.toLong()
            }
        }

        iv_1.setOnTouchListener { view, event ->
            when (event?.action) {
                MotionEvent.ACTION_UP -> {
                    val point = Point()
                    event.x.toInt().toString().showInLog()
                    event.y.toInt().toString().showInLog()
                    point.x = event.x.toInt()
                    point.y = event.y.toInt()
                    bitmap1?.let { b -> fillBlack(iv_1, b, point, Color.WHITE, Color.BLACK) }
                }
            }
            true
        }

        iv_2.setOnTouchListener { view, event ->
            when (event?.action) {
                MotionEvent.ACTION_UP -> {
                    val point = Point()
                    event.x.toInt().toString().showInLog()
                    event.y.toInt().toString().showInLog()
                    point.x = event.x.toInt()
                    point.y = event.y.toInt()
                    bitmap2?.let { b -> fillBlack(iv_2, b, point, Color.WHITE, Color.BLACK) }
                }
            }
            true
        }
    }

    private fun fillBlack(imageView: ImageView, bitmap: Bitmap, point: Point, tc: Int, rc: Int) {
        if (bitmap.width >= point.x && 0 <= point.x && bitmap.height >= point.y && 0 <= point.y) {
            if (bitmap.getPixel(point.x, point.y) == tc) {
                bitmap.setPixel(point.x, point.y, rc)
                val startPX = point.x - point.x % 30
                val startPY = point.y - point.y % 30
                val endX = if (startPX + 30 < bitmap.width) startPX + 30 else bitmap.width
                val endY = if (startPY + 30 < bitmap.height) startPY + 30 else bitmap.height
                fillSquare(bitmap, startPX, endX, startPY, endY, rc)

                Handler(Looper.getMainLooper()).postDelayed({
                    if (startPX - 30 >= 0 && startPX - 30 <= bitmap.width)
                        fillBlack(imageView, bitmap, Point(startPX - 30, point.y), tc, rc)
                }, animationTime)
                Handler(Looper.getMainLooper()).postDelayed({
                    if (startPX + 30 >= 30 && startPX + 30 < bitmap.width)
                        fillBlack(imageView, bitmap, Point(startPX + 30, point.y), tc, rc)
                }, animationTime)
                Handler(Looper.getMainLooper()).postDelayed({
                    if (startPY - 30 >= 0 && startPY - 30 <= bitmap.height)
                        fillBlack(imageView, bitmap, Point(point.x, startPY - 30), tc, rc)
                }, animationTime)
                Handler(Looper.getMainLooper()).postDelayed({
                    if (startPY + 30 >= 30 && startPY + 30 < bitmap.height)
                        fillBlack(imageView, bitmap, Point(point.x, startPY + 30), tc, rc)
                }, animationTime)
            }
            imageView.setImageBitmap(bitmap)
        }
    }

    private fun bitmapMatrix(imageView: ImageView): Bitmap {
        val width = imageView.width
        val height = imageView.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        for (i in 0..width step 30) {
            for (j in 0..height step 30) {
                val endX = if (i + 30 < width) i + 30 else width
                val endY = if (j + 30 < height) j + 30 else height
                val color = if (Random.nextBoolean()) Color.BLACK else Color.WHITE
                fillSquare(bitmap, i, endX, j, endY, color)
            }

        }
        return bitmap
    }

    private fun fillSquare(
        bitmap: Bitmap,
        startPX: Int,
        endPX: Int,
        startPY: Int,
        endPY: Int,
        color: Int
    ) {
//        "sx:$startPX, ex:$endPX, sy:$startPY, ey:$endPY".showInLog()
        for (i in startPX until endPX) {
            for (j in startPY until endPY) bitmap.setPixel(i, j, color)
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