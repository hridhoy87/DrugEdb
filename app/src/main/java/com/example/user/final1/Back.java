package com.example.user.final1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Back extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
    private String JSON_STRING;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Background","Started");
        getJSON();
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                notifyIf();
            }

            @Override
            protected String doInBackground(Void... params) {
                ReqHandler rh = new ReqHandler();
                String s = rh.sendGetRequest(Config.URL_BACK_CHECK);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void notifyIf() {
        JSONObject jsonObject = null;
        //ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String remDays = jo.getString(Config.TAG_REM);
                //Log.d("string print",remDays);
                if(Integer.parseInt(remDays)>0 && Integer.parseInt(remDays)<100){
                    Log.d("Rem",remDays);
                    createNoti();
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void createNoti() {
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Caution")
                        .setContentText("Expirity Date Nearby")
                        .setPriority(2)
                        .setColor(25);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, ViewAllDrug.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ViewAllDrug.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        int mId=1;
        mNotificationManager.notify(mId, mBuilder.build());
        mBuilder.setAutoCancel(true);
    }
}
