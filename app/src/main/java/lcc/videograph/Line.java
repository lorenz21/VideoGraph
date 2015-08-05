package lcc.videograph;

/**
 * The Line class is a struct which holds the pixel locations of the starting and ending points of a line
 * Created by jenny_000 on 7/24/2015.
 */
public class Line {
    public int startX;
    public int startY;
    public int endX;
    public int endY;
    public Line(int startX, int startY, int endX, int endY){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }
}