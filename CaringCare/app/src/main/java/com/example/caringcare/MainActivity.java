package com.example.caringcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView waringTextWindow;
    private SensorManager SM;
    private Sensor accelerationSensor;
    private float x = 0, y = 0, z = 0;
    private SensorEventListener accListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        waringTextWindow = findViewById(R.id.waringText);

        SM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> list = SM.getSensorList(Sensor.TYPE_LINEAR_ACCELERATION);
        if (list.size() > 0) {
            accelerationSensor = list.get(0);
        }
        checkPhonePermisson();
        startListen();

    }

    public void checkPhonePermisson() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {

            } else {
                Toast.makeText(this, "请允许拨号权限，用以拨打报警电话", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 2);
            }
        }
    }

    public void call(String phone) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        }


    }


    private void startListen() {
        accListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];
                double totalnum = Math.cbrt(x * x * x + y * y * y + z * z * z);
                if (totalnum > 10 || totalnum < -10) {
                    waringTextWindow.setText("检测到\n异常撞击！\n即将拨打110");
                    call("10086");
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerationSensor != null) {
            SM.registerListener(accListener, accelerationSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        SM.unregisterListener(accListener, accelerationSensor);
        super.onStop();
    }


}
