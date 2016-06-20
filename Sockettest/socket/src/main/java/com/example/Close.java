package com.example;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by Simon on 2016/6/15.
 */
public class Close {

    public static void main(String []args) throws IOException{
        ServerSocket serversocket = null;
        serversocket = new ServerSocket(5050);
        serversocket.close();
    }

}
