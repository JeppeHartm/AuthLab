package com.s103458.server;

import com.s103458.request.*;
import com.s103458.security.Cryptographer;
import com.s103458.security.RSAEncryptedDataset;
import com.s103458.security.Ticket;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeppe on 11/4/15.
 */
public class PrinterServant extends UnicastRemoteObject implements PrinterService{
    private static int log_count = 0;
    private static boolean online;
    private static ArrayList<String> queue;
    private static Map<String,String> config;
    protected PrinterServant() throws RemoteException {
        super();
        queue = new ArrayList<String>();
        config = new HashMap<>();
        online = false;
    }

    private void writeToLog(String msg){
        try {
            FileWriter fw = new FileWriter("accesslog",true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println((log_count++)+":\t"+msg);
            fw.close();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private Object[] generateSecurityParams(RSAEncryptedDataset encryptedRequest, String operation){
        Request requestObject = null;
        try {
            requestObject = (Request) Cryptographer.decryptObject(encryptedRequest,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            System.out.println("Decryption failed!");
        }
        Ticket t = requestObject.getTicket(Cryptographer.getPrivateKey());
        if(!AccessController.check(t.getName(),"print")||!Server.getAs().hasTicket(t.getTicketID())){
            writeToLog("Access denied");
            return null;
        }else{
            writeToLog("Access granted");
            Object[] output = {requestObject,t};
            return output;
        }

    }
    @Override
    public RSAEncryptedDataset print(RSAEncryptedDataset red) throws RemoteException {
        Object[] res = generateSecurityParams(red,"print");
        assert res != null;
        PrintRequest re = (PrintRequest) res[0];
        String reply = "Printing \""+re.getFilename()+"\" on printer: "+re.getPrinter();
        System.out.println(reply);
        try {
            queue.add(reply);
            return Cryptographer.encryptObject(reply,((Ticket)res[1]).getPublicKey());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public RSAEncryptedDataset queue(RSAEncryptedDataset red) throws RemoteException {
        Object[] res = generateSecurityParams(red,"queue");
        assert res != null;
        QueueRequest re = (QueueRequest) res[0];
        String reply = "";
        for (String s:queue) {
            reply += s+"\n";
        }
        try {
            return Cryptographer.encryptObject(reply,((Ticket)res[1]).getPublicKey());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void topQueue(RSAEncryptedDataset red) throws RemoteException {
        Object[] res = generateSecurityParams(red,"topQueue");
        assert res != null;
        TopQueueRequest re = (TopQueueRequest) res[0];
        String job = queue.remove(re.getJob());
        queue.add(0,job);

    }

    @Override
    public void start(RSAEncryptedDataset red) throws RemoteException {
        assert generateSecurityParams(red,"start") != null;
        if(!online) online = true;
    }

    @Override
    public void stop(RSAEncryptedDataset red) throws RemoteException {
        assert generateSecurityParams(red,"stop") != null;
        if(online) online = false;
    }

    @Override
    public void restart(RSAEncryptedDataset red) throws RemoteException {
        assert generateSecurityParams(red,"restart") != null;
        online = true;
    }

    @Override
    public RSAEncryptedDataset status(RSAEncryptedDataset red) throws RemoteException {
        Object[] res = generateSecurityParams(red,"status");
        assert res != null;
        StatusRequest re = (StatusRequest) res[0];
        String reply = "Print server is "+(online?"online":"offline");
        try {
            return Cryptographer.encryptObject(reply,((Ticket)res[1]).getPublicKey());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RSAEncryptedDataset readConfig(RSAEncryptedDataset red) throws RemoteException {
        Object[] res = generateSecurityParams(red,"readConfig");
        assert res != null;
        ReadConfigRequest re = (ReadConfigRequest) res[0];
        String reply = "";
        for (Map.Entry<String,String> e:config.entrySet()) {
            reply += e.getKey()+": "+e.getValue()+"\n";
        }
        try {
            return Cryptographer.encryptObject(reply,((Ticket)res[1]).getPublicKey());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setConfig(RSAEncryptedDataset red) throws RemoteException {
        Object[] res = generateSecurityParams(red,"setConfig");
        assert res != null;
        SetConfigRequest re = (SetConfigRequest) res[0];
        config.put(re.getParameter(),re.getValue());
    }
}
