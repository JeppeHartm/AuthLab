package com.s103458.server;

import com.s103458.security.Cryptographer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * Created by Jeppe on 09-11-2015.
 */
public class Server {
    public static AuthenticationServant getAs() {
        return as;
    }

    public static PrinterServant getPs() {
        return ps;
    }

    public static AuthenticationServant as;
    public static PrinterServant ps;
    public static void main(String[] args) throws IOException {
        Registry reg = LocateRegistry.createRegistry(8081);
        reg.rebind("authenticator",as = new AuthenticationServant());
        reg.rebind("printer",ps = new PrinterServant());
        FileWriter fw = new FileWriter(new File("test"));

        String s = "Alice:"+Cryptographer.hash_string("alice")+":MANAGER\n"+
        "Bob:"+Cryptographer.hash_string("bob")+":SERVICE\n"+
        "Cecilia:"+Cryptographer.hash_string("cecilia")+":POWERUSER\n"+
        "David:"+Cryptographer.hash_string("david")+":USER\n"+
        "Erica:"+Cryptographer.hash_string("erica")+":USER\n"+
        "Fred:"+Cryptographer.hash_string("fred")+":USER\n"+
                "Ida:"+Cryptographer.hash_string("ida")+":POWERUSER\n"+
                "Henry:"+Cryptographer.hash_string("henry")+":USER\n"+
        "George:"+Cryptographer.hash_string("george")+":USER";
        fw.write(s);
        fw.close();
    }
}
