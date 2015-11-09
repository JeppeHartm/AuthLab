package com.s103458.request;

import com.s103458.security.Ticket;

/**
 * Created by Jeppe on 09-11-2015.
 */
public class QueueRequest extends Request {
    public QueueRequest(Ticket ticket, int reqid) {
        super(ticket, reqid);
    }
}
