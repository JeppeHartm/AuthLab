package com.s103458.server;

import com.s103458.request.LoginRequest;
import com.s103458.security.Cryptographer;
import com.s103458.security.RSAEncryptedDataset;
import com.s103458.security.Store;
import com.s103458.security.Ticket;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jeppe on 11/4/15.
 */
public class AuthenticationServant extends UnicastRemoteObject implements AuthenticationService {

    ArrayList<Integer> activeTickets = new ArrayList<Integer>();
    public AuthenticationServant() throws RemoteException {
        super();
    }
    public boolean hasTicket(int ID){
        return activeTickets.contains(ID);
    }
    @Override
    public PublicKey getKey() throws RemoteException {
        return Cryptographer.getPublicKey();
    }

    @Override
    public RSAEncryptedDataset login(RSAEncryptedDataset cred) throws RemoteException {
        LoginRequest lr = null;
        try {
            lr = (LoginRequest) Cryptographer.decryptObject(cred,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String hashedKey = Cryptographer.hash_string(lr.getKey());
        try {
            Store.lookup(lr.getName(),hashedKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return Cryptographer.encryptObject(Ticket.generate(lr,registerTicket(lr)),Cryptographer.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int registerTicket(LoginRequest lr) {
        Random r = new Random();
        int i;
        while(activeTickets.contains(i = r.nextInt()));
        activeTickets.add(i);
        return i;
    }

    @Override
    public boolean logout(RSAEncryptedDataset ticket) throws RemoteException {
        try {
            Ticket t = (Ticket) Cryptographer.decryptObject(ticket,Cryptographer.getPrivateKey());
            return activeTickets.removeIf(i -> i == t.getTicketID());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
