package com.example.bt_webservice;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {


    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(Main3Activity.this,
                android.R.layout.simple_list_item_1
                , arrayList);

        listView.setAdapter(arrayAdapter);

        ParseJsonTivi parseJsonTivi = new ParseJsonTivi();
        parseJsonTivi.execute("http://172.24.45.110/api/danhsachtivi.json");

    }

    class ParseJsonTivi extends AsyncTask<String,Void,ArrayList<String>>{
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            try {
                //get data from URL
                URL url = new URL(strings[0]);
                URLConnection urlConnection = url.openConnection();
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

                InputStream inputStream = httpURLConnection.getInputStream();


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();

                while (line != null){
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }


                return readArray(stringBuilder.toString());

            }catch ( Exception ex){

            }
            return  null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);

            arrayList.addAll(strings);
            arrayAdapter.notifyDataSetChanged();
        }
    }

    private ArrayList<String> readArray(String data) {
        try {

            ArrayList<String> tmp = new ArrayList<>();

            JSONArray jsonArray = new JSONArray(data);

            String tivi = "";

            for (int i =0; i<jsonArray.length();i ++   ){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                if (jsonObject.has("Ten")){
                    String ten = jsonObject.getString("Ten");
                    tivi = tivi + ten +" ";
                }
                if (jsonObject.has("DiaChi")){
                    String diachi = jsonObject.getString("DiaChi");
                    tivi = tivi + diachi + " ";
                }

                if (jsonObject.has("Soluong")){
                    int Soluong = jsonObject.getInt("Soluong");
                    DecimalFormat df = new DecimalFormat("#,###");
                    tivi = tivi + df.format(Soluong) + " ";
                }
                if (jsonObject.has("Giatri")){
                    double giatri = jsonObject.getDouble("Giatri");
                    DecimalFormat df = new DecimalFormat("#,###");
                    tivi = tivi + df.format(giatri) + " ";
                }

                tmp.add(tivi);
            }
            return tmp;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}
