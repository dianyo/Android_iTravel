package com.example.dianyo.itraveler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Write extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        Bundle bundle = this.getIntent().getExtras();
        //set bundle data*******************************
        ArrayList<String> data1 = bundle.getStringArrayList("eat");
        ArrayList<String> data2 = bundle.getStringArrayList("live");
        ArrayList<String> data3 = bundle.getStringArrayList("transport");
        ArrayList<String> data4 = bundle.getStringArrayList("entertain");
        ArrayList<String> data5 = bundle.getStringArrayList("buy");
        ArrayList<String> data6 = bundle.getStringArrayList("other");
        ArrayList<Integer> cosdata1 = bundle.getIntegerArrayList("eat_cost");
        ArrayList<Integer> cosdata2 = bundle.getIntegerArrayList("live_cost");
        ArrayList<Integer> cosdata3 = bundle.getIntegerArrayList("transport_cost");
        ArrayList<Integer> cosdata4 = bundle.getIntegerArrayList("entertain_cost");
        ArrayList<Integer> cosdata5 = bundle.getIntegerArrayList("buy_cost");
        ArrayList<Integer> cosdata6 = bundle.getIntegerArrayList("other_cost");
        String username = bundle.getString("user_name");
        String tripname = bundle.getString("trip_name");
        //JSONSET array*******************************************
        JSONArray jsarray1 = new JSONArray(data1);
        JSONArray jsarray2 = new JSONArray(data2);
        JSONArray jsarray3 = new JSONArray(data3);
        JSONArray jsarray4 = new JSONArray(data4);
        JSONArray jsarray5 = new JSONArray(data5);
        JSONArray jsarray6 = new JSONArray(data6);
        JSONArray jsarrayc1 = new JSONArray(cosdata1);
        JSONArray jsarrayc2 = new JSONArray(cosdata2);
        JSONArray jsarrayc3 = new JSONArray(cosdata3);
        JSONArray jsarrayc4 = new JSONArray(cosdata4);
        JSONArray jsarrayc5 = new JSONArray(cosdata5);
        JSONArray jsarrayc6 = new JSONArray(cosdata6);
        //JSONSET object************************************************
        JSONObject jsobj = new JSONObject();
        try {
            jsobj.put("eat", jsarray1);
            jsobj.put("eat_cost", jsarrayc1);
            jsobj.put("live", jsarray2);
            jsobj.put("live_cost", jsarrayc2);
            jsobj.put("transport", jsarray3);
            jsobj.put("transport_cost", jsarrayc3);
            jsobj.put("entertain", jsarray4);
            jsobj.put("entertain_cost", jsarrayc4);
            jsobj.put("buy", jsarray5);
            jsobj.put("buy_cost", jsarrayc5);
            jsobj.put("other", jsarray6);
            jsobj.put("other_cost", jsarrayc6);
            jsobj.put("user_name" , username);
            jsobj.put("trip_name" , tripname);
        }catch (JSONException e){}
        //filename*************************************
        String fileName = getFilesDir() + "/" + username + "_" + tripname;
        //存歷史**********************************************
        String hisfile = getFilesDir() + "/hisfile";
        String[] history = readDataFromFile(hisfile);
        List<String> historyArraylist = new ArrayList<>();
        for(int i = 0 ; i < 1000 ; i++){
            if(history[i] != null)  historyArraylist.add(history[i]);
            else    break;
        }
        int flag = 0;
        for(int i = 0 ; i < historyArraylist.size() ; i++){
            if(historyArraylist.get(i).equals(fileName)) flag = 1;
        }
        if(flag == 0)    historyArraylist.add(fileName);
        String[] hisstring = historyArraylist.toArray(new String[0]);
        writeDataToFile(hisfile , hisstring);
       //write ****************************************************
        try{
            FileWriter file = new FileWriter(fileName);
            file.write(jsobj.toString());
            file.flush();
            file.close();
        }catch (IOException i){}
        finish();
    }
    private void writeDataToFile(String filename, String[] data) {
        try {
            BufferedWriter out = null;
            out = new BufferedWriter(new FileWriter(filename));
            for (int i = 0; i < data.length; i++) {
                out.write(data[i]);
                out.newLine();
                out.flush();
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String[] readDataFromFile(String filename) {
        String[] data = new String[1000];
        int i = 0;
        try {
            FileReader fread = new FileReader(filename);
            BufferedReader bread = new BufferedReader(fread);
            String line;
            do {
                line = bread.readLine();
                if (line == null) {
                    break;
                } else {
                    data[i] = line;
                }
                i = i+1;
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
