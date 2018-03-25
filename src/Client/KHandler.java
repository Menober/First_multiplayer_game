package Client;

import Client.Game;
import Client.Klient;

import java.security.Key;

/**
 * Stworzone przez Krzyśka dnia 2017-09-12.
 */
public class KHandler {
    public Klient klient;
    public KlientGUI kgui;
    public Game game;
    public GameCamera gameCamera;
    public KeyManager keyManager;

    public void setKeyManager(KeyManager keyManager){this.keyManager=keyManager;}
    public void setGame(Game game){this.game=game;}
    public void setKlient(Klient klient){this.klient=klient;}
    public void setKgui(KlientGUI kgui){this.kgui=kgui;}
}
