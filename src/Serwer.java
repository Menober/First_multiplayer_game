import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


/**
 * Stworzone przez Krzyśka dnia 2017-09-02.
 */
public class Serwer implements Runnable{
    ArrayList<clientThread> cklienci=null;
    ArrayList<Thread> klienci=null;
    SHandler handler;
    public Serwer(SHandler handler){
        this.handler=handler;
    }
    @Override
    public void run() {

        klienci=new ArrayList<>();
        cklienci=new ArrayList<>();
        ServerSocket s= null;
        try {
            s = new ServerSocket(9090);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((DefaultListModel) handler.sg.chat.getModel()).addElement("Rozpoczynam nasłuchiwanie.");
        while(true){

        Socket socket= null;
        try {
            clear();
            socket = s.accept();
            clear();
            clientThread ct=new clientThread(socket,handler);
            cklienci.add(ct);
            Thread t=new Thread(ct);
            t.start();
            klienci.add(t);
        //    System.out.println("ILOSC POLACZEN: "+cklienci.size());

        } catch (IOException e) {
            e.printStackTrace();
        }



    }}
    public int ktoryKlient(clientThread c){
        int i=0;
        for(clientThread o:cklienci)
        {
            if(o==c)
                return i;
            else
                i++;

        }
        return i;
    }
public void printAllAccounts(){
    ((DefaultListModel) handler.sg.chat.getModel()).addElement("-------------------------------------");
    for(Konto k:handler.bazaKont.konta)
            ((DefaultListModel) handler.sg.chat.getModel()).addElement("Nazwa: "+k.getNazwa()+" Poziom: "+k.getLvl()+" Exp: "+k.getExp()+" ID: "+k.getId()+" IsLogged: "+k.getLogged());
    ((DefaultListModel) handler.sg.chat.getModel()).addElement("-------------------------------------");
    }
    public void saveAll() throws IOException {
    handler.bazaKont.exportBase();
    }


    public void send(String s) throws IOException {
        ((DefaultListModel) handler.sg.chat.getModel()).addElement("Server commands: "+s);

        switch(s){
            case "cls":clear();break;
            case "aso":handler.sg.chat.ensureIndexIsVisible(handler.sg.chat.getModel().getSize()-1);break;
            case "clsconsole":((DefaultListModel) handler.sg.chat.getModel()).removeAllElements();break;
            case "paa":printAllAccounts();break;
            case "save": handler.bazaKont.exportBase();((DefaultListModel) handler.sg.chat.getModel()).addElement("Baza kont zapisana!");break;
            case "saveAll":saveAll();break;
        }
        if(s.startsWith("/playermove"))
        {
            String[] array=s.split("\\s");
            if(array.length==4){
                handler.bazaKont.getAccByNazwa(array[1]).setX(Integer.valueOf(array[2]));
                handler.bazaKont.getAccByNazwa(array[1]).setY(Integer.valueOf(array[3]));
                handler.bazaKont.getAccByNazwa(array[1]).getThread().move();
                handler.bazaKont.getAccByNazwa(array[1]).getThread().tellEveryoneIveMoved();
            }
            else
                ((DefaultListModel) handler.sg.chat.getModel()).addElement("/playermove <nick> <x> <y>");

        }
        if(s.startsWith("/playerremove")){
            String[] array=s.split("\\s");
            if(array.length==2)
                if(handler.bazaKont.getAccByNazwa(array[1])!=null){
                handler.bazaKont.delAccount(handler.bazaKont.getAccByNazwa(array[1]));
                    ((DefaultListModel) handler.sg.chat.getModel()).addElement("Player "+array[1]+" removed.");

                }
                else
                    ((DefaultListModel) handler.sg.chat.getModel()).addElement("Nie znaleziono gracza o nicku "+array[1]);
            else
                ((DefaultListModel) handler.sg.chat.getModel()).addElement("/playerremove <nick>");

        }
        if(s.startsWith("/playeradd")){
            String[] array=s.split("\\s");
            if(array.length==5)
            //Konto(String login, String password,int lvl,int exp,int id,String nazwa,int x,int y,orientation)
            handler.bazaKont.addAccount(new Konto(array[1],array[2],Integer.valueOf(array[3]),0,handler.bazaKont.wolneId(),array[4],0,0));
            else
                ((DefaultListModel) handler.sg.chat.getModel()).addElement("/playeradd <login> <password> <lvl> <nick> ");
        }
        if(s.startsWith("/kick"))
        {
            for(clientThread c:cklienci)
                if(c.konto.getLogin().equals(s.substring(6))){
                    c.konto.setLogged(false);
                    klienci.get(ktoryKlient(c)).stop();
                    c.send("YOUAREKICKED");
                    ((DefaultListModel) handler.sg.chat.getModel()).addElement(c.konto.getLogin()+" is kicked.");
                    clear();
            }
        }
        /*
        if(s.equals("CLEAR")){
            clear();
            System.out.println("cleared");
        }
        else if(s.equals("accept")) {
            cklienci.get(0).send("ACCEPT");
            System.out.println("accept sended");
        }
        else
            cklienci.get(0).send(s);
            */
    }
    public void sendToAll(String data){
        for(clientThread c:cklienci){
            try {
                c.send(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void clear(){
        for(int i=0;i<cklienci.size();i++){
            if(cklienci.get(i)==null)
                cklienci.remove(i);
            else
            if(cklienci.get(i).isRunning==false){

                ((DefaultListModel) handler.sg.users.getModel()).removeElement(cklienci.get(i).socket.getInetAddress()+" "+cklienci.get(i).socket.getPort());
                if(cklienci.get(i).konto!=null)
                sendToAll("PLAYERLEFT|"+cklienci.get(i).konto.getNazwa());
                klienci.get(i).stop();
                klienci.remove(i);
                cklienci.remove(i);

                i--;
            }
        }
    }
}
