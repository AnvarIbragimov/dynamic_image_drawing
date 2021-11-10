package mobile.uz.dynamic_image_drawing;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

    private Paint paint;
    private Path path;
    public Bitmap mBitmap;
    public ProgressDialog pd;
    final Point p1 = new Point();
    public Canvas canvas;

    //Bitmap mutableBitmap ;
    public MyView(Context context) {

        super(context);

        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        pd = new ProgressDialog(context);
        this.paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(15f);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.elephant).copy(Bitmap.Config.ARGB_8888, true);
        this.path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        this.paint.setColor(Color.BLUE);

        canvas.drawBitmap(mBitmap, 0, 0, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                p1.x = (int) x;
                p1.y = (int) y;
                final int sourceColor = mBitmap.getPixel((int) x, (int) y);
                final int targetColor = paint.getColor();
                new TheTask(mBitmap, p1, sourceColor, targetColor).execute();
                invalidate();
        }
        return true;
    }

    public void clear() {
        path.reset();
        invalidate();
    }

    public int getCurrentPaintColor() {
        return paint.getColor();
    }

    public void changePaintColor(int color) {
        this.paint.setColor(color);
    }

    class TheTask extends AsyncTask<Void, Integer, Void> {

        Bitmap bmp;
        Point pt;
        int replacementColor, targetColor;

        public TheTask(Bitmap bm, Point p, int sc, int tc) {
            this.bmp = bm;
            this.pt = p;
            this.replacementColor = tc;
            this.targetColor = sc;
            pd.setMessage("Filling....");
            pd.show();
        }

        @Override
        protected void onPreExecute() {
            pd.show();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected Void doInBackground(Void... params) {
            FloodFill1 f = new FloodFill1();
            f.floodFill(bmp, pt, targetColor, replacementColor);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            pd.dismiss();
            invalidate();
        }
    }
}

