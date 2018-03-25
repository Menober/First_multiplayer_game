import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Stworzone przez Krzyśka dnia 2017-09-08.
 */
public class clientThread implements Runnable{
    Socket socket=null;
    SHandler handler;
    PrintStream os= null;
    boolean isRunning;
    public Konto konto=null;

    public clientThread(Socket socket,SHandler handler){
        this.socket=socket;
        this.isRunning=true;
        this.handler=handler;
    }


   public void run(){
       ((DefaultListModel) handler.sg.users.getModel()).addElement(socket.getInetAddress()+" "+socket.getPort());
       ((DefaultListModel) handler.sg.chat.getModel()).addElement(socket.getInetAddress()+" "+socket.getPort()+" connected.");
        BufferedReader sc= null;
        try {
            sc = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line=null;
        //wysylka

        try {
            os = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
            try{
                line=sc.readLine();
            }catch(SocketException e){

                ((DefaultListModel) handler.sg.chat.getModel()).addElement(socket.getInetAddress()+" "+socket.getPort()+" disconnected.");
                isRunning=false;
                handler.serwer.clear();
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(line!=null) {
     //DODAWANIE KAZDEJ AKCJI DO KONSOLI. KAZDEGO PAKIETU        //   ((DefaultListModel) handler.sg.chat.getModel()).addElement(socket.getInetAddress() + " " + socket.getPort() + " said: " + line);
                if(line.startsWith("ACCESS|")){
                    String[] array=line.split("\\|");
             if(array.length==3){
                    if(handler.bazaKont.getAccByLogin(array[1])==null){
                        try {
                            send("WRONG_LOGIN");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                   if(handler.bazaKont.getAccByLogin(array[1]).getPassword().equals(array[2])){
                       try {
                           send("ACCEPT");
                           handler.bazaKont.getAccByLogin(array[1]).setLogged(true);
                           this.konto=handler.bazaKont.getAccByLogin(array[1]);
                            konto.setThread(this);
                          // long start=System.currentTimeMillis();
                         //  while(System.currentTimeMillis()-start<100){}       //wysylka mapy
                           przeslijMape();

                         //  start=System.currentTimeMillis();
                         //  while(System.currentTimeMillis()-start<100){}
                           move();                  //wysylka gracza pozycji


                         //  start=System.currentTimeMillis();
                      //     while(System.currentTimeMillis()-start<100){}    //wysyl
                           tellEveryoneImIn();      //wysylka do innych graczy, że sie pojawiłem

                        //   start=System.currentTimeMillis();
                        //   while(System.currentTimeMillis()-start<100){}    //wysylka ludziow
                           whoIsWhere();

                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
                   else
                       try {
                           send("WRONG_PASSWORD");
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                }}
                if(line.startsWith("MOVE|")){

                    String [] array=line.split("\\|");
                    switch(array[1]){
                        case "UP":konto.setY(konto.getY()-1);break;
                        case "DOWN":konto.setY(konto.getY()+1);break;
                        case "LEFT":konto.setX(konto.getX()-1);break;
                        case "RIGHT":konto.setX(konto.getX()+1);break;
                    }
                    move();
                    tellEveryoneIveMoved();


                }
                if(line.startsWith("CHAT|")){
                    ((DefaultListModel) handler.sg.chat.getModel()).addElement(konto.getNazwa()+": "+line.substring(5));
                    handler.serwer.sendToAll("CHATADD|"+konto.getNazwa()+": "+line.substring(5));

                }

                /*
                    try {
                        send("ACCEPT");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                */

            }
                line=null;
            }
        }


        public void whoIsWhere(){
           String whoiswhere="PLAYERSATMAP|";
           for(clientThread c: handler.serwer.cklienci){
               if(c.konto!=null){
                   if(c.konto!=konto) {
                       if (c.konto.mapa == konto.mapa) {
                           whoiswhere += c.konto.getNazwa() + "|" +c.konto.getId()+"|"+ c.konto.getLvl() + "|" + c.konto.getX() + "|" + c.konto.getY() + "|";
                       }
                   }
               }

           }
            try {
                send(whoiswhere);
            } catch (IOException e) {
                System.out.println("Nie udało się ustalić kto gdzie jest.");
            }

        }
    public void tellEveryoneImIn(){ //(String nazwa,int id,int lvl,int x,int y){
        for(clientThread c:handler.serwer.cklienci){
            if(c.konto!=null){
                if(c.konto!=konto){
                    if (c.konto.mapa == konto.mapa) {
                    try {
                        c.send("NEWPLAYERATMAP|"+konto.getNazwa()+"|"+konto.getId()+"|"+konto.getLvl()+"|"+konto.getX()+"|"+konto.getY());
                    } catch (IOException e) {
                        System.out.println("Nie udało się wysłać mojej pozycji graczowi: "+konto.getNazwa());
                    }}}}
        }
    }
    public void tellEveryoneIveMoved(){
       for(clientThread c:handler.serwer.cklienci){
           if(c.konto!=null){
               if(c.konto!=konto){
                   if (c.konto.mapa == konto.mapa) {
           try {
               c.send("PLAYERMOVE|"+konto.getNazwa()+"|"+konto.getX()+"|"+konto.getY()+"|");
           } catch (IOException e) {
               e.printStackTrace();
           }}}}
       }
    }
    public void move(){
        try {
            send("YOURPOS|"+konto.getX()+"|"+konto.getY()+"|");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void przeslijMape() throws IOException {
        send("MAPA|"+handler.bazaMap.findMapaById(konto.mapa).mapaNaString());
    }

    public void send( String data) throws IOException{

        os.println(data);
        os.flush();

    }

    public PrintStream getOs(){return os;}
}
