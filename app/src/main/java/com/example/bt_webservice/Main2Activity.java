package com.example.bt_webservice;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Main2Activity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter    arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(Main2Activity.this,
                android.R.layout.simple_list_item_1
                , arrayList);

        listView.setAdapter(arrayAdapter);

//        readArray();
        readArrayXML();
        
    }

    private void readArrayXML() {

        try {
            InputStream is = getAssets().open("danhsachhoadon.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("hoaDon");


            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                Element element2 = (Element) node;

                String idHoadon = element2.getAttribute("ID_HOADON");
                String danhba = element2.getAttribute("DANHBA");

                arrayList.add(idHoadon + " " +danhba);
            }

            arrayAdapter.notifyDataSetChanged();

        } catch (Exception e) {e.printStackTrace();}
    }

    private void readArray() {
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("danhsachtivi");
            byte[] text = new byte[inputStream.available()];

            inputStream.read(text);
            inputStream.close();

            String data = new String(text, "UTF-8");


//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//            StringBuilder stringBuilder = new StringBuilder();
//
//            String line = bufferedReader.readLine();
//
//            while (line != null){
//                stringBuilder.append(line);
//                line = bufferedReader.readLine();
//            }

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

                arrayList.add(tivi);
            }
            arrayAdapter.notifyDataSetChanged();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
