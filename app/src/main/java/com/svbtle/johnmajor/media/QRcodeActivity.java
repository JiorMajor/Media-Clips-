package com.svbtle.johnmajor.media;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class QRcodeActivity extends Activity {

    private static final int CAPTURE_QRCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        captureQRCode();

    }

    public void captureQRCode() {
        Intent intent = new Intent("la.droid.qr.scan");
        intent.putExtra("la.droid.qr.complete", true);
        try {
            startActivityForResult(intent, CAPTURE_QRCODE);
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=la.droid.qr.priva")));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_QRCODE) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra("la.droid.qr.result")) {
                    String res = data.getExtras().getString("la.droid.qr.result");
                    Toast.makeText(this, res, Toast.LENGTH_LONG).show();
                    finish();
                }
            } else if (resultCode == RESULT_CANCELED) {
                // Capture cancelled
                Toast.makeText(QRcodeActivity.this, "Capture cancelled.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // Capture failed
                Toast.makeText(QRcodeActivity.this, "Capture failed.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qrcode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
