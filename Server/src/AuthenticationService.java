import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.PublicKey;

/**
 * Created by jeppe on 11/4/15.
 */
public interface AuthenticationService extends Remote {
    PublicKey getKey() throws RemoteException;
    Ticket login(String name, String key) throws RemoteException;
    boolean logout(Ticket t) throws RemoteException;
}
