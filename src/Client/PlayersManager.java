package Client;

import java.awt.*;
import java.util.ArrayList;

/**
 * Stworzone przez Krzyśka dnia 2017-09-20.
 */
public class PlayersManager {
    ArrayList<AnotherPlayer> players;
    KHandler handler;
    public PlayersManager(KHandler handler){
        players=new ArrayList<>();
        this.handler=handler;
    }
    public void addPlayer(String nazwa,int id,int lvl,int x,int y){
        // public AnotherPlayer(KHandler handler,String nazwa,int lvl,int x,int y){
        players.add(new AnotherPlayer(handler,nazwa,id,lvl,x,y));
    }
    public void removePlayer(AnotherPlayer p){
        players.remove(p);
    }
    public AnotherPlayer getAnotherPlayerById(int id){
        for(AnotherPlayer p:players)
            if(p.id==id)
            return p;

            return null;
    }
    public AnotherPlayer getAnotherPlayerByName(String name){
        for(AnotherPlayer p:players)
            if(p.nazwa.equals(name))
                return p;

        return null;
    }
    public void tick(){
        for(AnotherPlayer e : players){
            e.tick();
        }

    }
    public void render(Graphics g){
        for(AnotherPlayer e : players){
            e.render(g);
        }

    }
}

