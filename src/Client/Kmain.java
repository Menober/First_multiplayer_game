package Client;

import Client.KHandler;
import Client.Klient;

import java.io.IOException;

/**
 * Stworzone przez Krzyśka dnia 2017-09-08.
 */
public class Kmain {
    public static void main(String [] args) throws IOException, InterruptedException {
        KHandler handler=new KHandler();
        Assets.init();
        Klient klient=new Klient(handler);
        handler.setKlient(klient);
        KlientGUI gui=new KlientGUI(klient,handler);
        handler.setKgui(gui);
        gui.main();

        Thread k=new Thread(klient);
        k.start();

    System.currentTimeMillis();
    }
}
