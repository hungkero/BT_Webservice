package com.example.bt_webservice;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {


    TextView txt1, txt2,txt3, txt4;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1 = findViewById(R.id.textView);
        txt2 = findViewById(R.id.textView2);
        txt3 = findViewById(R.id.textView3);
        txt4 = findViewById(R.id.textView4);

        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AssetManager assetManager = getAssets();
                    InputStream inputStream = assetManager.open("cty.jason");
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder stringBuilder = new StringBuilder();

                    String line = bufferedReader.readLine();

                    while (line != null){
                        stringBuilder.append(line);
                        line = bufferedReader.readLine();
                    }

                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());

                    if (jsonObject.has("Ten")){
                        String ten = jsonObject.getString("Ten");
                        txt1.setText(ten);
                    }
                    if (jsonObject.has("DiaChi")){
                        String diachi = jsonObject.getString("DiaChi");
                        txt2.setText(diachi);
                    }

                    if (jsonObject.has("Soluong")){
                        int Soluong = jsonObject.getInt("Soluong");
                        DecimalFormat df = new DecimalFormat("#,###");

                        txt3.setText(df.format(Soluong ));
                    }
                    if (jsonObject.has("Giatri")){
                        double giatri = jsonObject.getDouble("Giatri");
                        DecimalFormat df = new DecimalFormat("#,###");
                        txt4.setText(df.format(giatri ));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
