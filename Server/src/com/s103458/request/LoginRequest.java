package com.s103458.request;

import com.s103458.security.Ticket;

import java.io.Serializable;
import java.security.PublicKey;

/**
 * Created by Jeppe on 09-11-2015.
 */
public class LoginRequest implements Serializable{

    private final String name;
    private final String key;
    private final PublicKey publicKey;

    public int getReqid() {
        return reqid;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    private final int reqid;

    public LoginRequest(String name, String key, int reqid, PublicKey publicKey) {
        this.name = name;
        this.key = key;
        this.reqid = reqid;
        this.publicKey = publicKey;
    }
}
