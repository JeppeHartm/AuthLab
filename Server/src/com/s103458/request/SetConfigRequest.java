package com.s103458.request;

import com.s103458.security.Ticket;

/**
 * Created by Jeppe on 09-11-2015.
 */
public class SetConfigRequest extends Request{
    private final String parameter;
    private final String value;

    public SetConfigRequest(Ticket ticket, int reqid, String parameter, String value) {
        super(ticket, reqid);
        this.parameter = parameter;
        this.value = value;
    }
}
