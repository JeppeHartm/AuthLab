package com.s103458.request;

import com.s103458.security.Ticket;

/**
 * Created by Jeppe on 09-11-2015.
 */
public class PrintRequest extends Request {
    private final String filename;
    private final String printer;
    public PrintRequest(Ticket ticket, int reqid, String filename, String printer) {
        super(ticket, reqid);
        this.filename = filename;
        this.printer = printer;
    }
}
