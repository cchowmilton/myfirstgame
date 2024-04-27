//Game Example
//Lockwood Version 2023-24
// Learning goals:
// make an object class to go with this main class
// the object class should have a printInfo()
//input picture for background
//input picture for object and paint the object on the screen at a given point
//create move method for the object, and use it
// create a wrapping move method and a bouncing move method
//create a second object class
//give objects rectangles
//start interactions/collisions

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
import jdk.jfr.internal.test.WhiteBox;

import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;

//*******************************************************************************
// Class Definition Section

public class GameLand implements Runnable, KeyListener {

    //Variable Declaration Section
    //Declare the variables used in the program
    //You can set their initial values here if you want
    //variable to hold points
    private int points = 0;
    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    //Declare the objects used in the program below
    /** STEP 0 declare object **/
    public Ball ball;
    public Plank plank;

    /** Declare an array list**/
    public ArrayList<Blocks> blocks;

    /** STEP 1 declare image for object**/
    //public Image carPic;
    public Image backgroundPic;
    public Image ballPic;
    public Image plankPic;
    public Image blocksPic;

    //declare background image

    //declare intersection booleans
    public boolean ballIsIntersectingPlank=false;
    public boolean ballIsIntersectingBlocks=false;

    //variable to track if game has started
    private boolean gameStarted = false;

    /* Step 1 Declare an array of your object type */
    public Ball[] asteroids;



    // Main method definition: PSVM
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        GameLand ex = new GameLand();//creates a new instance of the game and tells GameLand() method to run
        new Thread(ex).start();       //creates a thread & starts up the code in the run( ) method
    }

    // Constructor Method
    // This has no return type and has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.

    public GameLand() {
        setUpGraphics(); //this calls the setUpGraphics() method

        //create (construct) the objects needed for the game below
        /** STEP 2 construct object **/
        ball = new Ball(150,150,9,9,150, 150);
        plank = new Plank(450, 500, 13, 13, 160, 160);
        ball.width = 30;
        ball.height = 30;

        /** construct my array list of blocks**/
        blocks = new ArrayList<>();

        /** fill my array list of blocks going up to an actual number**/

       for(int i=0;i<15;i++) {
           int randX = (int)(Math.random()*1000);
           int randY = (int)(Math.random()*500);
           blocks.add(new Blocks(randX, randY, 2, 3, 80, 80));
       }




        /** STEP 3 add image to object**/
        ballPic= Toolkit.getDefaultToolkit().getImage("ball.png");
        ball.printInfo();
        plankPic= Toolkit.getDefaultToolkit().getImage("plank.png");
        plank.printInfo();
        backgroundPic = Toolkit.getDefaultToolkit().getImage("background.jpeg");
        blocksPic = Toolkit.getDefaultToolkit().getImage("blocks.png");

        //for each object that has a picture, load in images as well

    }// GameLand()

//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever using a while loop
        while (true) {
            if (gameStarted) {
                moveThings();
                collisions();
                render();
            } else {
                // Display the start screen if the game hasn't started
                renderStartScreen();
            }
            pause(20); // Sleep for 20 ms
        }

    }
    private void renderStartScreen() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        Image backgroundImage = Toolkit.getDefaultToolkit().getImage("background.jpeg");

        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);

        // Draw the start screen text centered on the screen
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Asteroid Football", WIDTH / 2 - 200, HEIGHT / 2);
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        g.drawString("Are you ready to play?", WIDTH / 2 - 190, HEIGHT / 2+50);
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        g.drawString("Press RETURN to play now!", WIDTH / 2 - 250, HEIGHT / 2 +100);

        // Dispose the graphics
        g.dispose();

        bufferStrategy.show();
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Game Land");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.addKeyListener(this);
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics g = bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        if (ball.ypos > 700) {
            // Display the end screen if the ball's y position is over 700
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over", WIDTH / 2 - 150, HEIGHT / 2 - 50);
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            g.drawString("You Lose!", WIDTH / 2 - 100, HEIGHT / 2 + 50);
            g.drawString("Final Score: " + points, WIDTH / 2 - 150, HEIGHT / 2 + 150);
        } else {
            // Draw the game screen if the game is still running
            g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);
            g.drawImage(ballPic, ball.xpos, ball.ypos, ball.width, ball.height, null);
            g.drawImage(plankPic, plank.xpos, plank.ypos, plank.width, plank.height, null);
            for (int i = 0; i < blocks.size(); i++) {
                if (blocks.get(i).isAlive) {
                    g.drawImage(blocksPic, blocks.get(i).xpos, blocks.get(i).ypos, blocks.get(i).width, blocks.get(i).height, null);
                }
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Points: " + points, WIDTH - 150, 30);
        }

        g.dispose();
        bufferStrategy.show();
    }


    public void moveThings() {
        //call the move() method code from your object class
        ball.bouncingMove();
        plank.move();

    }

    public void collisions(){
            if(ball.rec.intersects(plank.rec)){
                ballIsIntersectingPlank=true;
                ball.dy = -ball.dy;
            }
            if(ball.rec.intersects(plank.rec)==false){
                ballIsIntersectingPlank=false;
            }
        //check if there are less than 5 active blocks
        int activeBlocksCount = 0;
        for (Blocks block : blocks) {
            if (block.isAlive) {
                activeBlocksCount++;
            }
        }

        if (activeBlocksCount < 5) {
            // Generate additional blocks
            int blocksToAdd = 5 - activeBlocksCount;
            for (int i = 0; i < blocksToAdd; i++) {
                int randX = (int) (Math.random() * 1000);
                int randY = (int) (Math.random() * 500);
                blocks.add(new Blocks(randX, randY, 2, 3, 80, 80));
            }
        }

        for (int i=0; i< blocks.size();i=i+1) {
            if (blocks.get(i).rec.intersects(ball.rec)){
                ballIsIntersectingBlocks = true;
                blocks.get(i).isAlive = false;
                points++;

            }
        }
    }




    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char key =e.getKeyChar();
        int keyCode=e.getKeyCode();
        System.out.println("Key: "+ key + ", KeyCode: "+ keyCode);
        if(keyCode==68) {//d is 68 // right movement
            plank.rightPressed = true;
        }
        if(keyCode==65) {//a is 65 // right movement
            plank.leftPressed = true;
        }
        /*if(keyCode==87) { //w is 87
            ball.upPressed = true;
        }
        if(keyCode==83) {//s is 83
            ball.downPressed = true;
        }*/
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key =e.getKeyChar();
        int keyCode=e.getKeyCode();
        System.out.println("Key: "+ key + ", KeyCode: "+ keyCode);
        if(keyCode==68) {//d is 68 // right movement
            plank.rightPressed = true;
        }
        if(keyCode==65) {//a is 65 // right movement
            plank.leftPressed = true;
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            gameStarted = true;
        }
        /*if(keyCode==87) { //w is 87
            ball.upPressed = true;
        }
        if(keyCode==83) {//s is 83
            ball.downPressed = true;
        }*/

    }

    @Override
    public void keyReleased(KeyEvent e) {
        char key =e.getKeyChar();
        int keyCode=e.getKeyCode();
        if(keyCode==68) {//d is 68 // right movement
            plank.rightPressed = false;
        }
        if(keyCode==65) {//a is 65 // right movement
            plank.leftPressed = false;
        }/*
        if(keyCode==87) { //w is 87
            ball.upPressed = false;
        }
        if(keyCode==83) {//s is 83
            ball.downPressed = false;
        }*/
    }
}