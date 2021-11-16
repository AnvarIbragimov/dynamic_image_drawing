package mobile.uz.dynamic_image_drawing

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import kotlin.random.Random

class FillAlgorithms {

    companion object {

        fun bitmapMatrix(imageView: ImageView, size: Int): Bitmap {
            val width = imageView.width
            val height = imageView.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            for (i in 0..width step size) {
                for (j in 0..height step size) {
                    val endX = if (i + size < width) i + size else width
                    val endY = if (j + size < height) j + size else height
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

        fun useRecursion(
            iv: ImageView, bitmap: Bitmap,
            point: Point,
            tc: Int, rc: Int,
            size: Int,
            duration: Long
        ) {
            if (bitmap.width >= point.x && 0 <= point.x && bitmap.height >= point.y && 0 <= point.y) {
                if (bitmap.getPixel(point.x, point.y) == tc) {
                    bitmap.setPixel(point.x, point.y, rc)
                    val startPX = point.x - point.x % size
                    val startPY = point.y - point.y % size
                    val endX =
                        if (startPX + size < bitmap.width) startPX + size else bitmap.width
                    val endY =
                        if (startPY + size < bitmap.height) startPY + size else bitmap.height

                    fillSquare(bitmap, rc, startPX, endX, startPY, endY)

                    Handler(Looper.getMainLooper()).postDelayed({
                        if (startPX - size >= 0 && startPX - size <= bitmap.width)
                            useRecursion(
                                iv, bitmap, Point(startPX - size, point.y),
                                tc, rc, size, duration
                            )
                    }, duration)
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (startPX + size >= size && startPX + size < bitmap.width)
                            useRecursion(
                                iv, bitmap, Point(startPX + size, point.y),
                                tc, rc, size, duration
                            )
                    }, duration)
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (startPY - size >= 0 && startPY - size <= bitmap.height)
                            useRecursion(
                                iv, bitmap, Point(point.x,startPY - size),
                                tc, rc, size, duration
                            )
                    }, duration)
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (startPY + size >= size && startPY + size < bitmap.height)
                            useRecursion(
                                iv, bitmap, Point(point.x, startPY + size),
                                tc, rc, size, duration
                            )
                    }, duration)
                }
                iv.setImageBitmap(bitmap)
            }
        }


    }
}