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
            postInvalidate();
            return true;

        }

        return false;
    }

}

