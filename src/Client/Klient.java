package Client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class Klient implements Runnable
{
    String serverAddress = "localhost"; //localhost
    Socket s=null;
    PrintStream os=null;
    BufferedReader sc=null;
    Scanner scanner=null;

    public ArrayList<String> stos;
    KHandler handler;
    public Klient(KHandler handler) throws IOException {
        this.handler=handler;
        this.stos=new ArrayList<>();
         this.s = new Socket(serverAddress, 9090);
        this.os=new PrintStream(s.getOutputStream());
        this.sc=new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.scanner=new Scanner(System.in);


    }
    public void send( String data) throws IOException {
        os.println(data);
        os.flush();

    }
    public void move(String gdzie) throws IOException {
        send("MOVE|"+gdzie);
    }

    public String getPakiet(){
        if(stos.size()>=1)
        return stos.get(0);
        else
            return "";
    }
    public void removePakiet(){
         stos.remove(0);
    }
    @Override
    public void run() {
String tmp="";
        while(true) {
            try{
                tmp=sc.readLine();
                this.stos.add(tmp);
            }catch(SocketException e){
                System.out.println("connection lost :<");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}