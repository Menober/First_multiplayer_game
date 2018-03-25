/**
 * Stworzone przez Krzyśka dnia 2017-09-16.
 */
public class Mapa {
    public String nazwa;
    public int szerokosc,wysokosc;
    public int [][] mapa;

    public Mapa(String nazwa,int wysokosc,int szerokosc, int [][] mapa){
        this.nazwa=nazwa;
        this.szerokosc=szerokosc;
        this.wysokosc=wysokosc;
        this.mapa=mapa;
    }
    public void changeTile(int x,int y, int tile){
        mapa[y][x]=tile;
    }

    public String mapaNaString(){
        String smapa="";
        smapa=smapa+nazwa+"|"+Integer.toString(wysokosc)+"|"+Integer.toString(szerokosc)+"|";
        for(int i=0;i<wysokosc;i++)
            for(int j=0;j<szerokosc;j++){
                smapa=smapa+Integer.toString(mapa[i][j])+"|";
            }
    return smapa;
    }
}
