package com.s103458.request;

import com.s103458.security.RSAEncryptedDataset;
import com.s103458.security.Ticket;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Redefinable;

/**
 * Created by Jeppe on 09-11-2015.
 */
public class PrintRequest extends Request {
    private final String filename;
    private final String printer;
    public PrintRequest(RSAEncryptedDataset ticket, int reqid, String filename, String printer) {
        super(ticket, reqid);
        this.filename = filename;
        this.printer = printer;
    }
    public String getFilename(){
        return filename;
    }
    public String getPrinter(){
        return printer;
    }
}
