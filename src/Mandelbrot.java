import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JFrame;


/*Template for class exercise
 * This make a window and draws the Mandelbrot fractal
 * by holding the mouse and drawing a rectangle, you can zoom into the fractal
 *
 * goal for class: when you press the right mouse button, zoom back out again
 * two functions to write: save() and zoomout()
 */

@SuppressWarnings("serial")
public class Mandelbrot extends JComponent implements MouseListener
{
    //window size
    public int XSIZE=500,YSIZE=500;

    //mathematical dimensions: these are the coordinates that correspond to the edges of the window
    double xtop=-1,ytop=-1,xbottom=1,ybottom=1;
    //the window object
    JFrame win;

    //program starts here:
    public Mandelbrot()
    {
        //generate a window, include a mouse listener and a jcomponent for painting
        win=new JFrame();
        win.setTitle("Mandelbrot ("+xtop+","+ytop+") - ("+xbottom+","+ybottom+")");
        win.setSize(XSIZE,YSIZE);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.add(this);
        addMouseListener(this);
        win.setVisible(true);
    }
    public static void main(String[] args)
    {
        new Mandelbrot();
    }


    //go through each pixel in the window.  figure out its coordinate, compute its mandelbrot color, and draw it
    public void paintComponent(Graphics g)
    {
        for(int x=0; x<XSIZE; x++)
        {
            for(int y=0; y<YSIZE; y++)
            {
                //scale the coordinate from pixels to the the fractal coordinates
                //this starts at -1.0 to 1.0 but will change as we zoom in
                double xcoord = (xbottom - xtop) / (XSIZE) * x + xtop;
                double ycoord = (ybottom - ytop) / (YSIZE) * y + ytop;

                Color c;
                //this will return a value from 0 to 255 that we can use for color.
                int val=getMandelbrot(xcoord,ycoord);

                //one coloring method: make everything a shade of blue
//				c=new Color(0,0,val);

                //another coloring method: make everything a shade from blue to white except for 255 cases. make those black
                if(val<255)
                    c=new Color(val,val,255);
                else
                    c=Color.black;
                //draw a tiny 1x1 pixel rectangle of the appropriate color
                g.setColor(c);
                g.fillRect(x,y,1,1);
            }

        }
    }

    //source for this function: math.hws.edu/eck/cs124/javanotes4/source/Mandelbrot.java
    //idea: do computation repeatedly. either x and y will stay within bounds or they will fly out of bounds
    // return the number of iterations until they fly out of bounds, or 255 for if they don't
    private int getMandelbrot(double x, double y)
    {
        int count=0;
        double zx=x;
        double zy=y;
        while(count<255 && Math.abs(zx)<100 && Math.abs(zy)<100)
        {
            double new_zx=zx*zx-zy*zy+x;
            double new_zy=2*zx*zy+y;
            zy=new_zy;
            zx=new_zx;
            count++;
        }
        return count;
    }


    //keep track of what pixel we pressed
    private int pressedX,pressedY;
    public void mousePressed(MouseEvent arg0)
    {
        pressedX=arg0.getX();
        pressedY=arg0.getY();
    }

    //when we release, zooming action happens
    public void mouseReleased(MouseEvent arg0) {
        //if we pressed the left button, zoom in
        if(arg0.getButton()==MouseEvent.BUTTON1)
        {
            //save our current coordinates on the stack for later zoomout
            save();
            //get the pixel we released on
            int releasedX=arg0.getX();
            int releasedY=arg0.getY();
            //scale them to fractal coordinates
            double newxtop = pressedX * (xbottom-xtop)/XSIZE + xtop;
            double newxbottom = releasedX * (xbottom-xtop)/XSIZE + xtop;
            double newytop = pressedY * (ybottom-ytop)/YSIZE + ytop;
            double newybottom = releasedY * (ybottom-ytop)/YSIZE + ytop;
            //make them the new window boundaries
            xtop=newxtop;
            ytop=newytop;
            xbottom=newxbottom;
            ybottom=newybottom;
        }
        else
        {
            //pressed the right button?  zoom out by loading previous window boundary coordinates from the stack
            zoomout();
        }
        //call paintcomponent and update the window
        win.setTitle("Mandelbrot ("+xtop+","+ytop+") - ("+xbottom+","+ybottom+")");
        repaint();
    }

    //got to have these methods, even if we don't use them
    public void mouseClicked(MouseEvent arg0) {}
    public void mouseEntered(MouseEvent arg0) {}
    public void mouseExited(MouseEvent arg0) {}


    //TODO:
    //this is our task


    Stack stack = new Stack(); // creates an empty stack


    //called when we zoom in.  should save the top and bottom x,y coordinates before they're changed
    private void save()
    {

        Coordinate coords = new Coordinate(xtop,ytop,xbottom,ybottom);  // new Coordinate object containing given X's and Y's coords
        // Coordinate node = new Coordinate();

        stack.push(coords); // saving it pushes it into the stack in memory

    }

    //called when we want to zoom out.  should retrieve the old top and bottom x,y coordinates and restore them
    private void zoomout()
    {
        Coordinate savedCoords = new Coordinate(xtop,ytop,xbottom,ybottom);
       // Coordinate newCoords = new Coordinate(xtop,ytop,xbottom,ybottom);
               // stack.pop(newCoords);

        if (!stack.isEmpty())  // Don't pop is the stack is empty! Will get a NullPointerException
        {
            savedCoords = stack.pop();        // pop those saved coords, baby
        }


        // all current coords become the previous coords in the stack that we saved as we zoomed in
        xtop = savedCoords.getxTL();   ytop = savedCoords.getyTL();
        xbottom = savedCoords.getxBR();  ybottom = savedCoords.getyBR();


        repaint();  // reprocess calculated painting algorithm

    }



}