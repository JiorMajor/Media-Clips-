package com.svbtle.johnmajor.media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by johnmajor on 7/22/15.
 */

    public class SmsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            Bundle bundle = arg1.getExtras();
            SmsMessage[] msgs = null;
            StringBuilder str = new StringBuilder();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];
                for (int i=0; i<msgs.length; i++) {
                    msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                    str.append("SMS from "); str.append(msgs[i].getOriginatingAddress());
                    str.append(" :");
                    str.append(msgs[i].getMessageBody().toString());
                    str.append('\n');
                }
                Toast.makeText(arg0, str.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

