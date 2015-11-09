package com.s103458.server;

import com.s103458.security.RSAEncryptedDataset;
import com.s103458.security.Ticket;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.PublicKey;

/**
 * Created by jeppe on 11/4/15.
 */
public interface AuthenticationService extends Remote {
    PublicKey getKey() throws RemoteException;
    RSAEncryptedDataset login(RSAEncryptedDataset cred) throws RemoteException;
    boolean logout(Ticket t) throws RemoteException;
}
