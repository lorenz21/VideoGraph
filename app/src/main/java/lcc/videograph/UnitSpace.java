package lcc.videograph;

import android.graphics.Rect;
import android.graphics.Point;

/**
 * A UnitSpace holds defines a 2D space, x and y, and performs
 * conversions to pixel space. The min and max values correspond
 * to the values at the edge of a pixel space (i.e. edge of drawing
 * area)
 * @author Jennifer
 *
 */
public class UnitSpace {
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;
    private Rect pixelSpace;
    private double scaleX;
    private double scaleY;
    private Point origin;

    /**
     * Constructs an empty unit space.
     */
    public UnitSpace() {
        this(0.0, 0.0, 0.0, 0.0, new Rect(0, 0, 1, 1));
    }

    /**
     * Constructs a unit space which holds the user's scale
     *
     * @param minX the value of X (in terms of user values) which corresponds with the left side of the drawing area
     * @param minY the value of Y which corresponds with the bottom of the drawing area
     * @param maxX the value of X which corresponds with the right side of the drawing area
     * @param maxY the value of Y which corresponds with the top of the drawing area
     */
    public UnitSpace(double minX, double minY, double maxX, double maxY) {
        this(minX, minY, maxX, maxY, new Rect(0, 0, 1, 1));
    }

    /**
     * Constructs a unit space which holds the user's scale
     *
     * @param minX       the value of X (in terms of user values) which corresponds with the left side of the drawing area
     * @param minY       the value of Y which corresponds with the bottom of the drawing area
     * @param maxX       the value of X which corresponds with the right side of the drawing area
     * @param maxY       the value of Y which corresponds with the top of the drawing area
     * @param pixelSpace a rectangle which represents the drawing area
     */
    public UnitSpace(double minX, double minY, double maxX, double maxY, Rect pixelSpace) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.pixelSpace = pixelSpace;
        computeScaleX();
        computeScaleY();
        computeOrigin();
    }

    /**
     * Returns the number of pixels per user unit (e.g. pixels/meter) for the X axis. Returns
     * -1 if the maxX = minX. Returns negative values if maxX < minX.
     *
     * @return the scale of the X axis of the space in pixels/user unit.
     */
    public double getScaleX() {
        return this.scaleX;
    }

    /**
     * Returns the number of pixels per user unit (e.g. pixels/meter) for the X axis. Returns
     * -1 if the maxX = minX. Returns negative values if maxX < minX.
     *
     * @return the scale of the X axis of the space in pixels/user unit.
     */
    public double getScaleY() {
        return this.scaleY;
    }

    /**
     * Returns the pixel coordinates corresponding to the location (0,0) in the user's space
     *
     * @return location of (0,0) in pixels
     */
    public Point getOrigin() {
        return this.origin;
    }

    /**
     * Sets the x value corresponding with the left edge of the drawing surface. Recomputes the scale
     * and origin to compensate for the new left edge value.
     *
     * @param minX the x value corresponding with the left edge of the drawing surface
     */
    public void setMinX(double minX) {
        this.minX = minX;
        computeScaleX();
        computeOrigin();
    }

    /**
     * Gets the x value corresponding with the left edge of the drawing surface.
     *
     * @return the x value corresponding with the left edge of the drawing surface
     */
    public double getMinX() {
        return this.minX;
    }

    /**
     * Sets the y value corresponding with the bottom edge of the drawing surface. Recomputes the scale
     * and origin to compensate for the new bottom edge value.
     *
     * @param minY the y value corresponding with the bottom edge of the drawing surface
     */
    public void setMinY(double minY) {
        this.minY = minY;
        computeScaleY();
        computeOrigin();
    }

    /**
     * Gets the y value corresponding with the bottom edge of the drawing surface.
     *
     * @return the y value corresponding with the bottom edge of the drawing surface
     */
    public double getMinY() {
        return this.minY;
    }

    /**
     * Sets the x value corresponding with the right edge of the drawing surface. Recomputes the scale
     * and origin to compensate for the new right edge value.
     *
     * @param maxX the x value corresponding with the right edge of the drawing surface
     */
    public void setMaxX(double maxX) {
        this.maxX = maxX;
        computeScaleX();
        computeOrigin();
    }

    public double getMaxX(){ return this.maxX;}


    /**
     * Sets the y value corresponding with the top edge of the drawing surface. Recomputes the scale
     * and origin to compensate for the new top edge value.
     *
     * @param maxY the y value corresponding with the top edge of the drawing surface
     */
    public void setMaxY(double maxY) {
        this.maxY = maxY;
        computeScaleY();
        computeOrigin();
    }

    public double getMaxY(){ return this.maxY;}

    /**
     * Determines the number of pixels per user unit (e.g. pixels/meter) for the X axis. Returns
     * -1 if the maxX = minX. Does not check that maxX > minX so negative results are also possible
     * in this case.
     */
    private void computeScaleX() {
        if ((maxX - minX) != 0.0 && (pixelSpace != null)) {
            scaleX = pixelSpace.width() / (maxX - minX);
        } else {
            scaleX = -1.0;
        }
    }

    /**
     * Determines the number of pixels per user unit (e.g. pixels/meter) for the Y axis. Returns
     * -1 if the maxX = minX. Does not check that maxX > minX so negative results are also possible
     * in this case.
     */
    private void computeScaleY() {
        if ((maxY - minY) != 0.0 && (pixelSpace != null)) {
            this.scaleY = pixelSpace.height() / (maxY - minY);
        } else {
            this.scaleY = -1.0;
        }
    }

    /**
     * Determines where the point (0,0) is located in pixel space.
     */
    public void computeOrigin() {
        if (origin == null) origin = new Point(0, 0);
        if (pixelSpace != null) {
            origin.x = (int) Math.round(-minX * scaleX) + pixelSpace.left;
            origin.y = (int) Math.round(maxY * scaleY) + pixelSpace.top;
        }
    }

    /**
     * Determines the corresponding pixel location of an X value
     *
     * @param userX the X value in user units (e.g. # meters)
     * @return the x coordinate of the pixel corresponding to that X value
     */
    public int toPixelX(double userX) {
        int px = origin.x + (int) Math.round(userX * scaleX);
        return px;
    }

    /**
     * Determines the corresponding pixel location of an X value
     *
     * @param userY the Y value in user units (e.g. # meters)
     * @return the y coordinate of the pixel corresponding to that y value
     */
    public int toPixelY(double userY) {
        int py = origin.y - (int) Math.round(userY * scaleY);
        return py;
    }

    public double toUserX(int pixelX){
        return (pixelX - origin.x)/scaleX;
    }

    /**
     * Determines the corresponding pixel location of an (x,y) value
     *
     * @param userX the X value in user units (e.g. # meters)
     * @param userY the Y value in user units (e.g. # meters)
     * @return the Point containing the (x,y) coordinates in pixels
     */
    public Point toPixel(double userX, double userY) {
        Point p = new Point(0, 0);
        p.x = toPixelX(userX);
        p.y = toPixelY(userY);
        return p;
    }


    /**
     * Returns the right side border of the drawing area. The actual object cannot be exposed
     * because any alterations to the object must cause a recomputation of the unit space
     * @return the pixel location of the right side
     */
    public int getRight() {
        return this.pixelSpace.right;
    }

    /**
     * Returns the bottom side border of the drawing area
     * @return the pixel location of the bottom side
     */
    public int getBottom() {
        return this.pixelSpace.bottom;
    }

    /**
     * Returns the top side border of the drawing area
     * @return the pixel location of the top side
     */
    public int getTop() {
        return this.pixelSpace.top;
    }

    /**
     * Returns the left side border of the drawing area
     * @return the pixel location of the left side
     */
    public int getLeft() {
        return this.pixelSpace.left;
    }

}
