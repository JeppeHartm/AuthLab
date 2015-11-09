import com.s103458.server.AuthenticationService;
import com.s103458.server.PrinterService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by jeppe on 11/4/15.
 */
public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        AuthenticationService authenticator = (AuthenticationService) Naming.lookup("rmi://localhost:5099/Authenticator");
        PrinterService printer = (PrinterService) Naming.lookup("rmi://localhost:5099/Printer");
        authenticator.login(args[0],args[1]);
        //printer.print(args[3]);



    }
}
