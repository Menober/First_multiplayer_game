import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Stworzone przez Krzyśka dnia 2017-09-13.
 */
public class BazaKont implements Serializable{
    public ArrayList<Konto> konta;
    public SHandler handler;
    public String sciezka="res/DataBase/accounts";
    public BazaKont(SHandler handler){
        this.handler=handler;
        this.konta=new ArrayList<>();
    }
    public void importBase() throws IOException, ClassNotFoundException { //pobrać z pliku
        FileInputStream fins= new FileInputStream(sciezka);
        ObjectInputStream ois=new ObjectInputStream(fins);
        this.konta= (ArrayList<Konto>) ois.readObject();
    }
    public void exportBase() throws IOException {//wpisac do pliku
        FileOutputStream fout = new FileOutputStream(sciezka);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(konta);

    }
    public void addAccount(Konto konto){konta.add(konto);}
    public void delAccount(Konto konto){
        konta.remove(konto);
    }
    public Konto getAccById(int id){
        for(Konto k:konta){
            if(k.getId()==id)
                return k;
        }
        return null;
    }
    public Konto getAccByLogin(String login){
        for(Konto k:konta){
            if(k.getLogin().equals(login))
                return k;
        }
        return null;

    }
    public int wolneId(){
        boolean isFree=true;
        for(int i=0;i<100;i++) {
            isFree=true;
            for (Konto k : konta) {
                if (i == k.getId()){
                    isFree = false;
                break;
                }
            }
            if(isFree==true)
                return i;
        }
        return -1;
    }
    public Konto getAccByNazwa(String nazwa)
    {
        for(Konto k:konta){
            if(k.getNazwa().equals(nazwa))
                return k;
        }
        return null;
    }
}
