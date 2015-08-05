package lcc.videograph;

/**
 * The DataSeries class is a set of data points which can be plotted in a Graph.
 * The DataSeries class is intended for a small number of points. This class
 * determines the minimum and maximum values of the data points for use in the
 * autoscale methods of the Graph classes.
 * <p>Company: created for Leeward Community College</p>
 * @author Jennifer R. McFatridge
 * @version 1.0
 */
public class DataSeries {
    private DataPoint [] points;
    private int numPoints;
    private double minimumX;
    private double minimumY;
    private double maximumX;
    private double maximumY;
    /**
     * Default constructor for the DataSeries class. Constructs an empty array of
     * DataPoints. The beginning size of the array is set to 10.
     */
    public DataSeries(){
        points = new DataPoint[10];
        numPoints = 0;
    }
    /**
     * Constructs a DataSeries with a given set of DataPoints in the series.
     * @param points the array of DataPoints which is initially in the series
     * @param numPoints the number of points added
     */
    public DataSeries(DataPoint [] points, int numPoints){
        this.points = points;
        this.numPoints = numPoints;
        if(numPoints != 0){
            this.minimumX = points[0].x;
            this.minimumY = points[0].y;
            this.maximumX = points[0].x;
            this.maximumY = points[0].y;
            for(int i = 0; i < numPoints; i++){
                if(points[i].x < minimumX) minimumX = points[i].x;
                if(points[i].y < minimumY) minimumY = points[i].y;
                if(points[i].x > maximumX) maximumX = points[i].x;
                if(points[i].y > maximumY) maximumY = points[i].y;
            }
        }
    }
    /**
     * Constructs a DataSeries with a given set of x values and y values.
     * @param xPoints the array of x values added
     * @param yPoints the array of y values added
     * @param numPoints the number of points added
     */
    public DataSeries(double [] xPoints, double [] yPoints, int numPoints){
        points = new DataPoint[numPoints];
        this.numPoints = numPoints;
        if(numPoints != 0){
            for(int i = 0; i<numPoints; i++){
                points[i] = new DataPoint(xPoints[i], yPoints[i]);
                if(i == 0){
                    this.minimumX = points[0].x;
                    this.minimumY = points[0].y;
                    this.maximumX = points[0].x;
                    this.maximumY = points[0].y;
                }
                if(points[i].x < minimumX) minimumX = points[i].x;
                if(points[i].y < minimumY) minimumY = points[i].y;
                if(points[i].x > maximumX) maximumX = points[i].x;
                if(points[i].y > maximumY) maximumY = points[i].y;
            }
        }
    }
    /**
     * Adds a DataPoint to the series by specifying the x and y values. The array
     * grows if necessary to accomidate the new point. The array grows linearly.
     * @param xPoint the x value of the data point to be added
     * @param yPoint the y value of the data point to be added
     */
    public void addPoint(double xPoint, double yPoint){
        if(points.length < numPoints + 1){
//allocate more memory; add only 10 more spaces so that size increases linearly
            DataPoint [] temp = new DataPoint[points.length + 10];
            System.arraycopy(points, 0, temp, 0, points.length);
            points = temp;
        }
        DataPoint dtaPt = new DataPoint(xPoint, yPoint);
        points[numPoints] = dtaPt;
        numPoints++;
//check to see if these are the new minimum or maximum
        if(xPoint < minimumX) minimumX = xPoint;
        if(yPoint < minimumY) minimumY = yPoint;
        if(xPoint > maximumX) maximumX = xPoint;
        if(yPoint > maximumY) maximumY = yPoint;
    }
    /**
     * Adds a DataPoint to the series by specifying the x and y values and the
     * error values for each. The array grows if necessary to accomidate the
     * new point. The array grows linearly.
     * @param xPoint the x value of the data point to be added
     * @param yPoint the y value of the data point to be added
     * @param deltaX the amount of error in the x value
     * @param deltaY the amount of error in the y value
     */
    public void addPoint(double xPoint, double yPoint, double deltaX, double deltaY){
        if(points.length < numPoints + 1){
//allocate more memory; add only 10 more spaces so that size increases linearly
            DataPoint [] temp = new DataPoint[points.length + 10];
            System.arraycopy(points, 0, temp, 0, points.length);
            points = temp;
        }
        DataPoint dtaPt = new DataPoint(xPoint, yPoint, deltaX, deltaY);
        points[numPoints] = dtaPt;
        numPoints++;
//check to see if these are the new minimum or maximum
        if(xPoint < minimumX) minimumX = xPoint;
        if(yPoint < minimumY) minimumY = yPoint;
        if(xPoint > maximumX) maximumX = xPoint;
        if(yPoint > maximumY) maximumY = yPoint;
    }
    /**
     * Clears the entire set of points and sets both the minimum and the maximum to
     * zero
     */
    public void deleteAll(){
        points = new DataPoint[10];
        minimumX = 0;
        minimumY = 0;
        maximumX = 0;
        maximumY = 0;
    }
    /**
     * Deletes the last added point. Finds the new maxima or minima if necessary
     */
    public void deleteLast(){
        if(points[numPoints - 1].x == minimumX){
//search for a new minimum among the previous entries
            minimumX = points[0].x;
            for(int i = 0; i< (numPoints-1); i++){
                if(points[i].x < minimumX) minimumX = points[i].x;
            }
        }
        else if(points[numPoints - 1].x == maximumX){
            maximumX = points[0].x;
            for(int i = 0; i< (numPoints-1); i++){
                if(points[i].x > maximumX) maximumX = points[i].x;
            }
        }
        if(points[numPoints - 1].y == minimumY){
//search for a new minimum among the previous entries
            minimumY = points[0].y;
            for(int i = 0; i< (numPoints-1); i++){
                if(points[i].y < minimumY) minimumY = points[i].y;
            }
        }
        else if(points[numPoints - 1].y == maximumY){
            maximumY = points[0].y;
            for(int i = 0; i< (numPoints-1); i++){
                if(points[i].y > maximumY) maximumY = points[i].y;
            }
        }
        numPoints--;
    }
    /**
     * Deletes the point at the specified index
     * @param index the index of the point to be deleted
     */
    public void deletePoint(int index){
        if(index == numPoints - 1){
            deleteLast();
        }
        else if(index < numPoints){
            DataPoint [] temp = new DataPoint[numPoints];
            System.arraycopy(points,index + 1,temp,0, numPoints - index - 1);
            System.arraycopy(temp,0,points,index,numPoints - index -1);
            numPoints--;
            for(int i = 0; i < numPoints; i++){
                if(points[i].x < minimumX) minimumX = points[i].x;
                if(points[i].y < minimumY) minimumY = points[i].y;
                if(points[i].x > maximumX) maximumX = points[i].x;
                if(points[i].y > maximumY) maximumY = points[i].y;
            }
        }
//else the index is invalid do nothing
    }
    /**
     * Gets the minimum value of the x points in the series for calculation in the
     * autoscale methods in the Graph classes.
     * @return minimumX - the minimum value of the x points
     */
    public double getMinimumX(){
        return this.minimumX;
    }
    /**
     *Gets the maximum value of the x points in the series for calculation in the
     *autoscale methods in the Graph classes.
     * @return maximumX - the maximum value of the x points.
     */
    public double getMaximumX(){
        return this.maximumX;
    }
    /**
     * Gets the minimum value of the y points in the series for calculation in the
     * autoscale methods in the Graph classes.
     * @return minimumY - the minimum value of the y points
     */
    public double getMinimumY(){
        return this.minimumY;
    }
    /**
     * Gets the maximum value of the y points in the series for calcualtion in the
     * autoscale methods in the Graph classes
     * @return maximumY - the maximum value of the y points
     */
    public double getMaximumY(){
        return this.maximumY;
    }
    /**
     * Gets the total number of points in the series.
     * @return numPoints - the number of points contained in the current instance
     */
    public int getNumPoints(){
        return this.numPoints;
    }
    /**
     * Gets the entire DataPoint array
     * @return points - an array of DataPoints which contains all the points added to
     * the series.
     */
    public DataPoint[] getPoints(){
        return this.points;
    }
    /**
     * Sets the DataPoint array to the specified array
     * @param points the DataPoints array which replaces the current array
     * @param numPoints the number of points which is held in the added array
     */
    public void setPoints(DataPoint[] points, int numPoints){
        this.points = points;
        this.numPoints = numPoints;
    }
    /**
     * Gets the point at the specified index. This is used in the plot method of
     * the Graph classes.
     * @param index the index of the point which is to be obtained
     * @return points[index] - the specified DataPoint
     */
    public DataPoint getPointAt(int index){
        if(index <numPoints){
            return this.points[index];
        }
        else{
            return null;
        }
    }
}
