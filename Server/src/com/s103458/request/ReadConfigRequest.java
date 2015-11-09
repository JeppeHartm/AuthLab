package com.s103458.request;

import com.s103458.security.Ticket;
import com.sun.org.apache.xpath.internal.operations.String;

/**
 * Created by Jeppe on 09-11-2015.
 */
public class ReadConfigRequest extends Request{
    private final String parameter;

    public ReadConfigRequest(Ticket ticket, int reqid, String parameter) {
        super(ticket, reqid);
        this.parameter = parameter;
    }
}
