package mobile.uz.dynamic_image_drawing

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import androidx.annotation.ColorInt
import kotlin.random.Random

@SuppressLint("AppCompatCustomView")
class MyImageView : ImageView {

    private var replaceColor: Int = Color.GREEN // green color by default
    private var type: AlgorithmType = AlgorithmType.Recursion // default fill algorithm
    private var squareSize: Int = 30 // squareSize * squareSize pixels
    private var animationDuration: Long = 200 // 0.2 seconds by default
    private var bitmap: Bitmap? = null // current bitmap

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { e ->
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    val point = Point()
                    point.x = event.x.toInt()
                    point.y = event.y.toInt()
                    bitmap?.let { b ->
                        when (type) {
                            AlgorithmType.Recursion ->
                                if (b.getPixel(point.x, point.y) != replaceColor &&
                                    b.getPixel(point.x, point.y) != Color.BLACK
                                ) useRecursion(this, b, point, Color.WHITE, replaceColor)
                        }
                    }
                }
                else -> {
                    // nothing
                }
            }
        }
        return true
    }

    fun setReplaceColor(@ColorInt colorId: Int) {
        replaceColor = colorId
    }

    fun setAnimationDuration(duration: Long) {
        if (duration in 200..5000) animationDuration = duration
    }

    fun setSquareSize(size: Int) {
        if (size in 1..this.width) squareSize = size
    }

    fun setAlgorithm(type: AlgorithmType) {
        this.type = type
    }

    fun getAlgorithms(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add(AlgorithmType.Recursion.toString())
        return list
    }

    fun generateBitmap() {
        bitmap = bitmapMatrix(this)
        this.setImageBitmap(bitmap)
    }

    private fun bitmapMatrix(imageView: ImageView): Bitmap {
        val width = imageView.width
        val height = imageView.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        for (i in 0..width step squareSize) {
            for (j in 0..height step squareSize) {
                val endX = if (i + squareSize < width) i + squareSize else width
                val endY = if (j + squareSize < height) j + squareSize else height
                val color = if (Random.nextBoolean()) Color.BLACK else Color.WHITE
                fillSquare(bitmap, color, i, endX, j, endY)
            }
        }
        return bitmap
    }

    private fun fillSquare(
        bitmap: Bitmap, color: Int,
        startPX: Int, endPX: Int,
        startPY: Int, endPY: Int
    ) {
        for (i in startPX until endPX)
            for (j in startPY until endPY) bitmap.setPixel(i, j, color)
    }

    private fun useRecursion(iv: ImageView, bitmap: Bitmap, point: Point, tc: Int, rc: Int) {
        if (bitmap.width >= point.x && 0 <= point.x && bitmap.height >= point.y && 0 <= point.y) {
            if (bitmap.getPixel(point.x, point.y) == tc) {
                bitmap.setPixel(point.x, point.y, rc)
                val startPX = point.x - point.x % squareSize
                val startPY = point.y - point.y % squareSize
                val endX =
                    if (startPX + squareSize < bitmap.width) startPX + squareSize else bitmap.width
                val endY =
                    if (startPY + squareSize < bitmap.height) startPY + squareSize else bitmap.height

                fillSquare(bitmap, rc, startPX, endX, startPY, endY)

                Handler(Looper.getMainLooper()).postDelayed({
                    if (startPX - squareSize >= 0 && startPX - squareSize <= bitmap.width)
                        useRecursion(iv, bitmap, Point(startPX - squareSize, point.y), tc, rc)
                }, animationDuration)
                Handler(Looper.getMainLooper()).postDelayed({
                    if (startPX + squareSize >= squareSize && startPX + squareSize < bitmap.width)
                        useRecursion(iv, bitmap, Point(startPX + squareSize, point.y), tc, rc)
                }, animationDuration)
                Handler(Looper.getMainLooper()).postDelayed({
                    if (startPY - squareSize >= 0 && startPY - squareSize <= bitmap.height)
                        useRecursion(iv, bitmap, Point(point.x, startPY - squareSize), tc, rc)
                }, animationDuration)
                Handler(Looper.getMainLooper()).postDelayed({
                    if (startPY + squareSize >= squareSize && startPY + squareSize < bitmap.height)
                        useRecursion(iv, bitmap, Point(point.x, startPY + squareSize), tc, rc)
                }, animationDuration)
            }
            iv.setImageBitmap(bitmap)
        }
    }

    enum class AlgorithmType { Recursion }
}