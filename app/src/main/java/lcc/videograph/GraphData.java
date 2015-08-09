package lcc.videograph;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve on 8/6/15.
 */
public class GraphData{
//data fields
        List<Double> time = new ArrayList<Double>();
        List<Double> xTap = new ArrayList<Double>();
        List<Double> yTap = new ArrayList<Double>();
        int x1;
        int x2;
        int y1;
        int y2;
        double scale;

//constructor
        /*
public GraphData(List<Double> time, List<Double>xTap, List<Double>yTap,
        int x1, int x2, int y1, int y2, double scale) {
        this.time = time;
        this.xTap = xTap;
        this.yTap = yTap;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.scale = scale;
}*/
public GraphData(int x1, int x2, int y1, int y2){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;

}

//set method for time
public void setTime(List<Double> time){
        this.time = time;
        }

//get method for time
public List<Double> getTime() {
        return time;
        }

//set method for xTap
public void setXtap(List<Double> xTap){
        this.xTap = xTap;
        }

//get method forxTap
public List<Double> getXtap() {
        return xTap;
        }

//set method for yTap
public void setYtap(List<Double> yTap){
        this.yTap = yTap;
        }

//get method for yTap
public List<Double> getYtap() {
        return yTap;
        }

//set method for x1
public void setX1(int x1){
        this.x1 = x1;
        }

//get method for time
public int getX1() {
        return x1;
        }

//set method for x2
public void setX2(int x2) {

        this.x2 = x2;
        }

//
public int getX2() {
        return x2;
        }

//
public void setY1(int y1) {
        this.y1 = y1;
        }

//
public int getY1() {
        return y1;
        }

//
public void setY2(int y2) {
        this.y2 = y2;
        }

//
public int getY2() {
        return y2;
        }

//
public void setScale(double scale) {
        this.scale = scale;
        }

//
public double getScale() {
        return scale;
        }

}

/*
    	**option 1**
    	GraphData example = new GraphData(null, null, null, x1, x2, y1, y2);

    	**option 2**
    	GraphData example;
    	example.setX1(x1);
    	example.setX2(x2);

    	etc...

    	**after values have been set**

    	example.getX1();

    	**if you have a variable you wanna use it for**

    	int variableName = example.getX1();
    */