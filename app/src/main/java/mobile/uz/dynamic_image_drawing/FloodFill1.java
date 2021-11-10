package mobile.uz.dynamic_image_drawing;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Looper;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Handler;

// flood fill
public class FloodFill1 {

    public void floodFill(Bitmap image, Point node, int targetColor, int replacementColor) {

        int width = image.getWidth();
        int height = image.getHeight();
        int target = targetColor;
        int replacement = replacementColor;

        if (target != replacement) {
            Queue<Point> queue = new LinkedList<>();
            do {

                int x = node.x;
                int y = node.y;
                while (x > 0 && image.getPixel(x - 1, y) == target) {
                    x--;
                }

                boolean spanUp = false;
                boolean spanDown = false;
                while (x < width && image.getPixel(x, y) == target) {
                    image.setPixel(x, y, replacement);
                    if (!spanUp && y > 0 && image.getPixel(x, y - 1) == target) {
                        queue.add(new Point(x, y - 1));
                        spanUp = true;
                    } else if (spanUp && y > 0 && image.getPixel(x, y - 1) != target) {
                        spanUp = false;
                    }
                    if (!spanDown && y < height - 1 && image.getPixel(x, y + 1) == target) {
                        queue.add(new Point(x, y + 1));
                        spanDown = true;
                    } else if (spanDown && y < (height - 1) && image.getPixel(x, y + 1) != target) {
                        spanDown = false;
                    }
                    x++;
                }
                //Handler(Looper.getMainLooper()).postDelayed({}, 100);
            } while ((node = queue.poll()) != null);
        }
    }
}

