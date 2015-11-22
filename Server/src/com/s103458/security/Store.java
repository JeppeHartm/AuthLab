package com.s103458.security;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeppe on 11/4/15.
 */
public class Store {
    private static final String password_file = "Server/res/passwords";
    private static Map<String, String> roles;
    static {
        importRoles();
    }
    public static String getRole(String name){
        return roles.get(name);
    }
    private static void importRoles() {
        roles = new HashMap<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(password_file)));
        }catch(FileNotFoundException fnfe){
            System.out.println("Couldn't find the passwords file!");
        }
        String data = "";
        String line;
        try {
            while ((line = reader.readLine()) != null) {

                data += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert data.matches("((\\w+:::(.|\\s)*:::[A-Z]+[@])*\\w+:::(.|\\s)*:::[A-Z]+)?");
        String[] entries = data.split("@");
        for(String entry:entries){
            String[] subs = entry.split(":::");
            roles.put(subs[0],subs[2]);
        }
    }

    public static void store(String name, String hashedkey) throws IOException {
        if(!lookup(name,hashedkey)) {
            FileWriter fw = new FileWriter(password_file, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(name + ":::" + hashedkey);
            pw.close();
            fw.close();
        }
    }
    public static boolean lookup(String name,String hashedkey) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(password_file)));
        }catch(FileNotFoundException fnfe){
            return false;
        }
        String data = "";
        String line;
        while ((line = reader.readLine()) != null) {
            data += line;
        }
        assert data.matches("((\\w+:::(.|\\s)*:::[A-Z]+[@])*\\w+:::(.|\\s)*:::[A-Z]+)?");
        String[] entries = data.split("@");
        for(String entry:entries){
            String[] subs = entry.split(":::");
            if(subs[0].equals(name) && subs[1].equals(hashedkey))return true;
        }
        return false;
    }
}
