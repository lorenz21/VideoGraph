package lcc.videograph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Joseph on 7/31/2015.
 */
public class ScaleView extends View {
    ArrayList<Integer> xCorr = new ArrayList<Integer>();
    ArrayList<Integer> yCorr = new ArrayList<Integer>();
    int x1 = 0;
    int x2 = 0;
    int y1 = 0;
    int y2 = 0;
    private int xCircle, yCircle;
    int count = 0;
    private Paint paint = new Paint();



    public ScaleView(Context context) {
        super(context);
        init(null, 0);
    }

    public ScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ScaleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public void init(AttributeSet attrs, int defStyle) {
        paint.setColor(Color.YELLOW);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(x1, y1, x2, y2, paint);
        if(count == 1) {
            canvas.drawCircle(xCircle, yCircle, 15, paint);
        }
        if(count == 2) {
            canvas.drawCircle(xCircle, yCircle, 15, paint);
            canvas.drawCircle(x2, y2, 15, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            float touchX = motionEvent.getRawX();
            float touchY = motionEvent.getRawY();
            xCorr.add(new Integer(Math.round(touchX)));
            yCorr.add(new Integer(Math.round(touchY)));
            xCircle = xCorr.get(0);
            yCircle = yCorr.get(0);
            count = count + 1;
            if (count == 2) {
                x1 = xCorr.get(0);
                y1 = yCorr.get(0);
                x2 = xCorr.get(1);
                y2 = yCorr.get(1);

            }


            postInvalidate();
            return true;

        }

        return false;

    }

}
