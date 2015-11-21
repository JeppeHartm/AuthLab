import com.s103458.security.RSAEncryptedDataset;
import com.s103458.server.AuthenticationService;
import com.s103458.server.PrinterService;
import com.s103458.security.Cryptographer;
import com.s103458.request.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.security.InvalidKeyException;
import java.security.PublicKey;

/**
 * Created by jeppe on 11/4/15.
 */
public class Client {
    public static void main(String[] args) throws IOException, NotBoundException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, ClassNotFoundException {
        AuthenticationService authenticator = (AuthenticationService) Naming.lookup("rmi://localhost:8081/authenticator");
        PrinterService printer = (PrinterService) Naming.lookup("rmi://localhost:8081/printer");
        LoginRequest lr = new LoginRequest("Jeppe","Hartmund","reqid".hashCode(),Cryptographer.getPublicKey());
        PublicKey serverKey = authenticator.getKey();
        RSAEncryptedDataset ticket = authenticator.login(Cryptographer.encryptObject(lr,serverKey));

        //printer.print(args[3]);
        PrintRequest pr = new PrintRequest(ticket,"reqid".hashCode(),"document.txt","p007b306");
        RSAEncryptedDataset ds = Cryptographer.encryptObject(pr,serverKey);
        RSAEncryptedDataset rep = printer.print(ds);
        String msg = (String)Cryptographer.decryptObject(rep,Cryptographer.getPrivateKey());
        System.out.println(msg);
        authenticator.logout(ticket);
        printer.print(ds);

    }
}
