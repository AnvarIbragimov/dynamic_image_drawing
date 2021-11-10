package mobile.uz.dynamic_image_drawing

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class DynamicImageView : View {

    private var rect = Rect()
    private var mBitmap: Bitmap? = null
    private var mDrawable: BitmapDrawable? = null

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {

        rect = Rect(0, 0, 512, 512)
        mBitmap = generateBitmap(loadBitmap())
        mDrawable = BitmapDrawable(context.resources, mBitmap)
        mDrawable?.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        mDrawable?.bounds = rect
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.let { mDrawable?.draw(it) }
    }

    private fun loadBitmap(): Bitmap {
        // included only for example sake
        val paint = Paint()
        paint.color = Color.BLACK
        val bm = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)
        canvas.drawRect(0f, 0f, 100f, 100f, paint)
        return bm
    }

    private fun generateBitmap(bitmap: Bitmap): Bitmap {
//        val paint = Paint()
//        val bm = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888)
        bitmap.width.toString().showInLog()
        bitmap.height.toString().showInLog()
        for (i in 0 until bitmap.width) {
            for (j in 0 until bitmap.height)
                if (Random.nextInt(0, 10) / 2 == 0) {
                    bitmap.setPixel(i, j, Color.WHITE)
                } else {
                    //bitmap.setPixel(i, j, Color.WHITE)
                }
        }
        return bitmap
    }

    private fun bitmapMatrix():Bitmap{
        val bitmap = Bitmap.createBitmap(24,24,Bitmap.Config.ARGB_8888)
        for (i in 0 until bitmap.width) {
            for (j in 0 until bitmap.height)
                if (Random.nextBoolean()) {
                    bitmap.setPixel(i, j, Color.WHITE)
                } else {
                    bitmap.setPixel(i, j, Color.WHITE)
                }
        }
        return bitmap
    }
}