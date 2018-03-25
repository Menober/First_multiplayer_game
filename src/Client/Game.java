package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Stworzone przez Krzyśka dnia 2017-09-12.
 */
public class Game implements Runnable{
    public boolean isLogged;
    public ArrayList<String> chat;
    public Mapa mapa;
    public KHandler handler;
    private BufferStrategy bs;
    private Graphics g;
    private GameCamera gameCamera;
    public KeyManager keyManager;
    public PlayersManager playersManager;

    public Game(KHandler handler){
        this.handler=handler;
        this.isLogged=false;
        this.chat=new ArrayList<>();
        this.mapa=new Mapa(null,0,0,null,handler);
        keyManager = new KeyManager();
        playersManager = new PlayersManager(handler);
        handler.setKeyManager(keyManager);

    }
    public void setMapa(Mapa map){
        this.mapa=map;
    }
    public void tick(){
      keyManager.tick();
      mapa.tick();
        playersManager.tick();
        if(handler.klient.getPakiet().startsWith("NEWPLAYERATMAP|")){
            String[] array=handler.klient.getPakiet().split("\\|");
            playersManager.addPlayer(array[1],Integer.valueOf(array[2]),Integer.valueOf(array[3]), Integer.valueOf(array[4]), Integer.valueOf(array[5]));
            handler.klient.removePakiet();
        }
        if(handler.klient.getPakiet().startsWith("PLAYERLEFT|")){
            String[] array=handler.klient.getPakiet().split("\\|");
            playersManager.removePlayer(playersManager.getAnotherPlayerByName(array[1]));
                    handler.klient.removePakiet();
        }

       if(handler.klient.getPakiet().startsWith("PLAYERMOVE|")){
          String[] array=handler.klient.getPakiet().split("\\|");
         playersManager.getAnotherPlayerByName(array[1]).setX(Integer.valueOf(array[2]));
          playersManager.getAnotherPlayerByName(array[1]).setY(Integer.valueOf(array[3]));
          handler.klient.removePakiet();
      }
        if(handler.klient.getPakiet().startsWith("YOURPOS|")){
            String[] array=handler.klient.getPakiet().split("\\|");
            mapa.entityManager.getPlayer().setX(Integer.valueOf(array[1]));
            mapa.entityManager.getPlayer().setY(Integer.valueOf(array[2]));

            handler.klient.removePakiet();
        }
        if(handler.klient.getPakiet().startsWith("PLAYERSATMAP|")){
            String[] array=handler.klient.getPakiet().split("\\|");
            for(int i=1;i<array.length;i+=6) {
                                             //AnotherPlayer(KHandler handler,String nazwa,int id,int lvl,int x,int y){
                playersManager.addPlayer(array[i],Integer.valueOf(array[i + 1]),Integer.valueOf(array[i + 2]), Integer.valueOf(array[i + 3]), Integer.valueOf(array[i+4]));

            }
            handler.klient.removePakiet();
        }
        if(handler.klient.getPakiet().startsWith("MAPA|"))
        {
            mapa.stringNaMape(handler.klient.getPakiet());
           //mapa.wyswietlMape();
            handler.klient.removePakiet();
        }
        if(handler.klient.getPakiet().startsWith("CHATADD|")) {
            ((DefaultListModel) handler.kgui.chatField.getModel()).addElement(handler.klient.getPakiet().substring(8));
            handler.kgui.chatField.ensureIndexIsVisible(handler.kgui.chatField.getModel().getSize()-1);
            handler.klient.removePakiet();
        }
        if(handler.klient.getPakiet().equals("YOUAREKICKED")){
            isLogged=false;
            ((DefaultListModel) handler.kgui.chatField.getModel()).addElement("You are kicked. :/");
            handler.klient.removePakiet();
        }
            //   if(gs.startsWith("CHATADD|")){
            //   System.out.println("DODANO");
            //   ((DefaultListModel) handler.kgui.chatField.getModel()).addElement(handler.klient.getGettedData().substring(8,handler.klient.getGettedData().length()-1));
            //   ((DefaultListModel) handler.kgui.chatField.getModel()).addElement("XD");

            //  handler.klient.setGettedData("");


    }
    private void render(){

        bs = handler.kgui.canvas.getBufferStrategy();
        if(bs==null){
            handler.kgui.canvas.createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();

        g.clearRect(0,0,handler.kgui.width,handler.kgui.height); //clear screen
        //draw here
        mapa.render(g);
        playersManager.render(g);
       /* g.setColor(Color.red);
        g.fillRect(1,1,100,100);

        g.setColor(Color.blue);
        g.fillRect(55,100,1000,1000);*/

        //end drawing
        bs.show();
        g.dispose();
    }
    public void run()
    {

        gameCamera=new GameCamera(handler,0,0);
        handler.gameCamera=gameCamera;
        handler.kgui.canvas.addKeyListener(keyManager);
        int fps=60;
        double timePerUpdate=1000000000/fps;
        double delta=0;
        long now;
        long lastTime=System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(isLogged){


                now=System.nanoTime();
                delta +=(now-lastTime)/timePerUpdate;
                timer+=now-lastTime;
                lastTime=now;

                if(delta>=1){
                    tick();
                    render();
                    ticks++;
                    delta--;
                }
                if(timer>=1000000000){
                    System.out.println("Ticks and frames: "+ticks);
                    ticks=0;
                    timer=0;
                }



        }

    }
}


