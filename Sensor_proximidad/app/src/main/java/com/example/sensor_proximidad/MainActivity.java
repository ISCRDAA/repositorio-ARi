package com.example.sensor_proximidad;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    RelativeLayout rlprin;
    TextView info;
    SensorManager sm;
    Sensor proximitySensor;
    Sensor accelerometerSensor;
    MediaPlayer md;
    int currentSongIndex = 0;
    int[] songs = {R.raw.happy, R.raw.natural, R.raw.monster};

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          rlprin = (RelativeLayout) findViewById(R.id.rlprin);
          info = (TextView) findViewById(R.id.info);
          sm = (SensorManager) getSystemService(SENSOR_SERVICE);
          proximitySensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
          accelerometerSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
          sm.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
          sm.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
          md = MediaPlayer.create(this, songs[currentSongIndex]);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


        String texto = String.valueOf(sensorEvent.values[0]);
        info.setText(texto);
        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float valor = Float.parseFloat(texto);
            if(valor == 5){
                rlprin.setBackgroundColor(Color.BLACK);
                md.start();
            }else{
                rlprin.setBackgroundColor(Color.MAGENTA);
                md.pause();
            }
        }
        else if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            if (x > 15 || y > 15 || z > 15) {
                currentSongIndex++;
                if (currentSongIndex == songs.length) {
                    currentSongIndex = 0;
                }
                md.reset();
                md = MediaPlayer.create(this, songs[currentSongIndex]);
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}