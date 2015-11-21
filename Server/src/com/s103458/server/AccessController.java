package com.s103458.server;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by jeppe on 11/20/15.
 */
public class AccessController {
    private final String POLICY="acl";
    private static String[] methods;
    private static Map<String,String[]> policy;

    static {

        Method[] meths = PrinterServant.class.getMethods();
        methods = new String[meths.length];
        for (int i = 0; i < meths.length; i++) {
            methods[i] = meths[i].getName();
        }
        policy = loadPolicy();
    }

    private static Map<String,String[]> loadPolicy() {
        return null;
    }

    public static boolean check(String username, String methodname) {
        for (String s:policy.get(username))
            if(s == methodname) return true;
        return false;
    }
}
