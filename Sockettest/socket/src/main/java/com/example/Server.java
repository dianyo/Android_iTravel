package com.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.Object;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Server
{
    public static final int LISTEN_PORT = 5050;
    public void listenRequest()
    {
        ServerSocket serverSocket = null;
        ExecutorService threadExecutor = Executors.newCachedThreadPool();
        try
        {
            serverSocket = new ServerSocket( LISTEN_PORT );
            System.out.println("Server listening requests...");
            while ( true )
            {
                Socket socket = serverSocket.accept();
                threadExecutor.execute( new RequestThread( socket ) );
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( threadExecutor != null )
                threadExecutor.shutdown();
            if ( serverSocket != null )
                try
                {
                    serverSocket.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
        }
    }
    private static ServerAverage sa;
    /**
     * @param args
     */
    public static void main( String[] args ) throws IOException
    {
        sa = new ServerAverage();
        Server server = new Server();
        server.listenRequest();
    }

    /**
     * 處理Client端的Request執行續。
     *
     * @version
     */
    class RequestThread implements Runnable
    {
        public final String [] object_string = {"eat","entertain","live","transport","buy","other"};
        private Socket clientSocket;


        public RequestThread( Socket clientSocket )
        {
            this.clientSocket = clientSocket;
        }

        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        private void deal_upload(String gson_object, String gson_cost) throws IOException{
            ArrayList<ArrayList<String>> list_object = new Gson().fromJson(gson_object, new TypeToken<ArrayList<ArrayList<String>>>() {}.getType());
            ArrayList<ArrayList<Integer>> list_cost = new Gson().fromJson(gson_cost, new TypeToken<ArrayList<ArrayList<Integer>>>() {}.getType());
            String [] objects = {"eat","live","transport","entertain","buy","other"};
            int i;
            for(i = 0; i < 6; i++){
                sa.inputArrayList(objects[i],list_object.get(i),list_cost.get(i));
            }

            //System.out.println("arraylist: "+ list_object.get(0).get(1));
        }
        private void upload(DataInputStream input, DataOutputStream output){
            String gson_object;
            String gson_cost;
            try{
                System.out.println("upload");
                output.writeUTF("OK");
                output.flush();
                gson_object = input.readUTF();
                System.out.println(gson_object);
                gson_cost = input.readUTF();
                System.out.println(gson_cost);
                //DO~~~~ to deal with the json
                deal_upload(gson_object, gson_cost);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        private void getdata(DataInputStream input, DataOutputStream output) throws IOException {
            String object = "";
            String subobject = "";
            float average;

            try{
                System.out.println("getdata");
                output.writeUTF("OK");
                output.flush();
                object = input.readUTF();
                int i;
                for(i = 0; i<6 ; i++){
                    if(object.equals(object_string[i])){
                        output.writeUTF("OK");
                        output.flush();
                        subobject = input.readUTF();
                        //do
                        /*ServerAverage serveraverage = new ServerAverage(object,subobject);
                        serveraverage.test();
                        average = serveraverage.get_average();*/
                        average = sa.getAverage(object,subobject);
                        output.writeUTF(Float.toString(average));
                        output.flush();

                        break;
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }

        }
        @Override
        public void run()
        {
            System.out.printf("有%s連線進來!\n", clientSocket.getRemoteSocketAddress() );
            DataInputStream input = null;
            DataOutputStream output = null;
            String command = "";
            try
            {
                input = new DataInputStream( this.clientSocket.getInputStream() );
                output = new DataOutputStream( this.clientSocket.getOutputStream() );
                output.writeUTF( String.format("Hi, %s!\n", clientSocket.getRemoteSocketAddress() ) );
                output.flush();
                while ( true )
                {
                    // TODO 處理IO，這邊定義protocol協定！！
                    command = input.readUTF();
                    if(command.equals("upload")){
                        upload(input,output);
                    }else if(command.equals("getdata")){
                        getdata(input,output);
                    }else if(command.equals("exit")){
                        System.out.println("exit");
                        break;
                    }else{
                        System.out.println("error");
                    }
                    //break;
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if ( input != null )
                        input.close();
                    if ( output != null )
                        output.close();
                    if ( this.clientSocket != null && !this.clientSocket.isClosed() )
                        this.clientSocket.close();
                    sa.writeToFiles();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }

            }
        }
    }
}

