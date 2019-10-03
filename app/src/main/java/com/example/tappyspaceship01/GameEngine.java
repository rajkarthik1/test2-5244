package com.example.tappyspaceship01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GameEngine extends SurfaceView implements Runnable {

    // Android debug variables
    final static String TAG="DINO-RAINBOWS";

    // screen size
    int screenHeight;
    int screenWidth;

    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;



    // -----------------------------------
    // GAME SPECIFIC VARIABLES
    // -----------------------------------

    // ----------------------------
    // ## SPRITES

    int DinoXPosition;
    int DinoYPosition;
    Bitmap playerImage;
    Rect playerHitbox;


    Bitmap candyImage;
    int candyXPosition;
    int candyYPosition;
    Rect candyHitbox;

    Bitmap rainbowImage;
    int rainbowXPosition;
    int rainbowYPosition;
    Rect rainbowHitbox;
    // ----------------------------

    // represent the TOP LEFT CORNER OF THE GRAPHIC

    // -----------------------
    //
    // ## GAME STATS
    // ----------------------------

//
    public GameEngine(Context context, int w, int h) {
        super(context);

        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        this.screenWidth = w;
        this.screenHeight = h;

        this.printScreenInfo();

        // Starting position of the dino

        this.playerImage = BitmapFactory.decodeResource(this.getContext().getResources(),
                R.drawable.dino64);
        this.DinoXPosition = 1600;
        this.DinoYPosition = 100;

        this.playerHitbox = new Rect(1600,
                100,
                1600+playerImage.getWidth(),
                100+playerImage.getHeight()

        );

        // put initial starting postion of enemy
        this.candyImage = BitmapFactory.decodeResource(this.getContext().getResources(),
                R.drawable.candy64);


        this.candyXPosition = 100;
        this.candyYPosition = 600;
        // 1. create the hitbox
        this.candyHitbox = new Rect(100,
                600,
                100+candyImage.getWidth(),
                600+candyImage.getHeight()
        );

        this.rainbowImage = BitmapFactory.decodeResource(this.getContext().getResources(),
                R.drawable.rainbow64);

        this.rainbowXPosition = 100;
        this.rainbowYPosition = 400;

        this.rainbowHitbox = new Rect(100,
                400,
                100+rainbowImage.getWidth(),
                400+rainbowImage.getHeight()
        );
    }



    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }



    private void spawnPlayer() {
        //@TODO: Start the player at the left side of screen
    }
    private void spawnEnemyShips() {
        Random random = new Random();

        //@TODO: Place the enemies in a random location

    }

    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------
    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updatePositions();
            this.redrawSprites();
            this.setFPS();
        }
    }


    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------

    public void updatePositions() {

        // POsition of the dino
        if (this.fingerAction == "mousedown") {
            // if mousedown, then move player up
            // Make the UP movement > than down movement - this will
            // make it look like the player is moving up alot
            this.DinoYPosition = this.DinoYPosition + 100;

            //this.playerHitbox.left  = this.DinoXPosition;
            //this.playerHitbox.top = this.DinoYPosition;
          //  this.playerHitbox.right  = this.DinoXPosition + this.playerImage.getWidth();
        //    this.playerHitbox.bottom = this.DinoYPosition + this.playerImage.getHeight();
        }

        if (this.fingerAction == "mouseup") {
            // if mouseup, then move player down
            this.DinoYPosition = this.DinoYPosition - 10;

            //this.playerHitbox.left  = this.DinoXPosition;
            //this.playerHitbox.top = this.DinoYPosition;
            //this.playerHitbox.right  = this.DinoXPosition + this.playerImage.getWidth();
            //this.playerHitbox.bottom = this.DinoYPosition + this.playerImage.getHeight();
        }
    }

    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------

            // configure the drawing tools
            this.canvas.drawColor(Color.argb(255,255,255,255));
            paintbrush.setColor(Color.WHITE);


            // DRAW THE PLAYER HITBOX
            // ------------------------
            // 1. change the paintbrush settings so we can see the hitbox
            paintbrush.setColor(Color.BLUE);
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(5);



            // draw player graphic on screen
            canvas.drawBitmap(playerImage, DinoXPosition, DinoYPosition, paintbrush);
            // draw the player's hitbox
           canvas.drawRect(this.playerHitbox, paintbrush);

            // draw the enemy graphic on the screen
            canvas.drawBitmap(candyImage, candyXPosition, candyYPosition, paintbrush);
            // 2. draw the enemy's hitbox
            canvas.drawRect(this.candyHitbox, paintbrush);


            // draw enemy 2 on the screen
            // draw the enemy graphic on the screen
            canvas.drawBitmap(rainbowImage, rainbowXPosition, rainbowYPosition, paintbrush);
            // 2. draw the enemy's hitbox
            canvas.drawRect(this.rainbowHitbox, paintbrush);
            //----------------
            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    public void setFPS() {
        try {
            gameThread.sleep(120);
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------


    String fingerAction = "";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN) {

        }
        else if (userAction == MotionEvent.ACTION_UP) {

        }

        return true;
    }
}
