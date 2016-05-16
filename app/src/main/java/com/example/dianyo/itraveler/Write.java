package com.example.dianyo.itraveler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Write extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        Bundle bundle = this.getIntent().getExtras();
        //test data ************************************
/*
        Bundle bundle = new Bundle();
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("test1");
        stringArrayList.add("test2");
        stringArrayList.add("test3");
        ArrayList<Integer> intArrayList = new ArrayList<>();
        intArrayList.add(0);
        intArrayList.add(9);
        intArrayList.add(4);
        String user_name = "user_test";
        String trip_name = "trip_test";
        bundle.putIntegerArrayList("eat_cost", intArrayList);
        bundle.putIntegerArrayList("live_cost", intArrayList);
        bundle.putIntegerArrayList("transport_cost", intArrayList);
        bundle.putIntegerArrayList("entertain_cost", intArrayList);
        bundle.putIntegerArrayList("buy_cost", intArrayList);
        bundle.putIntegerArrayList("other_cost", intArrayList);
        bundle.putStringArrayList("eat", stringArrayList);
        bundle.putStringArrayList("live", stringArrayList);
        bundle.putStringArrayList("transport", stringArrayList);
        bundle.putStringArrayList("entertain", stringArrayList);
        bundle.putStringArrayList("buy", stringArrayList);
        bundle.putStringArrayList("other", stringArrayList);
        bundle.putString("user_name", user_name);
        bundle.putString("trip_name", trip_name);
*/
        //init bundle data*******************************
        List<String> data1 = new ArrayList<>();
        List<String> data2 = new ArrayList<>();
        List<String> data3 = new ArrayList<>();
        List<String> data4 = new ArrayList<>();
        List<String> data5 = new ArrayList<>();
        List<String> data6 = new ArrayList<>();
        List<Integer> cosdata1 = new ArrayList<>();
        List<Integer> cosdata2 = new ArrayList<>();
        List<Integer> cosdata3 = new ArrayList<>();
        List<Integer> cosdata4 = new ArrayList<>();
        List<Integer> cosdata5 = new ArrayList<>();
        List<Integer> cosdata6 = new ArrayList<>();
        String username , tripname;
        //set data***********************************
        data1 = bundle.getStringArrayList("eat");
        data2 = bundle.getStringArrayList("live");
        data3 = bundle.getStringArrayList("transport");
        data4 = bundle.getStringArrayList("entertain");
        data5 = bundle.getStringArrayList("buy");
        data6 = bundle.getStringArrayList("other");
        cosdata1 = bundle.getIntegerArrayList("eat_cost");
        cosdata2 = bundle.getIntegerArrayList("live_cost");
        cosdata3 = bundle.getIntegerArrayList("transport_cost");
        cosdata4 = bundle.getIntegerArrayList("entertain_cost");
        cosdata5 = bundle.getIntegerArrayList("buy_cost");
        cosdata6 = bundle.getIntegerArrayList("other_cost");
        username = bundle.getString("user_name");
        tripname = bundle.getString("trip_name");
        if(new String(username).equals(null)){
            username = "unknown";
        }
        if(new String(tripname).equals(null)){
            tripname = "unknown";
        }
        //filename*************************************
        String fileName = getFilesDir() + "/" + username + "_" + tripname;
        //存歷史**********************************************
        String hisfile = getFilesDir() + "/history1";
        String[] history = readDataFromFile(hisfile);
        List<String> historyArraylist = new ArrayList<>();
        for(int i = 0 ; i < 1000 ; i++){
            if(history[i] != null)  historyArraylist.add(history[i]);
            else    break;
        }
        historyArraylist.add(fileName);
        String[] hisstring = historyArraylist.toArray(new String[0]);
        writeDataToFile(hisfile , hisstring);
        //arraylist to string array***************************************
        String[] string1 = data1.toArray(new String[0]);
        String[] string2 = data2.toArray(new String[0]);
        String[] string3 = data3.toArray(new String[0]);
        String[] string4 = data4.toArray(new String[0]);
        String[] string5 = data5.toArray(new String[0]);
        String[] string6 = data6.toArray(new String[0]);
        //arraylist to integer array**************************************
        Integer[] integer1 = cosdata1.toArray(new Integer[0]);
        Integer[] integer2 = cosdata2.toArray(new Integer[0]);
        Integer[] integer3 = cosdata3.toArray(new Integer[0]);
        Integer[] integer4 = cosdata4.toArray(new Integer[0]);
        Integer[] integer5 = cosdata5.toArray(new Integer[0]);
        Integer[] integer6 = cosdata6.toArray(new Integer[0]);
        //set integer array to string************************************
        String[] intstring1 = transforminttostring(integer1);
        String[] intstring2 = transforminttostring(integer2);
        String[] intstring3 = transforminttostring(integer3);
        String[] intstring4 = transforminttostring(integer4);
        String[] intstring5 = transforminttostring(integer5);
        String[] intstring6 = transforminttostring(integer6);
        //string write ************************************************
        writeDataToFile(fileName + "_eat" , string1);
        writeDataToFile(fileName + "_live" , string2);
        writeDataToFile(fileName + "_transport" , string3);
        writeDataToFile(fileName + "_entertain" , string4);
        writeDataToFile(fileName + "_buy" , string5);
        writeDataToFile(fileName + "_other" , string6);
        //integer write *************************************************
        writeDataToFile(fileName + "_eat_cost" , intstring1);
        writeDataToFile(fileName + "_live_cost" , intstring2);
        writeDataToFile(fileName + "_transport_cost" , intstring3);
        writeDataToFile(fileName + "_entertain_cost" , intstring4);
        writeDataToFile(fileName + "_buy_cost" , intstring5);
        writeDataToFile(fileName + "_other_cost" , intstring6);
        // test method
        /*
        String[] tmp1 = new String[integer6.length];
        System.arraycopy(readDataFromFile(fileName + "_other_cost") , 0 , tmp1 , 0 , tmp1.length);
        Integer[] ttmp1 = new Integer[integer6.length];
        ttmp1 = destransform(tmp1);
        tv.setText(ttmp1[1] + " and " + ttmp1[2]);
        */
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
    private String[] transforminttostring(Integer[] intarray){
        String[] result = new String[intarray.length];
        for(int i = 0 ; i < intarray.length ; i++){
            result[i] = intarray[i] != null ? intarray[i].toString() : null;
        }
        return result;
    }
    private  Integer[] destransform(String[] stringarray){   // for debug
        Integer[] result = new Integer[stringarray.length];
        for(int i = 0 ; i < stringarray.length ; i++){
            result[i] = stringarray[i] != null ? Integer.parseInt(stringarray[i]) : null;
        }
        return  result;
    }
}
