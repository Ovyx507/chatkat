/**
 * Created by Ovylock on 19.05.2015.
 */

import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class ChatClient implements Runnable {

    static final String Gazda="192.168.1.105";
    static int Poarta=1234;
    static Socket legatura = null;
    static BufferedReader intrare_local = null;
    static BufferedReader intrare_server = null;
    static PrintStream iesire_server = null;
    static boolean terminate = false;

    public static void main (String[] args)
    {
        conectareServer();
    }

    private static void conectareServer()
    {
        try {
            legatura = new Socket(Gazda, Poarta);
            intrare_local = new BufferedReader(new InputStreamReader(System.in));
            intrare_server = new BufferedReader(new InputStreamReader(legatura.getInputStream()));
            iesire_server = new PrintStream(legatura.getOutputStream(), true);
        } catch (UnknownHostException eroare) {
            System.err.println(">problems with host : " + Gazda);
        } catch (IOException eroare) {
            System.err.println(">I/O problems with host : " + Gazda);
        }
        if (legatura != null && intrare_server != null && iesire_server != null) {
            try {
                new Thread(new ChatClient()).start();

                while (!terminate) {
                    iesire_server.println(intrare_local.readLine());
                }
                iesire_server.close();
                intrare_server.close();
                legatura.close();

            } catch (IOException eroare) {
                System.err.println(eroare);
            }
        }
    }

    @Override

    public void run()
    {

        String mesaj, raspuns;

        try {
            while ((raspuns = intrare_server.readLine()) != null) {
                System.out.println(raspuns);
                if (raspuns.indexOf(">have a nice day !") != -1)
                break;
            }
            terminate = true;
        } catch (IOException eroare) {
            System.err.println(eroare);
        }
    }
}
