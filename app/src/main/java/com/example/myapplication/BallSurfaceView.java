package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BallSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private static final int BALL_RADIUS = 50;

    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private float ballX, ballY;  // Position de la balle
    private float accelerationX, accelerationY;  // Valeurs de l'accéléromètre

    public BallSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        paint = new Paint();
        paint.setColor(Color.RED);

        ballX = getWidth() / 2;
        ballY = getHeight() / 2;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Commencez à mettre à jour la position de la balle lorsque la surface est créée
        // Utilisez un thread pour la mise à jour continue
        new BallUpdateThread().start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Mise à jour de la position initiale de la balle lorsqu'il y a un changement de taille de la surface
        ballX = width / 2;
        ballY = height / 2;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Arrêtez le thread lorsque la surface est détruite
    }

    private class BallUpdateThread extends Thread {

        @Override
        public void run() {
            while (true) {
                update();  // Met à jour la position de la balle
                draw();   // Dessine la balle sur la surface
            }
        }

        private void update() {
            // Mettez à jour la position de la balle en fonction de l'accélération
            ballX -= accelerationX * 5.0;
            ballY += accelerationY * 5.0;

            // Assurez-vous que la balle reste à l'intérieur de la surface
            if (ballX < BALL_RADIUS) {
                ballX = BALL_RADIUS;
            } else if (ballX > getWidth() - BALL_RADIUS) {
                ballX = getWidth() - BALL_RADIUS;
            }

            if (ballY < BALL_RADIUS) {
                ballY = BALL_RADIUS;
            } else if (ballY > getHeight() - BALL_RADIUS) {
                ballY = getHeight() - BALL_RADIUS;
            }
        }

        private void draw() {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.WHITE);  // Efface l'écran avec une couleur blanche
                canvas.drawCircle(ballX, ballY, BALL_RADIUS, paint);  // Dessine la balle
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void setAcceleration(float x, float y) {
        // Met à jour les valeurs de l'accéléromètre
        accelerationX = x;
        accelerationY = y;
    }
}