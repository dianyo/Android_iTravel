package com.example.dianyo.itraveler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.RequiresPermission.Read;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class History extends Activity {
    private HistoryAdapter historyadapter;
    private ListView historylist;
    private Button backbutton;
    private ArrayAdapter<String> adapter;
    private List<String> arraylist = new ArrayList<>();
    private  String[] list = new String[1000];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        backbutton = (Button)findViewById(R.id.backbutton);
        /*
        ***************************** test ************************
        String[] data = {"測" , "試" , "資" , "料"};
        writeDataToFile(hisfileName , data);
        **********************************************************
        */
        int layoutId = android.R.layout.simple_list_item_1;
        init_list();
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void init_list(){
        list = new String[1000];
        arraylist.clear();
        String hisfileName = getFilesDir() + "/history1" ;
/*
        //clear garbage
        File file = new File(hisfileName);
        file.delete();
*/
        System.arraycopy(readDataFromFile(hisfileName) , 0 , list , 0 , list.length);
        for(int i = 0 ; i < 1000 ; i++){
            if(list[i] != null){
                String[] out1 = list[i].split("/");
                String[] out2 = out1[out1.length - 1].split("_");
                String user_name = out2[0];
                String trip_name = out2[1];
                arraylist.add("User: " + user_name + "   \nTrip:  " + trip_name);
            }
            else    break;
        }
        historylist = (ListView)this.findViewById(R.id.historylist);
        historyadapter = new HistoryAdapter(this);
        historylist.setAdapter(historyadapter);

       /* adapter = new ArrayAdapter<String>(this, layoutId, arraylist.toArray(new String[0]));
        historylist.setAdapter(adapter);*/
        /*historylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(History.this , "123123" , Toast.LENGTH_SHORT).show();
            }
        });*/
       /* historylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(History.this , "123123" , Toast.LENGTH_SHORT).show();
                ListView listView = (ListView) arg0;
                String title = listView.getItemAtPosition(arg2).toString();
                Intent intent = new Intent();
                intent.setClass(History.this, Read.class);                                  //  這裡接到read的activity
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });*/
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
    public class HistoryAdapter extends BaseAdapter {
        private LayoutInflater myInflater;
        public HistoryAdapter(Context c){
            myInflater = LayoutInflater.from(c);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arraylist.size();//size
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return arraylist.get(position);//get position
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            convertView = myInflater.inflate(R.layout.history_list, null);
            final TextView history = (TextView) convertView.findViewById(R.id.textView_history);
            final Button button = (Button)  convertView.findViewById(R.id.button_history_delete);
            final Button button_edit = (Button) convertView.findViewById(R.id.button_history_edit);
            history.setText(arraylist.get(position));
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(History.this);
                    builder.setTitle("確定刪除?");
                    builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do delete
                            ArrayList<String> nlist = new ArrayList<String>();
                            arraylist.get(position);
                            for(int i = 0 ; i < arraylist.size() ; i++){
                                if(i != position)   nlist.add(arraylist.get(i));
                            }
                            arraylist.clear();
                            String[] hisdata = new String[nlist.size()];
                            for(int i = 0 ; i < nlist.size() ; i++){
                                arraylist.add(nlist.get(i));
                                hisdata[i] = nlist.get(i);
                            }
                            String hisfileName = getFilesDir() + "/history1" ;
                            File hisfile = new File(hisfileName);
                            hisfile.delete();
                            String[] hisdata2 = new String[hisdata.length];
                            for(int i = 0 ; i < hisdata.length ; i++) {
                                String[] out1 = hisdata[i].split(":");
                                String[] out2 = out1[1].split(" ");
                                String[] out3 = out1[2].split(" ");
                                String user_name = out2[1];
                                String tripname = out3[2];
                                hisdata2[i] = getFilesDir() + "/" + user_name + "_" + tripname;
                            }
                            writeDataToFile(hisfileName , hisdata2);
                            init_list();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            });
            button_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = arraylist.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("title" , title);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(History.this, com.example.dianyo.itraveler.Read.class);
                    startActivity(intent);
                }
            });
            return convertView;
        }

    }
}