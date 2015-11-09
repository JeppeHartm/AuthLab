package com.s103458.security;

import java.io.*;

/**
 * Created by jeppe on 11/4/15.
 */
public class Store {
    public static void store(String name, String hashedkey) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("resources/passwords")));
        writer.write(name+":"+hashedkey);
        writer.newLine();
    }
    public static boolean lookup(String name,String hashedkey) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("resources/passwords")));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] s = line.split(":");
            if(s[0] == name && s[1] == hashedkey)return true;
        }
        return false;
    }
}
