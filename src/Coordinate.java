public class Coordinate
{

double xTL;     // initializing our four coordinate variables
double yTL;
double xBR;
double yBR;

    public Coordinate(double xTL, double yTL, double xBR, double yBR)   // constructor, taking in the 4 coordinate variables
    {
             this.xTL = xTL;      this.yTL = yTL;         // x,y TopLeft
             this.xBR = xBR;      this.yBR = yBR;         // x,y BottomRight
    }


    // "Get" methods to be able to obtain object's coords

    public double getxTL(){
        return xTL;
    }

    public double getyTL(){
        return yTL;
    }

    public double getxBR(){
        return xBR;
    }

    public double getyBR(){
        return yBR;
    }


}
