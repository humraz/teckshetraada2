package com.example.humraz.teckshetraada;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Toast;

import com.skyfishjy.library.RippleBackground;

import static com.example.humraz.teckshetraada.MyTestService.NOTIFICATION_ID;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    public static int notificationFlag = 0;
    SensorManager sensorManager;
    private static final int REQUEST_CODE_PERMISSION = 2;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    String mPermission2 = Manifest.permission.ACCESS_COARSE_LOCATION;
    private NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);


        if(notificationFlag ==1)
        { final PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
            AudioManager am;
            am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
            am.setRingerMode(AudioManager.RINGER_MODE_SILENT);

Toast.makeText(this, "Operation Cancelled",Toast.LENGTH_LONG).show();
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(MainActivity.this)
                            .setContentTitle("You Cancelled!")
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText("False Alarm! Operation Cancelled."))
                            .setContentText("False Alarm! Operation Cancelled.")
                            .setSound(Uri.parse("android.resource://"
                                    + getApplicationContext().getPackageName() + "/" + R.raw.s))
                            //.setSound(R.raq)
                            .setSmallIcon(R.drawable.question);
            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
            notificationFlag=0;
        }
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission2},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }

        // Define a listener that responds to location updates
        // tv = (TextView) findViewById(R.id.a);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        scheduleAlarm();
    }
    RippleBackground rippleBackground;
    GPSTracker gps;
    public void soshelpp(View view)
    {
        rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation();
        gps = new GPSTracker(MainActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
       /* Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();*/
            // sendNotification(Double.toString((latitude)));
            SharedPreferences pref3=getSharedPreferences("number",MODE_PRIVATE);
            String nu=pref3.getString("no1","0");
            SmsManager sms = SmsManager.getDefault();
            String phoneNumber=nu;
            String lat= Double.toString(latitude);
            String lng= Double.toString(longitude);

            String message="Help Me, I Have Met With An Accident http://maps.google.com/?q="+lat+","+lng;

            ///phone sending message code

            sms.sendTextMessage(phoneNumber, null, message, null, null);
            sensorManager.unregisterListener(this);
            Toast.makeText(this,"Help is being sent to your Location!",Toast.LENGTH_LONG).show();
/*
                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                        + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();*/
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }


    }
    public void police(View view)
    {String a1="100";
        startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + a1)));
    }
    public void ambulance(View view)
    {String a1="108";
        startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + a1)));
    }
    public void fire(View view)
    {String a1="101";
        startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + a1)));
    }
    public void traffic(View view)
    {String a1="1099";
        startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + a1)));
    }
    public void child(View view)
    {String a1="1098";
        startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + a1)));
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void con(View view
    )
    {
        Intent in = new Intent(this, GetContacts.class);
        startActivity(in);
    }
    public void con2(View view
    )
    {
        Intent in = new Intent(this, newsact.class);
        startActivity(in);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float gX = x / 9.8f;
        float gY = y / 9.8f;
        float gZ = z / 9.8f;

        float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);
        //   tv.setText(Float.toString(gForce));

    }
    public void scheduleAlarm() {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                1, pIntent);

    }
}
