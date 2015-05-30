/**
 * Created by Ovylock on 20.05.2015.
 */

import java.io.*;
import java.net.*;


public class User {

    Socket legatura;
    BufferedReader intrare_client;
    PrintStream iesire_client;
    String nickname="";

    public User(Socket legatura) throws IOException {
        this.legatura = legatura;

        try {
            intrare_client = new BufferedReader(new InputStreamReader(legatura.getInputStream()));
            iesire_client = new PrintStream(legatura.getOutputStream(), true);
        } catch (IOException ioEx) {
        }

    }

    public void setNickname(String nickname)
    {
        this.nickname=nickname;
    }
}
