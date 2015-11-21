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
import java.security.PublicKey;

/**
 * Created by jeppe on 11/4/15.
 */
public class PrinterServant extends UnicastRemoteObject implements PrinterService{
    private static int log_count = 0;
    protected PrinterServant() throws RemoteException {
        super();
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

    @Override
    public RSAEncryptedDataset print(RSAEncryptedDataset red) throws RemoteException {
        PrintRequest re = null;
        try {
            re = (PrintRequest) Cryptographer.decryptObject(red,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Ticket t = re.getTicket(Cryptographer.getPrivateKey());
        AccessController.check(t.getName(),"print");
        if(Server.getAs().hasTicket(t.getTicketID())){
            writeToLog("Access granted");
            String reply = "Printing \""+re.getFilename()+"\" on printer: "+re.getPrinter();
            System.out.println(reply);
            try {
                return Cryptographer.encryptObject(reply,t.getPublicKey());
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            writeToLog("Access denied");
        }

        return null;
    }

    @Override
    public RSAEncryptedDataset queue(RSAEncryptedDataset red) throws RemoteException {
        try {
            QueueRequest re = (QueueRequest) Cryptographer.decryptObject(red,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void topQueue(RSAEncryptedDataset red) throws RemoteException {
        try {
            TopQueueRequest re = (TopQueueRequest) Cryptographer.decryptObject(red,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(RSAEncryptedDataset red) throws RemoteException {
        try {
            StartRequest re = (StartRequest) Cryptographer.decryptObject(red,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(RSAEncryptedDataset red) throws RemoteException {
        try {
            StopRequest re = (StopRequest) Cryptographer.decryptObject(red,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restart(RSAEncryptedDataset red) throws RemoteException {
        try {
            RestartRequest re = (RestartRequest) Cryptographer.decryptObject(red,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RSAEncryptedDataset status(RSAEncryptedDataset red) throws RemoteException {
        try {
            StatusRequest re = (StatusRequest) Cryptographer.decryptObject(red,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RSAEncryptedDataset readConfig(RSAEncryptedDataset red) throws RemoteException {
        try {
            ReadConfigRequest re = (ReadConfigRequest) Cryptographer.decryptObject(red,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setConfig(RSAEncryptedDataset red) throws RemoteException {
        try {
            SetConfigRequest re = (SetConfigRequest) Cryptographer.decryptObject(red,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
