/**
 * Created by Ovylock on 19.05.2015.
 */

import java.io.*;
import java.net.*;
import java.lang.*;

public class ChatServer{

    private static ServerSocket priza;

    public static void main(String[] args)
    {
        System.out.println(">server starting...");
        try
        {
            priza = new ServerSocket(1234);
        }
        catch(IOException eroare)
        {
            System.out.println(">port not available...");
            System.exit(1);
        }
        System.out.println(">server RUNNING...");
        Socket legatura=new Socket();
        do
        {
            try {
                legatura = priza.accept();
            }
            catch(IOException IOex)
            {
                System.exit(1);
            }
            System.out.println(">client incoming...");
            Thread fir = new Thread(new ServerThread(legatura));
            fir.start();

        }while(true);
    }
}
