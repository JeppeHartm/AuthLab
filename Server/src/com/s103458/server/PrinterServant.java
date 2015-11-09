package com.s103458.server;

import com.s103458.request.*;
import com.s103458.security.Cryptographer;
import com.s103458.security.RSAEncryptedDataset;

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
    public byte[] print(RSAEncryptedDataset red) throws RemoteException {
        try {
            PrintRequest re = (PrintRequest) Cryptographer.decryptObject(red,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] queue(RSAEncryptedDataset red) throws RemoteException {
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
    public byte[] status(RSAEncryptedDataset red) throws RemoteException {
        try {
            StatusRequest re = (StatusRequest) Cryptographer.decryptObject(red,Cryptographer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] readConfig(RSAEncryptedDataset red) throws RemoteException {
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
