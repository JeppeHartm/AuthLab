package com.s103458.security;

import javafx.application.Application;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.xml.crypto.dsig.DigestMethod;
import java.io.*;
import java.security.*;

import static javax.crypto.Cipher.*;

/**
 * Created by jeppe on 11/5/15.
 */
public class Cryptographer {
    private static KeyPair kp;
    private static Cipher cipher;
    static {
        kp = null;
        cipher = null;
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            kp = generator.generateKeyPair();
            cipher = getInstance("RSA/ECB/PKCS1Padding");

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
    public static PrivateKey getPrivateKey(){
        return kp.getPrivate();
    }
    public static PublicKey getPublicKey(){
        return kp.getPublic();
    }
    public static byte[] encryptObject(Serializable serializable,PublicKey key) throws IOException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(serializable);
        byte[] data = bos.toByteArray();
        bos.close();
        oos.close();
        return encrypt(data,key);
    }
    public static Object decryptObject(byte[] data, PrivateKey key) throws IOException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, ClassNotFoundException {
        data = decrypt(data,key);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object output = ois.readObject();
        bis.close();
        ois.close();
        return output;
    }
    public static byte[] encrypt(byte[] msg, PublicKey key) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        cipher.init(ENCRYPT_MODE, key);
        return cipher.doFinal(msg);
    }
    public static byte[] encrypt(byte[] msg) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return encrypt(msg,kp.getPublic());
    }
    public static byte[] decrypt(byte[] msg, PrivateKey key) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        cipher.init(DECRYPT_MODE, key);
        return cipher.doFinal(msg);
    }
    public static byte[] decrypt(byte[] msg) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        return decrypt(msg,kp.getPrivate());
    }
    public static String hash_string(String message){
        byte[] hash = hash_bytes(message.getBytes());
        return new String(hash);
    }
    public static byte[] hash_bytes(byte[] bytes){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(DigestMethod.SHA1);
        }catch(NoSuchAlgorithmException n){
            n.printStackTrace();
        }
        return md.digest(bytes);

    }
}
