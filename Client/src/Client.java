import com.s103458.security.RSAEncryptedDataset;
import com.s103458.server.AuthenticationService;
import com.s103458.server.PrinterServant;
import com.s103458.server.PrinterService;
import com.s103458.security.Cryptographer;
import com.s103458.request.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.PublicKey;

import static org.junit.Assert.*;

/**
 * Created by jeppe on 11/4/15.
 */
public class Client {
    static PrinterService ps;
    static AuthenticationService as;
    static PublicKey serverKey;
    static final String TEST_REQ = "reqid"; //for testing purposes only; should be generated randomly.

    public static void main(String[] args) throws IOException, NotBoundException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, ClassNotFoundException {
//        AuthenticationService authenticator = (AuthenticationService) Naming.lookup("rmi://localhost:8081/authenticator");
//        PrinterService printer = (PrinterService) Naming.lookup("rmi://localhost:8081/printer");
//        LoginRequest lr = new LoginRequest("Jeppe","Hartmund","reqid".hashCode(),Cryptographer.getPublicKey());
//        PublicKey serverKey = authenticator.getKey();
//        RSAEncryptedDataset ticket = authenticator.login(Cryptographer.encryptObject(lr,serverKey));
//
//        //printer.print(args[3]);
//        PrintRequest pr = new PrintRequest(ticket,"reqid".hashCode(),"document.txt","p007b306");
//        RSAEncryptedDataset ds = Cryptographer.encryptObject(pr,serverKey);
//        RSAEncryptedDataset rep = printer.print(ds);
//        String msg = (String)Cryptographer.decryptObject(rep,Cryptographer.getPrivateKey());
//        System.out.println(msg);
//        authenticator.logout(ticket);
//        printer.print(ds);

    }
    public static RSAEncryptedDataset login(String name, String pass) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, IOException {
        LoginRequest lr = new LoginRequest(name,pass,TEST_REQ.hashCode(),Cryptographer.getPublicKey());
        return as.login(Cryptographer.encryptObject(lr,serverKey));
    }
    public static RSAEncryptedDataset print(RSAEncryptedDataset ticket,String doc, String printer){
        PrintRequest pr = new PrintRequest(ticket,TEST_REQ.hashCode(),doc,printer);//"document.txt","p007b306"
        RSAEncryptedDataset ds = null;
        try {
            ds = Cryptographer.encryptObject(pr,serverKey);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        RSAEncryptedDataset rep = null;
        try {
            rep = ps.print(ds);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return rep;
    }
    public static RSAEncryptedDataset queue(RSAEncryptedDataset ticket){
        QueueRequest pr = new QueueRequest(ticket,TEST_REQ.hashCode());
        RSAEncryptedDataset ds = null;
        try {
            ds = Cryptographer.encryptObject(pr,serverKey);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        RSAEncryptedDataset rep = null;
        try {
            rep = ps.queue(ds);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return rep;
    }
    public static RSAEncryptedDataset readConfig(RSAEncryptedDataset ticket){
        ReadConfigRequest pr = new ReadConfigRequest(ticket,TEST_REQ.hashCode(),"");
        RSAEncryptedDataset ds = null;
        try {
            ds = Cryptographer.encryptObject(pr,serverKey);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        RSAEncryptedDataset rep = null;
        try {
            rep = ps.readConfig(ds);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return rep;
    }
    public static void topQueue(RSAEncryptedDataset ticket){
        TopQueueRequest pr = new TopQueueRequest(ticket,TEST_REQ.hashCode(),2);
        RSAEncryptedDataset ds = null;
        try {
            ds = Cryptographer.encryptObject(pr,serverKey);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        try {
            ps.topQueue(ds);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @Before
    public void setup(){
        try {
            as = (AuthenticationService) Naming.lookup("rmi://localhost:8081/authenticator");
            ps = (PrinterService) Naming.lookup("rmi://localhost:8081/printer");
            serverKey = as.getKey();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnection(){
        assertNotNull(as);
        assertNotNull(ps);
        assertNotNull(serverKey);
    }

    @Test
    public void testAlice() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        String name = "Alice", pass="alice", role="MANAGER";
        RSAEncryptedDataset ticket = login(name,pass);
        assertNotNull(ticket);
        assertNotNull(print(ticket,"1","1"));
        assertNotNull(print(ticket,"2","2"));
        assertNotNull(print(ticket,"3","3"));
        assertNotNull(queue(ticket));
        assertNotNull(readConfig(ticket));
        topQueue(ticket);
    }

    @Test
    public void testBob() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        String name = "Bob", pass="bob", role="SERVICE";
        RSAEncryptedDataset ticket = login(name,pass);
        assertNull(ticket);
        assertNull(print(ticket,"1","1"));
        assertNull(print(ticket,"2","2"));
        assertNull(print(ticket,"3","3"));
        assertNull(queue(ticket));
        assertNull(readConfig(ticket));
        topQueue(ticket);
    }

    @Test
    public void testCecilia() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        String name = "Cecilia", pass="cecilia", role="POWERUSER";
        RSAEncryptedDataset ticket = login(name,pass);
        assertNotNull(ticket);
        assertNotNull(print(ticket,"1","1"));
        assertNotNull(print(ticket,"2","2"));
        assertNotNull(print(ticket,"3","3"));
        assertNotNull(queue(ticket));
        assertNull(readConfig(ticket));
        topQueue(ticket);
    }

    @Test
    public void testIda() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        String name = "Ida", pass="ida", role="POWERUSER";
        RSAEncryptedDataset ticket = login(name,pass);
        assertNotNull(ticket);
        assertNotNull(print(ticket,"1","1"));
        assertNotNull(print(ticket,"2","2"));
        assertNotNull(print(ticket,"3","3"));
        assertNotNull(queue(ticket));
        assertNull(readConfig(ticket));
        topQueue(ticket);
    }

    @Test
    public void testDavid() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        String name = "David", pass="david", role="USER";
        RSAEncryptedDataset ticket = login(name,pass);
        assertNotNull(ticket);
        assertNotNull(print(ticket,"1","1"));
        assertNotNull(print(ticket,"2","2"));
        assertNotNull(print(ticket,"3","3"));
        assertNotNull(queue(ticket));
        assertNull(readConfig(ticket));
        topQueue(ticket);
    }

    @Test
    public void testErica() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        String name = "Erica", pass="erica", role="USER";
        RSAEncryptedDataset ticket = login(name,pass);
        assertNotNull(ticket);
        assertNotNull(print(ticket,"1","1"));
        assertNotNull(print(ticket,"2","2"));
        assertNotNull(print(ticket,"3","3"));
        assertNotNull(queue(ticket));
        assertNull(readConfig(ticket));
        topQueue(ticket);
    }

    @Test
    public void testFred() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        String name = "Fred", pass="fred", role="USER";
        RSAEncryptedDataset ticket = login(name,pass);
        assertNotNull(ticket);
        assertNotNull(print(ticket,"1","1"));
        assertNotNull(print(ticket,"2","2"));
        assertNotNull(print(ticket,"3","3"));
        assertNotNull(queue(ticket));
        assertNull(readConfig(ticket));
        topQueue(ticket);
    }
    @Test
    public void testHenry() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        String name = "Henry", pass="henry", role="USER";
        RSAEncryptedDataset ticket = login(name,pass);
        assertNotNull(ticket);
        assertNotNull(print(ticket,"1","1"));
        assertNotNull(print(ticket,"2","2"));
        assertNotNull(print(ticket,"3","3"));
        assertNotNull(queue(ticket));
        assertNull(readConfig(ticket));
        topQueue(ticket);
    }
    @Test
    public void testGeorge() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        String name = "George", pass="george", role="SERVICE";
        RSAEncryptedDataset ticket = login(name,pass);
        assertNotNull(ticket);
        assertNull(print(ticket,"1","1"));
        assertNull(print(ticket,"2","2"));
        assertNull(print(ticket,"3","3"));
        assertNull(queue(ticket));
        assertNotNull(readConfig(ticket));
        topQueue(ticket);
    }
}
