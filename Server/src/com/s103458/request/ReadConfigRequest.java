package com.s103458.request;

import com.s103458.security.RSAEncryptedDataset;

/**
 * Created by Jeppe on 09-11-2015.
 */
public class ReadConfigRequest extends Request{
    private final String parameter;

    public ReadConfigRequest(RSAEncryptedDataset ticket, int reqid, String parameter) {
        super(ticket, reqid);
        this.parameter = parameter;
    }
}
