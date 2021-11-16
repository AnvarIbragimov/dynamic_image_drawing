package mobile.uz.dynamic_image_drawing

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import androidx.annotation.ColorInt

@SuppressLint("AppCompatCustomView")
class MyImageView : ImageView {

    private var replaceColor: Int = Color.GREEN // green color by default
    private var type: AlgorithmType = AlgorithmType.Recursion // default fill algorithm
    private var squareSize: Int = 30 // squareSize * squareSize pixels
    private var animationDuration: Long = 200 // 0.2 seconds by default
    private var bitmap: Bitmap? = null // current bitmap

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { e ->
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    val point = Point(event.x.toInt(), event.y.toInt())
                    bitmap?.let { b -> setToFillBitmap(b, point) }
                }
                else -> {
                    // nothing
                }
            }
        }
        return true
    }

    private fun setToFillBitmap(b: Bitmap, point: Point) {
        when (type) {
            AlgorithmType.Recursion ->
                if (b.getPixel(point.x, point.y) != replaceColor &&
                    b.getPixel(point.x, point.y) != Color.BLACK
                ) FillAlgorithms.useRecursion(
                    this, b, point, Color.WHITE,
                    replaceColor, squareSize, animationDuration
                )
        }
    }

    fun generateBitmap() {
        bitmap = FillAlgorithms.bitmapMatrix(this, squareSize)
        this.setImageBitmap(bitmap)
    }

    fun setReplaceColor(@ColorInt colorId: Int) {
        replaceColor = colorId
    }

    fun setAnimationDuration(duration: Long) {
        if (duration in 200..5000) animationDuration = duration
    }

    fun setSquareSize(size: Int) {
        if (size in 20..this.width) squareSize = size
    }

    fun setAlgorithm(type: AlgorithmType) {
        this.type = type
    }

    fun getAlgorithms(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add(AlgorithmType.Recursion.toString())
        return list
    }

    enum class AlgorithmType { Recursion }
}