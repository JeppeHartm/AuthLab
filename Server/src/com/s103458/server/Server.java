package com.s103458.server;

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
    public static void main(String[] args) throws RemoteException{
        Registry reg = LocateRegistry.createRegistry(8081);
        reg.rebind("authenticator",as = new AuthenticationServant());
        reg.rebind("printer",ps = new PrinterServant());


    }
}
