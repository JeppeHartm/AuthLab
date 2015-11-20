package com.s103458.security;

import java.io.*;

/**
 * Created by jeppe on 11/4/15.
 */
public class Store {
    static {
        try {
            store("Jeppe",Cryptographer.hash_string("Hartmund"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void store(String name, String hashedkey) throws IOException {
        if(!lookup(name,hashedkey)) {
            FileWriter fw = new FileWriter("passwords", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(name + ":" + hashedkey);
            pw.close();
            fw.close();
        }
    }
    public static boolean lookup(String name,String hashedkey) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream("passwords")));
        }catch(FileNotFoundException fnfe){
            return false;
        }
        String line;
        while ((line = reader.readLine()) != null) {
            String[] s = line.split(":");
            if(s[0] == name && s[1] == hashedkey)return true;
        }
        return false;
    }
}
