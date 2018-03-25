import java.io.Serializable;

/**
 * Stworzone przez Krzyśka dnia 2017-09-13.
 */
public class Konto implements Serializable {
    private String login;
    private String password;
    private int lvl;
    private int exp;
    private int id;
    private String nazwa;
    private int x,y;
    private boolean logged;
    private clientThread thread;


    public int mapa=0;
    public Konto(String login, String password,int lvl,int exp,int id,String nazwa,int x,int y){
        this.login=login;
        this.password=password;
        this.lvl=lvl;
        this.exp=exp;
        this.id=id;
        this.nazwa=nazwa;
        this.x=x;
        this.y=y;
        this.logged=false;
        this.thread=null;

    }



    public void setLogged(boolean logged){this.logged=logged;}
    public boolean getLogged(){return logged;}

    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public String getNazwa() {
        return nazwa;
    }
    public int getId() {
        return id;
    }
    public int getLvl() {
        return lvl;
    }
    public int getExp() {
        return exp;
    }
    public void addExp(int exp){this.exp+=exp;}



    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }

    public void setThread(clientThread th){this.thread=th;}
    public clientThread getThread() {
        return thread;
    }
}

