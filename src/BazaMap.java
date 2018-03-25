import java.util.ArrayList;

/**
 * Stworzone przez Krzyśka dnia 2017-09-16.
 */
public class BazaMap {
    ArrayList<Mapa> bazamap;
    SHandler handler;
    public BazaMap(SHandler handler){
        this.bazamap=new ArrayList<Mapa>();
        this.handler=handler;
    }
    public Mapa findMapaByName(String n){
        for(Mapa m:bazamap)
            if(m.nazwa.equals(n))
                return m;
        return null;
    }
    public Mapa findMapaById(int id){
       if(bazamap.get(id)!=null)
           return bazamap.get(id);
       else
           return null;
    }
}
