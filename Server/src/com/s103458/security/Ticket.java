package com.s103458.security;

import com.s103458.request.LoginRequest;

import java.io.Serializable;
import java.security.PublicKey;

/**
 * Created by jeppe on 11/5/15.
 */
public class Ticket implements Serializable {
    String name;
    int ticketID;
    int reqid;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    PublicKey publicKey;

    public Ticket(String name, int reqid, PublicKey publicKey, int ticketID) {
        this.name = name;
        this.ticketID = ticketID;
        this.reqid = reqid;
        this.publicKey = publicKey;
    }

    public static Ticket generate(LoginRequest lr,int ticketID) {
        return new Ticket(lr.getName(),lr.getReqid(),lr.getPublicKey(),ticketID);
    }

    public int getTicketID() {
        return ticketID;
    }
}
