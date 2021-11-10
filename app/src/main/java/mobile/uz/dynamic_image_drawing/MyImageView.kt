package mobile.uz.dynamic_image_drawing

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View

class MyImageView : View {

    private var paint: Paint = Paint()
    private var path: Path
    private var mBitmap: Bitmap? = null
    private val p1 = Point()
    private var canvas = Canvas()

    constructor(context: Context) : super(context) {
        paint.isAntiAlias = true
        this.paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = 15f
        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.elephant)
            .copy(Bitmap.Config.ARGB_8888, true)
        path = Path()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let { cv ->
            this.canvas = cv
            this.paint.color = Color.BLUE
            mBitmap?.let { cv.drawBitmap(it, 0f, 0f, paint) }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { e ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    p1.x = e.x.toInt()
                    p1.y = e.y.toInt()
                    val sourceColor = mBitmap?.getPixel(e.x.toInt(), e.y.toInt())
                    val targetColor = paint.color
                    if (sourceColor != null && mBitmap != null) {
                        FloodFill1().floodFill(mBitmap!!, p1, sourceColor, targetColor)
                    }
                    invalidate()
                }
            }
        }

        return true
    }

    fun clear() {
        path.reset()
        invalidate()
    }

    fun getCurrentPaintColor() = paint.color

    fun changePaintColor(color: Int) {
        this.paint.color = color
    }

//    private fun paintThePixel(bp: Bitmap, p: Point, tc: Int, rc: Int) {
//        if(bp.get)
//    }


}