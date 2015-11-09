package com.s103458.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by Jeppe on 09-11-2015.
 */
public class Server {
    public static void main(String[] args) throws RemoteException{
        Registry reg = LocateRegistry.createRegistry(8081);
        reg.rebind("authenticator",new AuthenticationServant());
        reg.rebind("printer",new PrinterServant());
    }
}
