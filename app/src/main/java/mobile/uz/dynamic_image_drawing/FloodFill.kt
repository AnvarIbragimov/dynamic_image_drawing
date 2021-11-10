package mobile.uz.dynamic_image_drawing

import android.graphics.Bitmap
import android.graphics.Point
import java.util.*

class FloodFill {

    companion object {
        fun floodFill(
            image: Bitmap, node: Point, targetC: Int,
            replaceC: Int
        ) {
            var mNode: Point? = node
            val width = image.width
            val height = image.height
            if (targetC != replaceC) {
                val queue: Queue<Point> = LinkedList()
                do {
                    var x: Int = node.x
                    val y: Int = node.y
                    while (x > 0 && image.getPixel(x - 1, y) == targetC) {
                        x--
                    }
                    var spanUp = false
                    var spanDown = false
                    while (x < width && image.getPixel(x, y) == targetC) {
                        image.setPixel(x, y, replaceC)
                        if (!spanUp && y > 0 && image.getPixel(x, y - 1) == targetC) {
                            queue.add(Point(x, y - 1))
                            spanUp = true
                        } else if (spanUp && y > 0 && image.getPixel(x, y - 1) != targetC) {
                            spanUp = false
                        }
                        if (!spanDown && y < height - 1 && image.getPixel(x, y + 1) == targetC) {
                            queue.add(Point(x, y + 1))
                            spanDown = true
                        } else if (spanDown && y < height - 1 && image.getPixel(
                                x,
                                y + 1
                            ) != targetC
                        ) {
                            spanDown = false
                        }
                        x++
                    }
                    //Handler(Looper.getMainLooper()).postDelayed({}, 100);
                } while (queue.poll().also { mNode = it } != null)
            }
        }
    }
}