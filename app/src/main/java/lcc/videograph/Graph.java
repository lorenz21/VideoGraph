package lcc.videograph;

import android.graphics.Point;
import android.graphics.Rect;

import java.text.DecimalFormat;

/**
 * The Graph class encapsulates all of the computations needed to draw a grid, plot points,
 * draw lines, fill areas under a curve. To paint grid lines, use the method getGridlines, which
 * returns an array of Lines.
 * <p>Company: written for Leeward Community College</p>
 * <p>Copyright 2015</p>
 * @author Jennifer R. McFatridge
 * @version 1.0
 */
public class Graph {
    private UnitSpace unitSpace;
    private int minTickSpacingX;
    private int minTickSpacingY;
    private DataSeries data;
    private int gridSeriesX = 1;
    private int gridSeriesY = 1;
    private int nx = 0;
    private int ny = 0;
    private String[] horizontalLabels;
    private String[] verticalLabels;
    private Line[] horizontalLines;
    private Line[] verticalLines;
    private CurveFit fit;
    private int curveType;
    private double[] fitParam;
    private Point[] fitPoints;



    /**
     * Constructs a Graph according to the data parameters entered.
     * @param minX the minimum value of the data being plotted on the x axis
     * @param minY the minimum value of the data being plotted on the y axis
     * @param maxX the maximum value of the data being plotted on the x axis
     * @param maxY the maximum value of the data being plotted on the y axis
     * @param area the rectangular area (in pixels) upon which this graph will be drawn
     */
    public Graph(double minX, double minY, double maxX, double maxY, Rect area){
        this.unitSpace = new UnitSpace(minX, minY, maxX, maxY, area);
        this.minTickSpacingX = 100; //30 pixels minimum size
        this.minTickSpacingY = 100; //30 pixels minimum size
        this.data = new DataSeries(); //creates an empty Data Series
        findGridScaleX();
        findGridScaleY();
        computeHorizontalGrid();
        computeVerticalGrid();
    }

    /**
     * Constructs a Graph according to the data parameters entered.
     * @param data the DataPoints which are to be plotted. By default, the graph autoscales to these points
     * @param area the rectangular area (in pixels) upon which this graph will be drawn
     */
    public Graph(DataSeries data, Rect area){
        this.unitSpace = new UnitSpace(data.getMinimumX(), data.getMinimumY(), data.getMaximumX(), data.getMaximumY(), area);
        this.minTickSpacingX = 100; //30 pixels minimum size
        this.minTickSpacingY = 100; //30 pixels minimum size
        this.data = data; //creates an empty Data Series
        findGridScaleX();
        findGridScaleY();
        computeHorizontalGrid();
        computeVerticalGrid();
    }



    /**
     * Autoscales the graph so that the grid lines are in the series (1,2,3..) (2,4,6..)(5,10,15..)
     * Determines the number of user units per grid line.. e.g. 10 m between grid lines
     * Also determines the power of ten of the series i.e. (1,2,3..) vs (10,20,30..)
     * @return the number of user units per grid line on the x axis of the graph
     */
    public double findGridScaleX(){
        boolean isFound = false;
        double tickSize = 0.0; //the amount of user units per tick
        while(!isFound && (nx <10 && nx >-10)){
            tickSize = minTickSpacingX/(unitSpace.getScaleX()*Math.pow(10,nx));
            if(tickSize >= 0.1 && tickSize < 1.0){
                isFound = true;
                gridSeriesX = 1;
            }
            else if(tickSize >= 1.0 && tickSize <2.0){
                isFound = true;
                gridSeriesX = 2;
            }
            else if(tickSize >=2.0 && tickSize <5.0){
                isFound = true;
                gridSeriesX = 5;
            }
            else{
                //not yet found the series, divide or multiply by ten
                if(tickSize < 1.0){
                    nx--;
                }
                else{
                    nx++;
                }
            }
        }
        return tickSize;

    }

    /**
     * Autoscales the graph so that the grid lines are in the series (1,2,3..) (2,4,6..)(5,10,15..)
     * Determines the number of user units per grid line.. e.g. 10 m between grid lines
     * Also determines the power of ten of the series i.e. (1,2,3..) vs (10,20,30..)
     * @return the number of user units per grid line on the y axis of the graph
     */
    public double findGridScaleY(){
        boolean isFound = false;
        double tickSize = 0.0; //the amount of user units per tick
        while(!isFound && (ny <10 && ny >-10)){
            tickSize = minTickSpacingY/(unitSpace.getScaleY()*Math.pow(10,ny));
            if(tickSize >= 0.1 && tickSize < 1.0){
                isFound = true;
                gridSeriesY = 1;
            }
            else if(tickSize >= 1.0 && tickSize <2.0){
                isFound = true;
                gridSeriesY = 2;
            }
            else if(tickSize >=2.0 && tickSize <5.0){
                isFound = true;
                gridSeriesY = 5;
            }
            else{
                //not yet found the series, divide or multiply by ten
                if(tickSize < 1.0){
                    ny--;
                }
                else{
                    ny++;
                }
            }
        }

        return tickSize;
    }




    /**
     * Returns an array of Points which are to be drawn. These points are in pixels.
     * @returns points
     */
    public Point[] getPoints(){
        DataPoint current;
        Point[] points = new Point[data.getNumPoints()];

        for(int i = 0; i < data.getNumPoints(); i++){
            current = data.getPointAt(i);
            points[i] = unitSpace.toPixel(current.x, current.y);

        }
        return points;
    }

    public void addPoint(double x, double y){
        data.addPoint(x,y);
        unitSpace.setMaxX(data.getMaximumX());
        unitSpace.setMaxY(data.getMaximumY());
        unitSpace.setMinX(data.getMinimumX());
        unitSpace.setMinY(data.getMinimumY());
        findGridScaleX();
        findGridScaleY();
        computeHorizontalGrid();
        computeVerticalGrid();
    }

    public void computeFit(){
        if(data.getNumPoints() > 1) {
            if (fit == null) {
                fit = new CurveFit(data, curveType);
                fitParam = fit.getParameters();
            }
            switch(curveType){
                case CurveFit.QUAD:
                    int length = unitSpace.toPixelX( unitSpace.getMaxX())-unitSpace.toPixelX( unitSpace.getMinX());
                    fitPoints = new Point[length];
                    int x;
                    for(int i = 0; i< length; i++){
                        x = (unitSpace.toPixelX(unitSpace.getMinX())) + i;
                        fitPoints[i] = new Point();
                        fitPoints[i].x = x;
                        fitPoints[i].y = unitSpace.toPixelY(fitParam[0] + fitParam[1]*unitSpace.toUserX(x) + fitParam[2]*unitSpace.toUserX(x)*unitSpace.toUserX(x));


                    }

                    break;
                case CurveFit.EXP:
                    break;

                default:
                    fitPoints = new Point[2];
                    fitPoints[0] = new Point();
                    fitPoints[0].x =   unitSpace.toPixelX( unitSpace.getMinX());
                    fitPoints[0].y =   unitSpace.toPixelY(fitParam[1] * unitSpace.getMinX() + fitParam[0]);
                    fitPoints[1] = new Point();
                    fitPoints[1].x =   unitSpace.toPixelX( unitSpace.getMaxX());
                    fitPoints[1].y =   unitSpace.toPixelY( fitParam[1]*unitSpace.getMaxX() + fitParam[0]);
                    break;

            }
        }
    }

    public Point[] getFitPoints(){
        if(fitPoints == null){
            computeFit();
        }
        return this.fitPoints;
    }

    public void setFitType(int curveType){
        this.curveType = curveType;
    }

    public int getCurveType(){
        return this.curveType;
    }

    public Line[] getVerticalGridlines(){
        return this.verticalLines;
    }

    private void computeHorizontalGrid(){
        //determines the left most line:
        int min = (int)(unitSpace.getMinY()*Math.pow(10,-ny));
        while(min%gridSeriesY != 0){
            min--;
        }
        //determines the number of lines
        boolean hasMoreTickmarks = true;
        int yTick; //current tick mark location
        int i=0; // counter number
        while(hasMoreTickmarks){
            yTick = unitSpace.toPixelY((min + i * gridSeriesY) * Math.pow(10, ny));
            i++;
            if(yTick <= (unitSpace.getTop() )){
                hasMoreTickmarks = false;
            }
        }
        horizontalLines = new Line[i+1];
        horizontalLabels = new String[i+1];
        double y;
        DecimalFormat dec = new DecimalFormat("@##");
        for(int j = 0; j <= i; j++){
            y = (min + j * gridSeriesY)*Math.pow(10,ny);
            yTick = unitSpace.toPixelY(y);
            horizontalLabels[j] = "" + dec.format(y);
            horizontalLines[j] = new Line(unitSpace.getLeft(), yTick, unitSpace.getRight(), yTick);
        }
    }


    private void computeVerticalGrid(){
        //determines the left most line:
        int min = (int)(unitSpace.getMinX()*Math.pow(10,-nx));
        while(min%gridSeriesX != 0){
            min++;
        }
        //determines the number of lines
        boolean hasMoreTickmarks = true;
        int xTick; //current tick mark location
        int i=0; // counter number
        while(hasMoreTickmarks){
            xTick = unitSpace.toPixelX((min + i*gridSeriesX)*Math.pow(10,nx));
            i++;
            if(xTick >= (unitSpace.getRight() )){
                hasMoreTickmarks = false;
            }
        }
        verticalLines = new Line[i+1];
        verticalLabels = new String[i+1];
        DecimalFormat dec = new DecimalFormat("@##");
        double x;
        for(int j = 0; j <= i; j++){
            x = (min + j*gridSeriesX)*Math.pow(10,nx);
            verticalLabels[j] = "" + dec.format(x);
            xTick = unitSpace.toPixelX((min + j*gridSeriesX)*Math.pow(10,nx));
            verticalLines[j] = new Line(xTick, unitSpace.getTop(), xTick, unitSpace.getBottom());
        }
    }

    public String[] getHorizontalLabels(){
        return this.horizontalLabels;
    }

    public String[] getVerticalLabels(){
        return this.verticalLabels;
    }

    public Line[] getHorizontalGridlines(){
        return this.horizontalLines;

    }

    public Point getOrigin(){
        Point p = new Point(unitSpace.getOrigin().x, unitSpace.getOrigin().y);
        return p;
    }


}
