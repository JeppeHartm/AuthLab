import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by jeppe on 11/4/15.
 */
public interface PrinterService extends Remote {
    //printermethods here
    String print(String filename, String printer, Ticket t) throws RemoteException;   // prints file filename on the specified printer
    String queue(Ticket t) throws RemoteException;   // lists the print queue on the user's display in lines of the form <job number>   <file name>
    void topQueue(int job, Ticket t) throws RemoteException;   // moves job to the top of the queue
    void start(Ticket t) throws RemoteException;   // starts the print server
    void stop(Ticket t) throws RemoteException;   // stops the print server
    void restart(Ticket t) throws RemoteException;   // stops the print server, clears the print queue and starts the print server again
    String status(Ticket t) throws RemoteException;  // prints status of printer on the user's display
    String readConfig(String parameter, Ticket t) throws RemoteException;   // prints the value of the parameter on the user's display
    void setConfig(String parameter, String value, Ticket t) throws RemoteException;   // sets the parameter to value
}