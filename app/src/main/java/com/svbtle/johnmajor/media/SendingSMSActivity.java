package com.svbtle.johnmajor.media;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendingSMSActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_sms);

        final EditText editText1 = (EditText) findViewById(R.id.editText1);

        final EditText editText2 = (EditText) findViewById(R.id.editText2);

        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS_Intent(editText1.getText().toString(), editText2.getText().toString());
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS_SDK(editText1.getText().toString(), editText2.getText().toString());
            }
        });

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS_SDK_Noti(editText1.getText().toString(), editText2.getText().toString());
            }
        });

        registerReceiver(r1,new IntentFilter(SENT));
    }


    @Override
    protected void onStop(){
        super.onStop();
        unregisterReceiver(r1);
    }

    //Send SMS via Intent
    private void sendSMS_Intent(String phoneNumber, String message)
    {
        Uri smsUri = Uri.parse("smsto:"+phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsUri);
        intent.putExtra("sms_body", message);
        startActivity(intent);

    }

    //Send SMS via SDK
    //Remember android.permission.SEND_SMS is required
    //without notification
    private void sendSMS_SDK(String phoneNumber, String message){
        android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber,null,message,null,null);
    }

    //Send SMS via Telephony SMS Manager with notification
    final String SENT = "SMS_SENT";
    BroadcastReceiver r1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(context, "SMS sent", Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    Toast.makeText(context, "Generic failure", Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    Toast.makeText(context, "No service", Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    Toast.makeText(context, "Null PDU", Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    Toast.makeText(context, "Radio off", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    private void sendSMS_SDK_Noti(String phoneNumber, String message){
        Intent sentIntent = new Intent(SENT);
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber,null,message,sentPI,null);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sending_sm, menu);
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
