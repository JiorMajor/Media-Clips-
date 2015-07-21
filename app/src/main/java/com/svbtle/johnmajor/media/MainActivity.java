package com.svbtle.johnmajor.media;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    private static final int CAPTURE_QRCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.action_camera:
                Intent camintet = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(camintet);
                return true;
            case R.id.action_qrcode:
                Intent qrintent = new Intent(MainActivity.this, QRcodeActivity.class);
                startActivity(qrintent);
                return true;
            case R.id.action_featurecam:
                Intent fcamintent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(fcamintent);
                return true;
            case R.id.action_audio:
                Intent audiointent = new Intent(MainActivity.this, AudioActivity.class);
                startActivity(audiointent);
                return true;
            case R.id.action_video:
                Intent videointent = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(videointent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
