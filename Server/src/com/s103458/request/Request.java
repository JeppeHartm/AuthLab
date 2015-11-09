package com.s103458.request;

import com.s103458.security.Cryptographer;
import com.s103458.security.RSAEncryptedDataset;
import com.s103458.security.Ticket;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.PrivateKey;

/**
 * Created by Jeppe on 09-11-2015.
 */
public abstract class Request implements Serializable {
    RSAEncryptedDataset ticket;
    int reqid;

    public Request(RSAEncryptedDataset ticket, int reqid) {
        this.ticket = ticket;
        this.reqid = reqid;
    }

    public Ticket getTicket(PrivateKey key) {
        try {
            return (Ticket)Cryptographer.decryptObject(ticket,key);
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
        return null;
    }

    public int getReqid() {
        return reqid;
    }
}
