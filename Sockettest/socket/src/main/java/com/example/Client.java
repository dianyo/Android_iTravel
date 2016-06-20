package com.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import netscape.javascript.JSObject;

public class Client
{
    public final static String IP = "192.168.44.1";
    public static void main( String[] args ) throws IOException
    {
        // String host = "";
        int port = 5050;
        Socket socket = null;
        Scanner consoleInput = new Scanner( System.in );
        String command;
        //System.out.println("請輸入Server端位址");
        //host = consoleInput.nextLine();
        try
        {
            socket = new Socket( IP, port );
            DataInputStream input = null;
            DataOutputStream output = null;

            try
            {
                input = new DataInputStream( socket.getInputStream() );
                output = new DataOutputStream( socket.getOutputStream() );
                System.out.println( input.readUTF() );
                while ( true )
                {
                    System.out.println("insert command");
                    command = consoleInput.nextLine();
                    if(command.equals("upload")){
                        upload( input , output );

                    }else if(command.equals("getdata")){
                        get(input,output);
                    }else if(command.equals("exit")){
                        output.writeUTF(String.format("exit"));
                        output.flush();
                        break;
                    }else{
                        System.out.println("error command");
                    }

                }
            }
            catch ( IOException e )
            {
            }
            finally
            {
                if ( input != null )
                    input.close();
                if ( output != null )
                    output.close();
            }
        }
        catch ( IOException e ){
            e.printStackTrace();
        }
        finally
        {
            if ( socket != null )
                socket.close();
            if ( consoleInput != null )
                consoleInput.close();
        }
    }
    private static void upload(DataInputStream input,DataOutputStream output){
        String pass = "";
        try{
            output.writeUTF(String.format("upload"));
            output.flush();
            pass = input.readUTF();
            System.out.println( pass );
            if(pass.equals("OK")){
                output.writeUTF("json_test");
                output.flush();
            }
        }catch(IOException e){

        }
    }
    private static void get(DataInputStream input, DataOutputStream output)  {
        ArrayList<String> name = new ArrayList<String>();
        ArrayList<Integer> num = new ArrayList<Integer>();
        name.add("one");
        name.add("two");
        num.add(1);
        num.add(2);


        try{
            String res = "";

            output.writeUTF(String.format("getdata"));
            output.flush();
            res = input.readUTF();
            System.out.println( res );
            if(res.equals("OK")){
                output.writeUTF("eat");
                output.flush();
                res = "";
                res = input.readUTF();
                if(res.equals("OK")){
                    output.writeUTF("noodle");
                    output.flush();
                    System.out.println("result: "+input.readUTF());
                }
            }
        }catch(IOException e){
        }
    }
}

