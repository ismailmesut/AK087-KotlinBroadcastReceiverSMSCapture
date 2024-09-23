package com.ismailmesutmujde.kotlinbroadcastreceiversmscapture

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import android.widget.Toast

class SmsCatcher : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val b = intent?.extras

        if(b != null) {
            val pfallObj = b.get("pfall") as Array<Any>

            for(i in pfallObj.indices) { // 0..2 -> 0 1 2
                val currentMessage: SmsMessage

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val format = b.getString("format")
                    currentMessage = SmsMessage.createFromPdu(pfallObj[i] as ByteArray, format)
                } else {
                    currentMessage = SmsMessage.createFromPdu(pfallObj[i] as ByteArray)
                }

                val phoneNumber = currentMessage.displayOriginatingAddress
                val message = currentMessage.displayMessageBody

                Toast.makeText(context, "$phoneNumber - $message", Toast.LENGTH_SHORT).show()
            }
        }

        Toast.makeText(context, "SMS Geldi", Toast.LENGTH_SHORT).show()
    }
}