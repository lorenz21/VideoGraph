package lcc.videograph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Joseph on 7/31/2015.
 */
public class ScaleView extends View {


    ArrayList<Integer> xCorr = new ArrayList<Integer>();
    ArrayList <Integer> yCorr = new ArrayList<Integer>();
    int x1,y1;

    private Paint paint = new Paint();

    public ScaleView(Context context){
        super(context);
        init(null, 0);
    }

    public ScaleView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(attrs, 0);
    }

    public ScaleView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        init(attrs, defStyle);
    }

    public void init(AttributeSet attrs, int defStyle){
        paint.setColor(Color.YELLOW);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x1,y1,15,paint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            float touchX = motionEvent.getX();
            float touchY = motionEvent.getY();
            xCorr.add(new Integer(Math.round(touchX)));
            yCorr.add(new Integer(Math.round(touchY)));
            x1 = xCorr.get(0);
            y1 = yCorr.get(0);
            postInvalidate();
            return true;
        }
        return false;
    }

}
