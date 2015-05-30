/**
 * Created by Ovylock on 20.05.2015.
 */

import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.*;

class ServerThread implements Runnable {
    Socket legatura;
    static ArrayList<User> utilizatori = new ArrayList<User>();
    String mesaj,nickname;
    boolean ok;
    int index;

    ServerThread(Socket legatura){
        this.legatura=legatura;
        try
        {
        utilizatori.add(new User(legatura));}
        catch(IOException eroare)
        {
            eroare.printStackTrace();
        }
    }

    @Override
    public void run(){

        try {
            for(int i=0;i<utilizatori.size();i++)
            {
                if(legatura.getPort()==utilizatori.get(i).legatura.getPort())
                    index=i;
            }
            do {
            ok = true;
            utilizatori.get(index).iesire_client.println("P.A.O Chat v0.9"+"\n");
            utilizatori.get(index).iesire_client.println(">nickname:");
            nickname = utilizatori.get(index).intrare_client.readLine();
            System.out.println(">client trying to connect with the nickname : <"+nickname+">");
            for (int i = 0; i < utilizatori.size(); i++)
                if (utilizatori.get(i).nickname.equals(nickname)) {
                    utilizatori.get(index).iesire_client.println(">nickname already used , try another...");
                    ok=false;
                }

            }
            while (!ok);

            utilizatori.get(index).setNickname(nickname);
            utilizatori.get(index).iesire_client.println(">welcome to the chat <" + nickname + "> !");
            utilizatori.get(index).iesire_client.println("\n"+">commands:");
            utilizatori.get(index).iesire_client.println("-----------------------------------------------");
            utilizatori.get(index).iesire_client.println(">1: list (lists online users)");
            utilizatori.get(index).iesire_client.println(">2: msg  (private message to a specific user)");
            utilizatori.get(index).iesire_client.println(">3: bcast(casts message to all online users)");
            utilizatori.get(index).iesire_client.println(">4: nick (changes nickname)");
            utilizatori.get(index).iesire_client.println(">5: quit (exit chat)");

            do{
                mesaj = utilizatori.get(index).intrare_client.readLine();
                for(int i=0;i<utilizatori.size();i++)
                {
                    if(legatura.getPort()==utilizatori.get(i).legatura.getPort())
                        index=i;
                }
                System.out.println(">client <" + nickname + "> executed " + mesaj + "...");
                if(mesaj.equals("list"))
                {
                    listare();
                }
                if(mesaj.equals("msg"))
                {
                    mesaj_particular();
                }
                if(mesaj.equals("bcast"))
                {
                    mesaj_grup();
                }
                if(mesaj.equals("nick"))
                {
                    schimb_nick();
                }

            }while(!mesaj.equals("quit"));
            if(mesaj.equals("quit"))
            {
                for (int i = 0; i < utilizatori.size(); i++)
                    if (utilizatori.get(i).nickname.equals(nickname))
                    {
                        utilizatori.get(i).iesire_client.println(">have a nice day !");
                    }

            }

            utilizatori.get(index).intrare_client.close();
            utilizatori.get(index).iesire_client.close();
            utilizatori.get(index).legatura.close();
            utilizatori.remove(index);

        }
        catch(IOException eroare)
        {
            eroare.printStackTrace();
        }

        try
        {
            legatura.close();
        }
        catch(IOException eroare)
        {
            eroare.printStackTrace();
        }
    }

    private  void listare()
    {
        utilizatori.get(index).iesire_client.println(">online users :");
        for(int i=0;i<utilizatori.size();i++)
        {
            utilizatori.get(index).iesire_client.println(">* : " + utilizatori.get(i).nickname);
        }
    }

    private void mesaj_particular()
    {
        String destinatar=null;
        int index_dest=-1;
        String message=null;
        utilizatori.get(index).iesire_client.println(">to:");
        try {
            destinatar = utilizatori.get(index).intrare_client.readLine();
        }
        catch(IOException eroare)
        {
            eroare.printStackTrace();
        }

        for(int i=0;i<utilizatori.size();i++)
        {
            if(utilizatori.get(i).nickname.equals(destinatar))
                index_dest=i;
        }

        if(index_dest!=-1)
        {
        utilizatori.get(index).iesire_client.println(">message: ");

        try{
        message = utilizatori.get(index).intrare_client.readLine();}
        catch(IOException eroare)
        {
            eroare.printStackTrace();
        }

        utilizatori.get(index_dest).iesire_client.println(utilizatori.get(index).nickname+" : "+message);}
        else
        {
            utilizatori.get(index).iesire_client.println(">no such user...");
        }

    }

    private void mesaj_grup()
    {
        String message=null;

        utilizatori.get(index).iesire_client.println(">message: ");

        try{
            message = utilizatori.get(index).intrare_client.readLine();}
        catch(IOException eroare)
        {
            eroare.printStackTrace();
        }

        for(int i=0;i<utilizatori.size();i++)
        {
            utilizatori.get(i).iesire_client.println(message);
        }

    }

    private void schimb_nick()
    {
        do {
            ok = true;
            utilizatori.get(index).iesire_client.println(">nickname: ");
            try{
            nickname = utilizatori.get(index).intrare_client.readLine();}
            catch(IOException eroare)
            {
                eroare.printStackTrace();
            }
            for (int i = 0; i < utilizatori.size(); i++)
                if (utilizatori.get(i).nickname.equals(nickname)) {
                    utilizatori.get(index).iesire_client.println(">nickname already used , try another...");
                    ok=false;
                }

        }
        while (!ok);

        utilizatori.get(index).setNickname(nickname);

    }
}
