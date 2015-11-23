package com.s103458.server;

import com.s103458.security.Store;
import javafx.application.Application;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by jeppe on 11/20/15.
 */
public class AccessController {
    private final static String POLICY="Server/res/acl";
    private final static String POLICY_RBAC="Server/res/rbac";
    private static Map<String,String[]> policy;
    private static Map<String,String[]> policy_rbac;

    static {

        try {
            loadPolicy();
            loadPolicy_rbac();
        } catch (FileNotFoundException e) {
            System.out.println("Policy file not located!");

        } catch (IOException e) {
            System.out.println("Error while reading line! Perhaps premature end of file?");
        }
    }

    private static void loadPolicy_rbac() throws IOException {
        policy_rbac = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(POLICY_RBAC)));
        String line;
        while ((line = reader.readLine()) != null) {
            assert line.matches("\\w+(:[A-Z]+(,[A-Z]+)*)?=(\\w+(,\\w+)*)?")  : "Invalid rbac entry: \""+line+"\"";
            String[] s = line.split("=");
            String[] sub = s[0].split(":");
            String role = sub[0];
            String[] operations = {};
            if(s.length > 1)operations = s[1].split(",");
            if(sub.length>1){
                String[] parents = sub[1].split(",");
                Set<String> opSet = new HashSet<>();
                for (String parent : parents) opSet.addAll(Arrays.asList(policy_rbac.get(parent)));
                opSet.addAll(Arrays.asList(operations));
                String[] output = new String[opSet.size()];
                int i = 0;
                for (String str:opSet) {
                    output[i++] = str;
                }
                policy_rbac.put(role,output);
            }else{
                policy_rbac.put(role,operations);
            }



        }
    }

    private static void loadPolicy() throws IOException {
        policy = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(POLICY)));
        String line;
        while ((line = reader.readLine()) != null) {
            assert line.matches("\\w+=(\\w+(,\\w+)*)?") : "Invalid acl entry: \""+line+"\"";
            String[] s = line.split("=");
            String operation = s[0];
            String[] names = s[1].split(",");
            policy.put(operation,names);

        }
    }
    public static boolean check(String username, String methodname) {
        //printPolicy();
//        for (String s:policy.get(methodname))
//            if(s.equals(username)) return true;
//        return false;
        for (String s:policy_rbac.get(Store.getRole(username))) {
            if(s.equals(methodname))return true;
        }
        return false;
    }
    public static void printPolicy(){
        String output="";
        for(Map.Entry<String,String[]> e:policy.entrySet()){
            output+=e.getKey()+": ";
            for(String s:e.getValue()){
                output+=s+" ";
            }
            output+="\n";
        }
        System.out.println(output);
    }
}
