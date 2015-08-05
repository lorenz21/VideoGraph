package lcc.videograph;

/**
 * The CurveFit class determines the linear, quadratic, or exponential fit of
 * a series of data. It assumes that there is no error in x (the independent variable)
 * It assumes there is a constant uncertainty in y. The uncertainty is computed
 * by propagation of uncertainties from the standard deviation from the fit curve.
 * Data series that contain 2 points or less will result in infinite error
 * as statistics cannot determine the uncertainty in such a small data set
 * Created by Jennifer R. McFatridge on 7/28/2015.
 */
public class CurveFit {

    public static final int LINEAR = 0;
    public static final int QUAD = 1;
    public static final int EXP = 2;
    private DataSeries series;
    private int curvetype;
    private double[] param;

    /**
     * Creates a CurveFit. By default, tries to fit a straight line to the data.
     * @param series the data which is to be fit
     */
    public CurveFit(DataSeries series){
        this(series, 0);
    }

    /**
     * Creates a CurveFit.
     * @param series the data which is to be fit
     * @param curvetype describes the desired fit. The options are (1: linear, 2: quadratic, 3: exponential)
     */
    public CurveFit(DataSeries series, int curvetype){
        this.series = series;
        this.curvetype = curvetype;
    }

    /**
     * Sets the desired fit. The options are (1: linear, 2: quadratic, 3: exponential)
     * @param curvetype
     */
    public void setType(int curvetype){
        this.curvetype = curvetype;
    }

    /**
     * Gets the desired fit.
     * @return an integer describing the fit. The options are (1: linear, 2: quadratic, 3: exponential)
     */
    public int getType(){
        return this.curvetype;
    }

    /**
     * Gets the parameters of the fit that was performed on the data. The options are:
     * 1. Linear: y(x) = A + Bx the returned parameters are (A,B)
     * 2. Quadratic: y(x) = A + Bx + Cx^2 the returned parameters are (A,B,C)
     * 3. Exponential: y(x) = Aexp(Bx) the returned parameters are (A,B)
     * @return the parameters for the specified fit
     */
    public double[] getParameters(){
        if(param == null){
            switch(curvetype){
                case CurveFit.QUAD:
                    param = getQuadParameters();
                    break;
                case CurveFit.EXP:
                    param = getExpParameters();
                    break;
                default:
                    param = getLinearParameters();
                    break;
            }
        }
        return param;
    }
    /**
     * Gets the parameters of the linear fit that was performed on the data.
     * 1. Linear: y(x) = A + Bx the returned parameters are (A,B)
     * @return the parameters for a linear fit
     */
    private double[] getLinearParameters(){
        double[] param = new double[2];
        double sumX = getSumX(1);
        double sumY = getSumY();
        double sumXX = getSumX(2);
        double sumXY = getSumXY();
        int N = series.getNumPoints();
        double denom = (N*sumXX - sumX*sumX);
        param[0] = (sumXX*sumY-sumX*sumXY)/denom;
        param[1] = (N*sumXY - sumX*sumY)/denom;
        return param;
    }
    /**
     * Gets the parameters of the quadratic fit that was performed on the data.
     * 2. Quadratic: y(x) = A + Bx + Cx^2 the returned parameters are (A,B,C)
     * @return the parameters for a quadratic fit
     */
    private double[] getQuadParameters(){
        double [] param = new double[3];
        double sumX = getSumX(1);
        double sumXX = getSumX(2);
        double sumXXX = getSumX(3);
        double sumXXXX = getSumX(4);
        double sumY = getSumY();
        double sumXY = getSumXY();
        double sumXXY = getSumXXY();
        int N = series.getNumPoints();
        double detM = N*sumXX*sumXXXX-N*sumXXX*sumXXX + 2*sumX*sumXX*sumXXX - sumX*sumX*sumXXXX - sumXX*sumXX*sumXX;
        param[0] = sumY/detM*(sumXX*sumXXXX-sumXXX*sumXXX)+sumXY/detM*(sumXX*sumXXX-sumX*sumXXXX)+sumXXY/detM*(sumX*sumXXX-sumXX*sumXX);
        param[1] = sumY/detM*(sumXXX*sumXX-sumX*sumXXXX)+sumXY/detM*(N*sumXXXX-sumXX*sumXX)+sumXXY/detM*(sumXX*sumX-N*sumXXX);
        param[2] = sumY/detM*(sumX*sumXXX-sumXX*sumXX)+sumXY/detM*(sumX*sumXX-N*sumXXX)+sumXXY/detM*(N*sumXX-sumX*sumX);
        return param;
    }


    private double[] getExpParameters(){
        DataSeries currentData = this.series;
        double[] xVals = new double[series.getNumPoints()];
        double[] yVals = new double[series.getNumPoints()];
        for(int i = 0; i<series.getNumPoints(); i++){
            xVals[i] = series.getPointAt(i).x;
            yVals[i] = Math.log(series.getPointAt(i).y);
        }
        DataSeries tempData = new DataSeries(xVals, yVals, series.getNumPoints());
        series = tempData;
        double[] param = getLinearParameters();
        param[0] = Math.exp(param[0]);
        series = currentData;
        return param;

    }



    public double[] getParamUncertainty(){
        double[] uncertainty;
        switch(curvetype){
            case CurveFit.QUAD:
                uncertainty = getQuadUncertainty();
                break;
            case CurveFit.EXP:
                uncertainty = getExpUncertainty();
                break;
            default:
                uncertainty = getLinearUncertainty();
                break;
        }
        return uncertainty;
    }

    public double[] getQuadUncertainty(){
        double[] quadUnc = new double[3];
        double sigmaY = getStandardDevY();
        double sumX = getSumX(1);
        double sumXX = getSumX(2);
        double sumXXX = getSumX(3);
        double sumXXXX = getSumX(4);
        int N = series.getNumPoints();
        double detM = N*sumXX*sumXXXX-N*sumXXX*sumXXX+2*sumX*sumXX*sumXXX-sumX*sumX*sumXXXX-sumXX*sumXX*sumXX;
        double q = sumXX*sumXXXX - sumXXX;
        double q2 = sumXX*sumXXX-sumX*sumXXXX;
        double q3 = sumX*sumXXX-sumXX*sumXX;
        double q4 = sumXX*sumXXX - sumX*sumXXXX;
        double q5 = N*sumXXXX-sumXX*sumXX;
        double q6 = sumXX*sumX - N*sumXXX;
        double q7 = sumX*sumXXX - N*sumXXX;
        double q8 = sumX*sumXX - N*sumXXX;
        double q9 = N*sumXX - sumXX*sumXX;
        System.out.println("sigmaY is " + sigmaY);
        System.out.println("detM is " + detM);
        quadUnc[0] = sigmaY/detM*Math.sqrt(N*q*q + q2*q2*sumXX+ q3*q3*sumXXXX+2*q*q2*sumX +2*q*q3*sumXX+2*q2*q3*sumXXX);
        quadUnc[1] = sigmaY/detM*Math.sqrt(N*q4*q4 + q5*q5*sumXX+ q6*q6*sumXXXX+2*q4*q5*sumX +2*q4*q6*sumXX+2*q5*q6*sumXXX);
        quadUnc[2] = sigmaY/detM*Math.sqrt(N*q7*q7 + q8*q8*sumXX+ q9*q9*sumXXXX+2*q7*q8*sumX +2*q7*q9*sumXX+2*q8*q9*sumXXX);
        return quadUnc;
    }

    //todo: propagate error to lnA.
    public double[] getExpUncertainty(){
        double[] linUnc = new double[2];
        double sigmaY = getStandardDevY();
        double sumXX = getSumX(2);
        double sumX = getSumX(1);
        int N = series.getNumPoints();
        linUnc[0] = sigmaY*Math.sqrt(sumXX/(N*sumXX - sumX*sumX));
        linUnc[1] = sigmaY*Math.sqrt(N/(N*sumXX - sumX*sumX));
        return linUnc;

    }

    public double[] getLinearUncertainty(){
        double[] linUnc = new double[2];
        double sigmaY = getStandardDevY();
        double sumXX = getSumX(2);
        double sumX = getSumX(1);
        int N = series.getNumPoints();
        linUnc[0] = sigmaY*Math.sqrt(sumXX/(N*sumXX - sumX*sumX));
        linUnc[1] = sigmaY*Math.sqrt(N/(N*sumXX - sumX*sumX));
        return linUnc;
    }

    /**
     * Returns the sum of x^n for this data series.
     * @returns the sum
     */
    private double getSumX(int n){
        double sumXn = 0.0;
        for(int i = 0; i<series.getNumPoints(); i++){
            double xn = 1.0;
            for(int j = 0; j< n; j++){
                xn = xn*series.getPointAt(i).x;
            }
            sumXn = sumXn + xn;
        }
        return sumXn;
    }



    private double getSumXXY(){
        double sumXXY = 0.0;
        for(int i = 0; i< series.getNumPoints(); i++){
            sumXXY = sumXXY + series.getPointAt(i).x*series.getPointAt(i).x*series.getPointAt(i).y;
        }
        return sumXXY;

    }

    private double getSumY(){
        double sumY = 0.0;
        for(int i = 0; i< series.getNumPoints(); i++){
            sumY = sumY + series.getPointAt(i).y;
        }
        return sumY;
    }

    private double getSumXY(){
        double sumXY = 0.0;
        for(int i = 0; i< series.getNumPoints(); i++){
            sumXY = sumXY + series.getPointAt(i).x*series.getPointAt(i).y;
        }
        return sumXY;
    }

    public double getStandardDevY(){
        double stdev;
        switch(curvetype){
            case CurveFit.QUAD:
                stdev = getQuadStDev();
                break;
            case CurveFit.EXP:
                stdev = getExpStDev();
                break;

            default:
                stdev = getLinearStDev();
                break;
        }
        return stdev;
    }


    private double getQuadStDev(){
        double stDev = 0.0;
        double A = getParameters()[0];
        double B = getParameters()[1];
        double C = getParameters()[2];
        int N = series.getNumPoints();
        double sumSqrDiff = 0.0;
        for(int i=0; i<series.getNumPoints(); i++){
            sumSqrDiff = sumSqrDiff + (series.getPointAt(i).y - A - B*series.getPointAt(i).x- C*series.getPointAt(i).x*series.getPointAt(i).x)*(series.getPointAt(i).y - A - B*series.getPointAt(i).x- C*series.getPointAt(i).x*series.getPointAt(i).x);
        }
        stDev = Math.sqrt(1/(N-2.0)*sumSqrDiff);
        return stDev;

    }

    private double getExpStDev(){
        double stDev = 0.0;
        return stDev;
    }



    private double getLinearStDev(){
        double stDev = 0.0;
        double A = getParameters()[0];
        double B = getParameters()[1];
        int N = series.getNumPoints();
        double sumSqrDiff = 0.0;
        for(int i=0; i<series.getNumPoints(); i++){
            sumSqrDiff = sumSqrDiff + (series.getPointAt(i).y - A - B*series.getPointAt(i).x)*(series.getPointAt(i).y - A - B*series.getPointAt(i).x);
        }
        stDev = Math.sqrt(1/(N-2.0)*sumSqrDiff);
        return stDev;
    }

}
