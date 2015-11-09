package com.s103458.server;

import com.s103458.request.*;
import com.s103458.security.Cryptographer;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;

/**
 * Created by jeppe on 11/4/15.
 */
public class PrinterServant extends UnicastRemoteObject implements PrinterService{
    protected PrinterServant() throws RemoteException {
        super();
    }

    @Override
    public String print(byte[] pr) throws RemoteException {
        try {
            PrintRequest re = (PrintRequest) Cryptographer.decryptObject(pr,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String queue(byte[] qr) throws RemoteException {
        try {
            QueueRequest re = (QueueRequest) Cryptographer.decryptObject(qr,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void topQueue(byte[] tqr) throws RemoteException {
        try {
            TopQueueRequest re = (TopQueueRequest) Cryptographer.decryptObject(tqr,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(byte[] sr) throws RemoteException {
        try {
            StartRequest re = (StartRequest) Cryptographer.decryptObject(sr,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(byte[] sr) throws RemoteException {
        try {
            StopRequest re = (StopRequest) Cryptographer.decryptObject(sr,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restart(byte[] rr) throws RemoteException {
        try {
            RestartRequest re = (RestartRequest) Cryptographer.decryptObject(rr,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String status(byte[] sr) throws RemoteException {
        try {
            StatusRequest re = (StatusRequest) Cryptographer.decryptObject(sr,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String readConfig(byte[] rcr) throws RemoteException {
        try {
            ReadConfigRequest re = (ReadConfigRequest) Cryptographer.decryptObject(rcr,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setConfig(byte[] scr) throws RemoteException {
        try {
            SetConfigRequest re = (SetConfigRequest) Cryptographer.decryptObject(scr,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
