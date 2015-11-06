import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by jeppe on 11/4/15.
 */
public class PrinterServant extends UnicastRemoteObject implements PrinterService{
    protected PrinterServant() throws RemoteException {
        super();
    }

    @Override
    public String print(String filename, String printer, Ticket t) throws RemoteException {
        return null;
    }

    @Override
    public String queue(Ticket t) throws RemoteException {
        return null;
    }

    @Override
    public void topQueue(int job, Ticket t) throws RemoteException {

    }

    @Override
    public void start(Ticket t) throws RemoteException {

    }

    @Override
    public void stop(Ticket t) throws RemoteException {

    }

    @Override
    public void restart(Ticket t) throws RemoteException {

    }

    @Override
    public String status(Ticket t) throws RemoteException {
        return null;
    }

    @Override
    public String readConfig(String parameter, Ticket t) throws RemoteException {
        return null;
    }

    @Override
    public void setConfig(String parameter, String value, Ticket t) throws RemoteException {

    }
}
