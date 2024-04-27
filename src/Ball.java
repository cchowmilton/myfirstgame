import java.awt.*;

public class Ball {

        //variable declaration

    public int xpos; // The x position
    public int ypos; // The y position
    public int dx; // The speed of the ball in the x direction
    public int dy; // The speed of the ball in the y direction
    public int width;
    public int height;
    public boolean isAlive;
    public Rectangle rec;
    private int initialXpos;
    private int initialYpos;


        //constructor method
        public Ball(int pXpos, int pYpos, int pDx, int pDy, int pWidth, int pHeight) {
            // Initialize the variables
            xpos = pXpos;
            ypos = pYpos;
            dx = pDx;
            dy = pDy;
            width = pWidth;
            height = pHeight;
            isAlive = true;
            rec = new Rectangle(xpos, ypos, width, height);
            // Set initial positions
            initialXpos = pXpos;
            initialYpos = pYpos;
        }

    // Reset method
    public void reset() {
        // Reset the ball's position to its initial position
        xpos = initialXpos;
        ypos = initialYpos;
        // Reset any other properties of the ball as needed
    }


    public void printInfo() {
            System.out.println("X Position: " + xpos);
            System.out.println("Y Position: " + ypos);
            //OR
            System.out.println("(x,y): (" + xpos + ", " + ypos + ")");
            System.out.println("x speed: " + dx);
            System.out.println("y speed: " + dy);
            System.out.println("width: " + width);
            System.out.println("height: " + height);
            System.out.println("isAlive: " + isAlive);
        }

        public void move() {

            xpos = xpos + dx;
            ypos = ypos + dy;
            rec = new Rectangle(xpos, ypos,width,height);
        }

        public void wrappingMove() {
            if (xpos > 1000){
                xpos = 0;
            }
            if (xpos < 0){
                xpos=1000;
            }
            if (ypos > 700){
                ypos = 0;
            }
            if (ypos < 0){
                ypos=1000;
            }
            xpos = xpos + dx;
            ypos = ypos +dy;
            rec=new Rectangle(xpos,ypos,width,height);
        }

        public void bouncingMove() {
            if (xpos>(1000-width)){
                dx = -dx;
            }
            if (xpos < 0){
                dx = -dx;
            }
            
            if (ypos < 0) {
                dy = -dy;
            }
            if (ypos>700){
                System.out.println("game over");
            }
            xpos = xpos + dx;
            ypos = ypos +dy;
            rec=new Rectangle (xpos,ypos,width,height );

        }

}
