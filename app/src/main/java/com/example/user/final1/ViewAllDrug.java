    package com.example.user.final1;

    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.os.AsyncTask;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ListAdapter;
    import android.widget.ListView;
    import android.widget.SimpleAdapter;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.ArrayList;
    import java.util.HashMap;

    public class ViewAllDrug extends AppCompatActivity implements ListView.OnItemClickListener {

    private ListView listView;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_all_drug);
            listView = (ListView) findViewById(R.id.listView);
            listView.setOnItemClickListener(this);
            getJSON();
            }


    private void showDrug(){
            JSONObject jsonObject = null;
            ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
            try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
            JSONObject jo = result.getJSONObject(i);
            String entry = jo.getString(Config.TAG_ENTRY);
            String drugName = jo.getString(Config.TAG_DRUG_NAME);
            String remDays = jo.getString(Config.TAG_REM);
            String exp = jo.getString(Config.TAG_EXP);

            HashMap<String,String> drugs = new HashMap<>();
            drugs.put(Config.TAG_ENTRY,entry);
            drugs.put(Config.TAG_DRUG_NAME,drugName);
            drugs.put(Config.KEY_REM,remDays);
            drugs.put(Config.KEY_EXP,exp);
            list.add(drugs);
            }

            } catch (JSONException e) {
            e.printStackTrace();
            }

        ListAdapter adapter = new SimpleAdapter(
            ViewAllDrug.this, list, R.layout.list_item,
            new String[]{
                    Config.TAG_ENTRY,Config.TAG_DRUG_NAME,Config.TAG_EXP,Config.TAG_REM},
                new int[]{R.id.entryListNo, R.id.drugName ,R.id.expDate, R.id.remDays}
            );
            listView.setAdapter(adapter);
            }

    private void getJSON(){
    class GetJSON extends AsyncTask<Void,Void,String> {

        ProgressDialog loading;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ViewAllDrug.this,"Fetching Data","Wait...",false,false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            JSON_STRING = s;
            showDrug();
        }

        @Override
        protected String doInBackground(Void... params) {
            ReqHandler rh = new ReqHandler();
            String s = rh.sendGetRequest(Config.URL_GET_ALL);
            return s;
        }
    }
        GetJSON gj = new GetJSON();
    gj.execute();
            }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(this, ViewDrug.class);
            HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
            String drugID = map.get(Config.TAG_ENTRY).toString();
            intent.putExtra(Config.TAG_ENTRY,drugID);
            startActivity(intent);
            }
    }