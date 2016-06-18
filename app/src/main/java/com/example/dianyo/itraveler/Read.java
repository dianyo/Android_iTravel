package com.example.dianyo.itraveler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Read extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        //set target*******************************
        String title;
        Bundle bundle = this.getIntent().getExtras();
        title = bundle.getString("title");
        //set user_name and trip_name
        String[] out1 = title.split(":");
        String[] out2 = out1[1].split(" ");
        String[] out3 = out1[2].split(" ");
        String username = out2[1];
        String tripname = out3[2];
        String fileName = getFilesDir() + "/" + username + "_" + tripname;
        //read ********************************************************************
        JSONObject reader = new JSONObject();
        try{
            FileReader file = new FileReader(fileName);
            BufferedReader br = new BufferedReader(file);
            while(br.ready()){
                String obstring = br.readLine();
                try{
                   reader = new JSONObject(obstring);
                }catch (JSONException e){}
            }
            file.close();
        }catch (IOException i){}
        //test **************************************************************************
        //Toast.makeText(Read.this , reader.toString() , Toast.LENGTH_LONG).show();
        //json array to arraylist ***************************************************************
        ArrayList<String> tmp1 = new ArrayList<>();
        ArrayList<String> tmp2 = new ArrayList<>();
        ArrayList<String> tmp3 = new ArrayList<>();
        ArrayList<String> tmp4 = new ArrayList<>();
        ArrayList<String> tmp5= new ArrayList<>();
        ArrayList<String> tmp6 = new ArrayList<>();
        ArrayList<Integer> ctmp1 = new ArrayList<>();
        ArrayList<Integer> ctmp2 = new ArrayList<>();
        ArrayList<Integer> ctmp3 = new ArrayList<>();
        ArrayList<Integer> ctmp4 = new ArrayList<>();
        ArrayList<Integer> ctmp5 = new ArrayList<>();
        ArrayList<Integer> ctmp6 = new ArrayList<>();
        try{
            TJA(tmp1 , reader.getJSONArray("eat"));
            TJA(tmp2 , reader.getJSONArray("live"));
            TJA(tmp3 , reader.getJSONArray("transport"));
            TJA(tmp4 , reader.getJSONArray("entertain"));
            TJA(tmp5 , reader.getJSONArray("buy"));
            TJA(tmp6 , reader.getJSONArray("other"));
            TJAI(ctmp1 , reader.getJSONArray("eat_cost"));
            TJAI(ctmp2 , reader.getJSONArray("live_cost"));
            TJAI(ctmp3 , reader.getJSONArray("transport_cost"));
            TJAI(ctmp4 , reader.getJSONArray("entertain_cost"));
            TJAI(ctmp5 , reader.getJSONArray("buy_cost"));
            TJAI(ctmp6 , reader.getJSONArray("other_cost"));
        }catch (JSONException e){}
        //set bundle ***********************************************************************
        Bundle detail = new Bundle();
        detail.putIntegerArrayList("eat_cost",ctmp1);
        detail.putIntegerArrayList("live_cost", ctmp2);
        detail.putIntegerArrayList("transport_cost", ctmp3);
        detail.putIntegerArrayList("entertain_cost", ctmp4);
        detail.putIntegerArrayList("buy_cost", ctmp5);
        detail.putIntegerArrayList("other_cost", ctmp6);
        detail.putStringArrayList("eat", tmp1);
        detail.putStringArrayList("live", tmp2);
        detail.putStringArrayList("transport", tmp3);
        detail.putStringArrayList("entertain", tmp4);
        detail.putStringArrayList("buy", tmp5);
        detail.putStringArrayList("other", tmp6);
        detail.putString("user_name", username);
        detail.putString("trip_name", tripname);
        Intent intent = new Intent();
        intent.setClass(Read.this , StartActivity.class);
        intent.putExtras(detail);
        startActivity(intent);
        finish();

        //debug message*********************************
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
    private  Integer[] destransform(String[] stringarray){
        Integer[] result = new Integer[stringarray.length];
        String[] cs = new String[stringarray.length];
        for(int i = 0 ; i < stringarray.length ; i++){
            String[] tmp = new String[10];
            if(stringarray[i] != null){
                tmp = stringarray[i].split("\n") ;
                cs[i] = tmp[0];
            }
            else break;
        }
        for(int i = 0 ; i < stringarray.length ; i++){
            if(stringarray[i] != null)    result[i] = Integer.parseInt(cs[i]);
            else    break;
        }
        return  result;
    }
    private ArrayList<String> stringarraytolist(String[] stringarray){
        ArrayList<String> stringlist = new ArrayList<String>();
        for(int i = 0 ; i < stringarray.length ; i++){
            if(stringarray[i] != null)  stringlist.add(stringarray[i]);
            else    break;
        }
        return  stringlist;
    }
    private  ArrayList<Integer> intarraytolist(Integer[] intarray){
        ArrayList<Integer> intlist = new ArrayList<Integer>();
        for(int i = 0 ; i < intarray.length ; i++){
            if(intarray[i] != null)intlist.add(intarray[i]);
            else    break;
        }
        return  intlist;
    }
    private void TJA(ArrayList<String> tmp, JSONArray jarray){
        for(int i = 0 ; i < 1000; i++){
            try{
                if(jarray.get(i) == null) break;
                tmp.add(jarray.get(i).toString());
            }catch (JSONException e){}
        }
    }
    private void TJAI(ArrayList<Integer> tmp , JSONArray jarray){
        for(int i = 0 ; i < 1000; i++){
            try{
                if(jarray.get(i) == null) break;
                tmp.add((Integer) jarray.get(i));
            }catch (JSONException e){}
        }
    }
}