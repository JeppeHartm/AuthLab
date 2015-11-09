package com.s103458.request;

import com.s103458.security.Ticket;

/**
 * Created by Jeppe on 09-11-2015.
 */
public class TopQueueRequest extends Request{
    private final int job;

    public TopQueueRequest(Ticket ticket, int reqid, int job) {
        super(ticket, reqid);
        this.job = job;
    }
}
