package com.example.dianyo.itraveler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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
        /*
        Bundle bundle = new Bundle();
        String user_name = "user_test";
        String trip_name = "trip_test";
        bundle.putString("user_name", user_name);
        bundle.putString("trip_name", trip_name);
        */
        title = bundle.getString("title");
        //set user_name and trip_name
        String[] out1 = title.split(":");
        String[] out2 = out1[1].split(" ");
        String[] out3 = out1[2].split(" ");
        String user_name = out2[1];
        String tripname = out3[1];
        String fileName = getFilesDir() + "/" + user_name + "_" + tripname;
        //Toast.makeText(Read.this , fileName , Toast.LENGTH_SHORT).show();
        //init data ******************************
        String[] string1 = new String[1000];
        String[] string2 = new String[1000];
        String[] string3 = new String[1000];
        String[] string4 = new String[1000];
        String[] string5 = new String[1000];
        String[] string6 = new String[1000];
        String[] intstring1 = new String[1000];
        String[] intstring2 = new String[1000];
        String[] intstring3 = new String[1000];
        String[] intstring4 = new String[1000];
        String[] intstring5 = new String[1000];
        String[] intstring6 = new String[1000];
        //*************************************
        System.arraycopy(readDataFromFile(fileName + "_eat") , 0 , string1 , 0 , string1.length);
        System.arraycopy(readDataFromFile(fileName + "_live") , 0 , string2 , 0 , string2.length);
        System.arraycopy(readDataFromFile(fileName + "_transport") , 0 , string3 , 0 , string3.length);
        System.arraycopy(readDataFromFile(fileName + "_entertain") , 0 , string4 , 0 , string4.length);
        System.arraycopy(readDataFromFile(fileName + "_buy") , 0 , string5 , 0 , string5.length);
        System.arraycopy(readDataFromFile(fileName + "_other") , 0 , string6 , 0 , string6.length);
        System.arraycopy(readDataFromFile(fileName + "_eat_cost") , 0 , intstring1 , 0 , intstring1.length);
        System.arraycopy(readDataFromFile(fileName + "_live_cost") , 0 , intstring2 , 0 , intstring2.length);
        System.arraycopy(readDataFromFile(fileName + "_transport_cost") , 0 , intstring3 , 0 , intstring3.length);
        System.arraycopy(readDataFromFile(fileName + "_entertain_cost") , 0 , intstring4 , 0 , intstring4.length);
        System.arraycopy(readDataFromFile(fileName + "_buy_cost") , 0 , intstring5 , 0 , intstring5.length);
        System.arraycopy(readDataFromFile(fileName + "_other_cost") , 0 , intstring6 , 0 , intstring6.length);
        //detransform *******************************
        Integer[] integer1 = destransform(intstring1);
        Integer[] integer2 = destransform(intstring2);
        Integer[] integer3 = destransform(intstring3);
        Integer[] integer4 = destransform(intstring4);
        Integer[] integer5 = destransform(intstring5);
        Integer[] integer6 = destransform(intstring6);
        //init ****************************************
        ArrayList<String> tmp1 = stringarraytolist(string1);
        ArrayList<String> tmp2 = stringarraytolist(string2);
        ArrayList<String> tmp3 = stringarraytolist(string3);
        ArrayList<String> tmp4 = stringarraytolist(string4);
        ArrayList<String> tmp5 = stringarraytolist(string5);
        ArrayList<String> tmp6 = stringarraytolist(string6);
        ArrayList<Integer> ctmp1 = intarraytolist(integer1);
        ArrayList<Integer> ctmp2 = intarraytolist(integer2);
        ArrayList<Integer> ctmp3 = intarraytolist(integer3);
        ArrayList<Integer> ctmp4 = intarraytolist(integer4);
        ArrayList<Integer> ctmp5 = intarraytolist(integer5);
        ArrayList<Integer> ctmp6 = intarraytolist(integer6);
        //
        Bundle detail = new Bundle();
        detail.putIntegerArrayList("eat_cost",ctmp1);
        detail.putIntegerArrayList("live_cost", ctmp2);
        detail.putIntegerArrayList("transport_cost",ctmp3);
        detail.putIntegerArrayList("entertain_cost",ctmp4);
        detail.putIntegerArrayList("buy_cost",ctmp5);
        detail.putIntegerArrayList("other_cost",ctmp6);
        detail.putStringArrayList("eat", tmp1);
        detail.putStringArrayList("live", tmp2);
        detail.putStringArrayList("transport", tmp3);
        detail.putStringArrayList("entertain", tmp4);
        detail.putStringArrayList("buy", tmp5);
        detail.putStringArrayList("other",tmp6);
        detail.putString("user_name", user_name);
        detail.putString("trip_name", tripname);
        //Toast.makeText(this , cosdata1.get(0).toString() , Toast.LENGTH_SHORT);
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
}