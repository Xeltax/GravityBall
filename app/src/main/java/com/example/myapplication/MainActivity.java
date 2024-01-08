package com.example.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.BallSurfaceView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private BallSurfaceView ballSurfaceView;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Mettez l'application en mode plein écran pour une meilleure expérience
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ballSurfaceView = findViewById(R.id.ballSurfaceView);

        // Initialisez le gestionnaire de capteurs
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Obtenez le capteur de l'accéléromètre
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometerSensor == null) {
            // Handle the case where the accelerometer sensor is not available on the device
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Enregistrez l'écouteur de capteur lorsque l'activité est en premier plan
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Désenregistrez l'écouteur de capteur pour économiser les ressources
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Mettez à jour les valeurs de l'accéléromètre et transmettez-les à la vue de la balle
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];

            // Mise à jour de la vue de la balle avec les nouvelles valeurs de l'accéléromètre
            ballSurfaceView.setAcceleration(x, y);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Gérez les changements de précision du capteur si nécessaire
    }
}