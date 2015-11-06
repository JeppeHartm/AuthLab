import javax.xml.crypto.dsig.DigestMethod;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jeppe on 11/4/15.
 */
public class AuthenticationServant extends UnicastRemoteObject implements AuthenticationService {
    public AuthenticationServant() throws RemoteException {
        super();
    }
    private String encrypt(String message){
        byte[] hash = hash(message.getBytes());
        return new String(hash);
    }
    private byte[] hash(byte[] bytes){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(DigestMethod.SHA1);
        }catch(NoSuchAlgorithmException n){
            n.printStackTrace();
        }finally {
            return md.digest(bytes);
        }
    }
    @Override
    public void login(String name, String key) throws RemoteException {
        String hashedKey = encrypt(key);
        Store.lookup(name,hashedKey);
    }
}
