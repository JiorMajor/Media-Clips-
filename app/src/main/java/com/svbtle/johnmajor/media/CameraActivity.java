package com.svbtle.johnmajor.media;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends Activity implements View.OnClickListener {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    Uri imageUri;
    boolean useextra=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        capturePhoto();
    }

    public void capturePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
        intent.putExtra("android.intent.extras.FLASH_MODE_ON", 1);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);
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

    @Override
    public void onClick(View v) {
        if(v.equals(R.id.action_camera)){
            useextra=false;
            capturePhoto();
        }
        else if(v.equals(R.id.action_featurecam)){
            //do this
            useextra=true;
            capturePhoto(v);
        }
        else {
            return;
        }
    }


    public void capturePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (useextra) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                    "IMG_" + timeStamp + ".jpg"));
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
            intent.putExtra("return-data", true);
        }
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = null;
                try {
                    if (intent == null) {
                        bitmap = BitmapFactory.decodeStream(
                                new FileInputStream(getRealPathFromURI(imageUri)));
                        if (bitmap.getHeight() > 2048 && bitmap.getWidth() > 2048) {
                            double ratio = 1024.0/bitmap.getHeight();
                            Bitmap b = Bitmap.createScaledBitmap(bitmap,
                                    (int) (bitmap.getWidth()*ratio),
                                    (int) (bitmap.getHeight()*ratio), false);
                            bitmap = b;
                        }
                    } else
                        bitmap = (Bitmap) intent.getExtras().get("data");
                    ImageView view = (ImageView) findViewById(R.id.imageView);
                    view.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Log.e("Bitmap", e.toString());
                }
            }
            else if (resultCode == RESULT_CANCELED) {
                // Image capture cancelled
                finish();
            }
            else {
                // Image capture failed, advise user
                finish();
            }
        }
    }

    private String getRealPathFromURI(Uri uri) {
        String filePath;
        if (uri != null && "content".equals(uri.getScheme())) {
            Cursor cursor = this.getContentResolver().
                    query(uri, new String[] {
                            android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
            cursor.moveToFirst();
            filePath = cursor.getString(0);
            cursor.close();
        } else {
            filePath = uri.getPath();
        }
        return(filePath);
    }
}
