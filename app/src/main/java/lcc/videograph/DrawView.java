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

    private Paint doodle_paint = new Paint();
    // Store circles to draw each time the user touches down
    private List<Point> circlePoints;
    public static List<Integer> time = new ArrayList<Integer>();

    final View drawInstance = (View) findViewById(R.id.drawView);


    public DrawView(Context context) {
        super(context);
        init(null, 0);

    }

    public DrawView(Context context,AttributeSet attrs){
        super(context, attrs);
        circlePoints = new ArrayList<Point>();
        init(attrs, 0);

    }

    public DrawView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init(attrs, defStyle);

    }

    public void init(AttributeSet attrs, int defStyle){
        doodle_paint.setColor(Color.YELLOW);
        doodle_paint.setAntiAlias(true);
        doodle_paint.setStyle(Paint.Style.FILL);

    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //canvas.drawLine(0, 0, getWidth(), getHeight(), doodle_paint);
        //canvas.drawPath(path, doodle_paint);
        //canvas.drawCircle(point.x, point.y, 20, doodle_paint);
        for (Point p : circlePoints) {
            canvas.drawCircle(p.x, p.y, 30, doodle_paint);

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN ) {
            float touchX = motionEvent.getRawX();
            float touchY = motionEvent.getRawY();
            circlePoints.add(new Point(Math.round(touchX), Math.round(touchY)));
            String points = String.valueOf(circlePoints);
            Log.d("MyTag", points);
            //Used to get() the current time in video.
            int currentTime = (Integer)getTag();
            time.add(currentTime);
            String cT = String.valueOf(time);
            Log.d("MyTag3", cT);
            drawInstance.setTag(time);
            // indicate view should be redrawn
            postInvalidate();
            return true;

        }

        return false;
    }

    public static void setTime(List<Integer> time) {
        DrawView.time = time;
    }

    public static List<Integer> getTime() {
        return time;
    }
}

