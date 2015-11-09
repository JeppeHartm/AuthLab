package com.s103458.security;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.crypto.dsig.DigestMethod;
import java.io.*;
import java.security.*;

import static javax.crypto.Cipher.*;

/**
 * Created by jeppe on 11/5/15.
 */
public class Cryptographer {
    private static KeyPair kp;
    private static KeyGenerator AESgenerator;
    private static Cipher RSAcipher;
    private static Cipher AEScipher;
    static {
        kp = null;
        RSAcipher = null;
        try {
            KeyPairGenerator RSAgenerator = KeyPairGenerator.getInstance("RSA");
            AESgenerator = KeyGenerator.getInstance("AES");
            AESgenerator.init(128);
            RSAgenerator.initialize(1024);

            kp = RSAgenerator.generateKeyPair();
            RSAcipher = getInstance("RSA/ECB/PKCS1Padding");
            AEScipher = getInstance("AES");

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
    public static RSAEncryptedDataset encryptObject(Serializable serializable,PublicKey key) throws IOException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(serializable);
        byte[] data = bos.toByteArray();
        bos.close();
        oos.close();
        return encrypt(data,key);
    }
    public static Object decryptObject(RSAEncryptedDataset red, PrivateKey key) throws IOException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, ClassNotFoundException {
        byte[] data = decrypt(red,key);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object output = ois.readObject();
        bis.close();
        ois.close();
        return output;
    }
    public static RSAEncryptedDataset encrypt(byte[] msg, PublicKey key) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        SecretKey sk;
        AEScipher.init(ENCRYPT_MODE, sk = AESgenerator.generateKey());
        byte[] data = AEScipher.doFinal(msg);
        RSAcipher.init(ENCRYPT_MODE, key);
        byte[] AESkey = RSAcipher.doFinal(sk.getEncoded());
        return new RSAEncryptedDataset(data,AESkey);
    }
    public static RSAEncryptedDataset encrypt(byte[] msg) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return encrypt(msg,kp.getPublic());
    }
    public static byte[] decrypt(RSAEncryptedDataset red, PrivateKey key) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {

        RSAcipher.init(DECRYPT_MODE, key);
        byte[] secretKeyBytes = RSAcipher.doFinal(red.getKey());
        SecretKeySpec sks = new SecretKeySpec(secretKeyBytes,"AES");
        AEScipher.init(DECRYPT_MODE,sks);
        return AEScipher.doFinal(red.getData());
    }
    public static byte[] decrypt(RSAEncryptedDataset red) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        return decrypt(red,kp.getPrivate());
    }
    public static String hash_string(String message){
        byte[] hash = hash_bytes(message.getBytes());
        return new String(hash);
    }
    public static byte[] hash_bytes(byte[] bytes){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");
        }catch(NoSuchAlgorithmException n){
            n.printStackTrace();
        }
        return md.digest(bytes);

    }


}
