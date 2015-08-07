package lcc.videograph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joseph on 7/23/2015.
 */
public class DrawView extends View {

    private Paint paint = new Paint();
    // Store circles to draw each time the user touches down
    private  List<Point> circlePoints = new ArrayList<Point>();
    private static List<Double> time = new ArrayList<Double>();
    private static List<Double> xTap = new ArrayList<Double>();
    private static List<Double> yTap = new ArrayList<Double>();
    //private static double scale = ScaleActivity.getScale();




    public DrawView(Context context) {
        super(context);
        init(null, 0);

    }

    public DrawView(Context context,AttributeSet attrs){
        super(context, attrs);
        init(attrs, 0);

    }

    public DrawView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init(attrs, defStyle);

    }

    public void init(AttributeSet attrs, int defStyle){
        paint.setColor(Color.YELLOW);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        for (Point p : circlePoints) {
            canvas.drawCircle(p.x, p.y, 20, paint);

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN ) {
            float touchX = motionEvent.getRawX();
            float touchY = motionEvent.getRawY();
            double xTouch = (double)(touchX);
            double yTouch = (double)(touchY);
            circlePoints.add(new Point(Math.round(touchX), Math.round(touchY)));
            //xTap.add(scale * touchX);
            //yTap.add(scale * (touchY));
            //String points = String.valueOf(circlePoints);
            //Log.d("DrawView", points);
            //Used to get() the current time in video.
            int currentTime = (Integer)getTag();
            double dTime = (double)(currentTime);
            time.add(dTime);
            String cT = String.valueOf(time);
            Log.d("timeTest", cT);

            String xTapTest = String.valueOf(xTap);
            Log.d("xValues", xTapTest);

            String yTapTest = String.valueOf(yTap);
            Log.d("yValues", yTapTest);
            // indicate view should be redrawn
            postInvalidate();
            return true;

        }

        return false;
    }

    public static List<Double> getTime() {

        return time;
    }

    public static void setTime(List<Double> time) {

        DrawView.time = time;
    }

    public static List<Double> getxTap() {
        return xTap;
    }

    public static void setxTap(List<Double> xTap) {

        DrawView.xTap = xTap;
    }

    public static List<Double> getyTap() {

        return yTap;
    }

    public static void setyTap(List<Double> yTap) {

        DrawView.yTap = yTap;
    }
}

