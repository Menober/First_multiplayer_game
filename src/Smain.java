import javax.swing.*;
import java.io.IOException;

/**
 * Stworzone przez Krzyśka dnia 2017-09-05.
 */
public class Smain {
    public static void main(String [] args) throws InterruptedException, IOException {
        SHandler handler=new SHandler();

        SerwerGUI sg=new SerwerGUI(handler);
        sg.main();
        handler.setSg(sg);
        BazaKont bazaKont=new BazaKont(handler);
        BazaMap bazaMap=new BazaMap(handler);
        int[][] test={
                {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
                {3,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3},
                {3,1,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3},
                {3,1,2,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3},
                {3,1,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3},
                {3,1,2,1,1,1,2,1,1,1,2,1,2,2,2,1,1,1,1,3},
                {3,1,2,1,1,1,2,1,2,1,2,1,2,1,1,2,1,1,1,3},
                {3,1,2,1,1,1,1,2,1,2,1,1,2,1,1,1,1,1,1,3},
                {3,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3},
                {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
        };
        bazaMap.bazamap.add(new Mapa("test",10,20,test));
        handler.setBazaMap(bazaMap);
        handler.setBazaKont(bazaKont);

//
//        //String login, String password,int lvl,int exp,int id,String nazwa,int x,int y
//        bazaKont.addAccount(new Konto("q","q",0,0,0,"Q",0,0));
//        bazaKont.addAccount(new Konto("w","w",134,0,2,"W",4,4));
//        bazaKont.addAccount(new Konto("e","e",134,0,2,"E",4,4));
//        bazaKont.addAccount(new Konto("r","r",134,0,2,"R",4,4));
//        bazaKont.addAccount(new Konto("t","t",134,0,2,"T",4,4));
//        bazaKont.addAccount(new Konto("y","y",134,0,2,"Y",4,4));
//        bazaKont.addAccount(new Konto("u","u",134,0,2,"U",4,4));
//
//        bazaKont.exportBase();


        try {
            bazaKont.importBase();
            ((DefaultListModel) handler.sg.chat.getModel()).addElement("Baza kont załadowana!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Serwer serwer=new Serwer(handler);
        handler.setSerwer(serwer);
        Thread s=new Thread(serwer);
        ((DefaultListModel) handler.sg.chat.getModel()).addElement("Serwer start.\n");
        s.start();


       // serwer.klienci.get(0).send(serwer.klienci.get(0).getOs(),"XD");
    }
}
