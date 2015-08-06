package lcc.videograph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

import java.util.List;

/**
 * Draws an XY scatter plot on a SurfaceView.
 * <p>Company: written for Leeward Community College</p>
 * @author Jennifer R. McFatridge
 * @version 1.0
 */
public class GraphView extends SurfaceView {
    List<Double> time = DrawView.getTime();
    List<Double> xValues = DrawView.getxTap();


    private Graph graph;
    public GraphView(Context context) {
        super(context);
    }
    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public GraphView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.DKGRAY);
        gridPaint.setTextSize(30);
        if(graph == null){
            init();
        }
        Line[] verticalLines = graph.getVerticalGridlines();
        String[] verticalLabels = graph.getVerticalLabels();
        for(int i = 0; i< verticalLines.length; i++){
            canvas.drawLine(verticalLines[i].startX,verticalLines[i].startY,verticalLines[i].endX,verticalLines[i].endY, gridPaint );
            canvas.drawText(verticalLabels[i], verticalLines[i].startX, graph.getOrigin().y + 30, gridPaint);
        }
        Line[] horizontalLines = graph.getHorizontalGridlines();
        String[] horizontalLabels = graph.getHorizontalLabels();
        for(int i = 0; i< horizontalLines.length; i++){
            canvas.drawLine(horizontalLines[i].startX,horizontalLines[i].startY,horizontalLines[i].endX,horizontalLines[i].endY, gridPaint );
            canvas.drawText(horizontalLabels[i], graph.getOrigin().x - 30, horizontalLines[i].startY, gridPaint);
        }
        Paint plotPaint = new Paint();
        plotPaint.setColor(Color.BLUE);
        Point[] dataPoints = graph.getPoints();
        for(int i = 0; i< dataPoints.length; i++){
            canvas.drawCircle(dataPoints[i].x , dataPoints[i].y , 10, plotPaint);
        }
        Paint fitPaint = new Paint();
        fitPaint.setStrokeWidth(2);
        fitPaint.setColor(Color.RED);
        Point[] fitPoints = graph.getFitPoints();
        for(int i = 1; i< fitPoints.length; i++){
            canvas.drawLine(fitPoints[i-1].x, fitPoints[i-1].y, fitPoints[i].x, fitPoints[i].y, fitPaint);
        }
    }
    private void init(){
        Rect drawingArea = new Rect(getLeft()+getPaddingLeft(), getTop()+getPaddingTop(), getRight()-getPaddingRight(), getBottom()-getPaddingBottom());
        //double [] xPoints = {0,0.267,0.50,0.701,0.901,1.101,1.301,1.502,1.702};
        //double [] yPoints = {0,0.10,0.2,0.3,0.4,0.5,0.6,0.7,0.8};
        double tPoints[] = new double[time.size()];
        for(int i = 0; i<time.size(); i++){
            tPoints[i] = time.get(i);
            String arrTime = String.valueOf(tPoints[i]);
            Log.d("GraphTime", arrTime);
        }
        double xPoints[] = new double[xValues.size()];
        for(int i = 0; i<xValues.size(); i++){
            xPoints[i] = xValues.get(i);
            String arrX = String.valueOf(xPoints[i]);
            Log.d("GraphX", arrX);
        }


        //T goes in xpoints - x goes in y points
        DataSeries data = new DataSeries(tPoints, xPoints, time.size());
        graph = new Graph(data, drawingArea);
        graph.setFitType(CurveFit.QUAD);
    }
}