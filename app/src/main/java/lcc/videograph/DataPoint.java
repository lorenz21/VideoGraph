package lcc.videograph;

/**
 * The DataPoint encapsulates the information needed for a point on
 * an xy graph, including the values of x and y, and the uncertainties
 * in x and y.
 * <p>Company: created for Leeward Community College</p>
 * @author Jennifer R. McFatridge
 * @version 1.0
 */
public class DataPoint {
    public double x;
    public double y;
    public double deltaX;
    public double deltaY;
    /**
     * Constructor for the DataPoint class. The values of x and y indicate the values
     * that are to be plotted in the Graph classes. Errors deltaX and deltaY are set to
     * zero.
     * @param x the x axis value
     * @param y the y axis value
     */
    public DataPoint(double x, double y){
        this.x = x;
        this.y = y;
        this.deltaX = 0.0;
        this.deltaY = 0.0;
    }
    /**
     * Constructor for the DataPoint class. The values of x and y indicate the values
     * that are to be plotted in the Graph classes. DeltaX and deltaY represent the
     * errors in the x value and the y value respectively
     * @param x the x axis value
     * @param y the y axis value
     * @param deltaX the error in the x axis value
     * @param deltaY the error in the y axis value
     */
    public DataPoint(double x, double y, double deltaX, double deltaY){
        this.x = x;
        this.y = y;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
}