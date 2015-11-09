package com.s103458.security;

import java.io.Serializable;

/**
 * Created by Jeppe on 09-11-2015.
 */
public class RSAEncryptedDataset implements Serializable {
    byte[] data;
    byte[] key;

    public byte[] getData() {
        return data;
    }

    public byte[] getKey() {
        return key;
    }

    public RSAEncryptedDataset(byte[] data, byte[] key) {

        this.data = data;
        this.key = key;
    }
}