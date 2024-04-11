import java.awt.*;

public class Ball {

        //variable declaration

        public int xpos; //the x position
        public int ypos; //the y position
        public int dx; //the speed of the hero in the x direction
        public int dy; //the speed of the hero in the y direction
        public int width;
        public int height;
        public boolean isAlive;//a boolean to denote if the hero is alive or not
        public Rectangle rec;
        //movement booleans
        public boolean leftPressed;
        public boolean rightPressed;
        public boolean upPressed;
        public boolean downPressed;


        //constructor method
        public Ball(int pXpos, int pYpos, int pDx, int pDy, int pWidth, int pHeight) {
            xpos = pXpos;
            ypos = pYpos;
            dx = pDx;
            dy = pDy;
            width = pWidth;
            height = pHeight;
            isAlive = true;
            rec=new Rectangle(xpos,ypos,width,height);

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
