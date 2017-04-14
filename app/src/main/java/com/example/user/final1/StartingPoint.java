package com.example.user.final1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class StartingPoint extends AppCompatActivity implements View.OnClickListener {

    private EditText etDragName;
    private EditText etYyyy;
    private EditText etDd;
    private EditText etMm;
    private Button buttonAdd;
    private Button buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_point);
        initialize();
        startService(new Intent(this,Back.class));
    }

    private void initialize() {
        etDragName=(EditText)findViewById(R.id.etDrugName);
        etYyyy=(EditText)findViewById(R.id.etYyyy);
        etDd=(EditText)findViewById(R.id.etDd);
        etMm=(EditText)findViewById(R.id.etMm);
        
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addDrug();
        }

        if(v == buttonView){
            startActivity(new Intent(this,ViewAllDrug.class));
            //createNoti();
            }
    }

    private void addDrug() {

        final String DrugName = etDragName.getText().toString().trim();
        final String Exp = etYyyy.getText().toString().trim()+"-"+
                etMm.getText().toString().trim()+"-"+
                etDd.getText().toString().trim();

        class AddDrug extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(StartingPoint.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String DrugName) {
                super.onPostExecute(DrugName);
                loading.dismiss();
                etDragName.setText("");
                etYyyy.setText("");
                etDd.setText("");
                etMm.setText("");
                Toast.makeText(StartingPoint.this,etDragName.getText().toString().trim()+
                        " Added Successfully",Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_DRUG_NAME,DrugName);
                params.put(Config.KEY_EXP,Exp);

                ReqHandler rh = new ReqHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }

        AddDrug ae = new AddDrug();
        ae.execute();
    }

    /*public void createNoti() {
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
    }*/
}
