package com.example.dianyo.itraveler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Client_service extends Activity {
    private Thread thread;                //執行緒
    private Socket clientSocket;        //客戶端的socket
    private JSONObject json_upload;        //從java伺服器傳遞與接收資料的json
    public final static String IP = "192.168.44.1";
    private String command = "";
    private final int result_fail = 0;
    private Bundle client_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.print("in Clent_service");
        client_file = this.getIntent().getExtras();
        command = client_file.getString("command");
        //setContentView(R.layout.upload_test);
        thread = new Thread(Connection);                                                                                                                                                                                                                                                                                                                                                                                                                                                             //賦予執行緒工作
        thread.start();
    }
    private void fail(){
        setResult(result_fail);
        finish();
    }
    //連結socket伺服器做傳送與接收
    private Runnable Connection = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                System.out.print("in COnnection");
                // IP為Server端
                InetAddress serverIp = InetAddress.getByName("10.5.2.28");
                int serverPort = 5050;
                clientSocket = new Socket(serverIp, serverPort);
                //取得網路輸出串流
                DataInputStream input = new DataInputStream( clientSocket.getInputStream() );
                DataOutputStream output = new DataOutputStream( clientSocket.getOutputStream() );
                System.out.println( input.readUTF() );
                //String command ="upload";
                if(command.equals("upload")){
                    upload( input , output );

                }else if(command.equals("getdata")){
                    get(input,output);
                }else if(command.equals("exit")){
                    output.writeUTF(String.format("exit"));
                    output.flush();

                }else{
                    System.out.println("error command");
                }
                output.writeUTF("exit");
                output.flush();

            } catch (Exception e) {
                //當斷線時會跳到catch,可以在這裡寫上斷開連線後的處理
                e.printStackTrace();
                // TextView v = (TextView) findViewById(R.id.upload_test);
                //v.setText("socket error");
                Log.e("text", "Socket連線=" + e.toString());
                fail();    //當斷線時自動關閉房間
            }finally{
                finish();
            }
        }

    };
    private void upload(DataInputStream input,DataOutputStream output){
        String pass = "";
        ArrayList<String> eat = client_file.getStringArrayList("eat");
        ArrayList<String> live = client_file.getStringArrayList("live");
        ArrayList<String> transport = client_file.getStringArrayList("transport");
        ArrayList<String> entertain = client_file.getStringArrayList("entertain");
        ArrayList<String> buy = client_file.getStringArrayList("buy");
        ArrayList<String> other = client_file.getStringArrayList("other");
        ArrayList<Integer> eat_cost = client_file.getIntegerArrayList("eat_cost");
        ArrayList<Integer> live_cost = client_file.getIntegerArrayList("live_cost");
        ArrayList<Integer> transport_cost = client_file.getIntegerArrayList("transport_cost");
        ArrayList<Integer> entertain_cost = client_file.getIntegerArrayList("entertain_cost");
        ArrayList<Integer> buy_cost = client_file.getIntegerArrayList("buy_cost");
        ArrayList<Integer> other_cost = client_file.getIntegerArrayList("other_cost");
        ArrayList<ArrayList<String>> node_object = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<Integer>> node_cost = new ArrayList<ArrayList<Integer>>();
        node_object.add(eat);
        node_object.add(live);
        node_object.add(transport);
        node_object.add(entertain);
        node_object.add(buy);
        node_object.add(other);
        node_cost.add(eat_cost);
        node_cost.add(live_cost);
        node_cost.add(transport_cost);
        node_cost.add(entertain_cost);
        node_cost.add(buy_cost);
        node_cost.add(other_cost);
        String gson_object = new Gson().toJson(node_object);
        String gson_cost = new Gson().toJson(node_cost);
        try{
            output.writeUTF("upload");
            output.flush();
            pass = input.readUTF();
            System.out.println( pass );
            if(pass.equals("OK")){
                pass = "";
                output.writeUTF(gson_object);
                output.flush();
                //pass = input.readUTF();
                output.writeUTF(gson_cost);
                output.flush();
                setResult(RESULT_OK);
            }
        }catch(IOException e){
            fail();
        }
    }
    private void get(DataInputStream input, DataOutputStream output)  {
        float result;
        String object = "";
        String subobject = "";
        object = client_file.getString("object");
        subobject = client_file.getString("subobject");
        //object = "eat";
        //subobject = "noodle";
        try{
            String res = "";
            output.writeUTF(String.format("getdata"));
            output.flush();
            res = input.readUTF();
            System.out.println( res );
            if(res.equals("OK")){
                output.writeUTF(object);
                output.flush();
                res = "";
                res = input.readUTF();
                if(res.equals("OK")){
                    output.writeUTF(subobject);
                    output.flush();
                    result = Float.valueOf(input.readUTF());
                    System.out.println("result: "+ result);
                    Bundle bundle = new Bundle();
                    Intent intent = this.getIntent();
                    bundle.putFloat("result",result);
                    intent.putExtras(bundle);
                    Client_service.this.setResult(RESULT_OK,intent);
                }
            }
        }catch(IOException e){
            fail();
        }
    }

}